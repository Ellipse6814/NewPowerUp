/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6814.robot.subsystems;

import org.usfirst.frc.team6814.robot.Constants;
import org.usfirst.frc.team6814.robot.commands.DriveTele2Joy;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends Subsystem {
	// I'm not making this a PID subsystem because this subsystem could have many
	// kinds of PID: 2 wheel drive straight PID; 1 gyro, 1 wheel drive straight PID;
	// turning PID, etc. It will be very messy if all of those were to be
	// implemented in this one tiny file. Therefore, I am making individual PID
	// command classes for each different PID since they will look very different
	// based on their unique purposes.

	private SpeedController leftMotor = new SpeedControllerGroup(new Spark(Constants.kDriveLeftFrontMotorPort),
	        new Spark(Constants.kDriveLeftBackMotorPort));
	private SpeedController rightMotor = new SpeedControllerGroup(new Spark(Constants.kDriveRightFrontMotorPort),
	        new Spark(Constants.kDriveRightBackMotorPort));

	public AHRS gyro;
	private Encoder leftEncoder, rightEncoder;
	private boolean encoderSafe = true;
	private double encoderSafeValL, encoderSafeValR = 0;
	private long encoderSafeTimestamp = 0;

	private int gear = 1;
	private final int gearMax = 3;
	private final int gearMin = 1;
	private double prevPowerL = 0, prevPowerR = 0; // for motor ramping
	private boolean driveInverted = false;

	// Getters & Setters:

	public Drive() {
		super();
		initGyro();
		initEncoder();
		System.out.println("Drive Subsystem Started");
	}

	public void initDefaultCommand() {
		setDefaultCommand(new DriveTele2Joy());
	}

	private void initEncoder() {
		rightEncoder = new Encoder(Constants.kDriveEncoderChannelA, Constants.kDriveEncoderChannelB,
		        Constants.kDriveEncoderReversed, Constants.kDriveEncoderEncodingType);
		rightEncoder.setMaxPeriod(Constants.kDriveEncoderRegardStop); // regard motor as stopped if no movement for 0.2
		                                                              // seconds
		rightEncoder.setMinRate(10); // regard motor as stopped if distance per second < 10
		// gearbox ratio 1:49; 0.5 inch changing diameter TODO;
//		final double PulseToDistanceConst = (1 / 49) * Math.PI * 0.02; // rotations -> meters
		rightEncoder.setDistancePerPulse(Constants.kDrivePulse2Distance); // the scaling constant that converts pulses
		                                                                  // into distance
		rightEncoder.setSamplesToAverage(Constants.kDriveEncoderReduceNoiseAverageSampleNum); // used to reduce noise in
		                                                                                      // period
		rightEncoder.reset();
	}

	private void initGyro() {
		try {
			gyro = new AHRS(SPI.Port.kMXP);
//			gyro = new AHRS(SerialPort.Port.kUSB);

		} catch (RuntimeException ex) {
			System.out.println("Error instantiating navX-MXP:  " + ex.getMessage());
		}
	}

	public double getGyroAngle() {
//		System.out.println("Getangle Func: " + gyro.getRawGyroX());
		return gyro.getAngle();
	}

	public AHRS getGyro() {
		return gyro;
	}

	public double getEncoderLeftDistance() {
		// TODO:
		return 0;
	}

	public double getEncoderRightDistance() {
		// this distance is actually a fake distance because the diameter of the shaft
		// changes
		return rightEncoder.getDistance();
	}

	public void gearUp() {
		if (gear < gearMax)
			gear++;
		System.out.println("Gear+ " + gear);
	}

	public void gearDown() {
		if (gear > gearMin)
			gear--;
		System.out.println("Gear- " + gear);
	}

	public void toggleInverted() {
		driveInverted = !driveInverted;
	}

	public void setInverted(boolean inverted) {
		driveInverted = inverted;
	}

	public boolean getInverted() {
		return driveInverted;
	}

	public boolean getEncoderSafe() {
		return encoderSafe;
	}

	public void setEncoderSafe(boolean encoderSafe) {
		this.encoderSafe = encoderSafe;
	}

	public void stop() {
		drive(0, 0);
	}

	public void reset() {
		gear = 1;
		driveInverted = false;
		gyro.reset();
		rightEncoder.reset();
		System.out.println("Drive RESET called: gear, drive, invertedDrive, gyro, rightEncoder resetted successfully.");
	}

	// Drive Commands:

	public void drive(double left, double right, boolean enableGear, boolean rampMotors) {
		// algorithm goes here
//

		// take gears into factor
		if (enableGear) {
			left = calculatePowerWithGear(left);
			right = calculatePowerWithGear(right);
		}

		// drive the robot backwards
		left = calculatePowerInverted(left);
		right = calculatePowerInverted(right);

		if (rampMotors) {
			left = motorRampL(left);
			right = motorRampR(right);
			prevPowerL = left;
			prevPowerR = right;
		}

		drive(left, right);
	}

	public void drive(double left, double right) {
		leftMotor.set(-left);
		rightMotor.set(right);
		updateEncoderSafety();
	}

	public void driveArcade(double power, double turn, boolean enableGear, boolean rampMotors) {
		// algorithm goes here
		if (enableGear) {
			power = calculatePowerWithGear(power);
			turn = calculatePowerWithGear(turn);

			// additional turn reduces (increases controlability)
			if (gear == 1)
				turn *= 0.95;
			else if (gear == 2)
				turn *= 0.8;
			else if (gear == 3)
				turn *= 0.8;
		}

		power = calculatePowerInverted(power);
//		turn = calculatePowerInverted(turn);

		double left, right;
		left = power + turn;
		right = power - turn;

		if (rampMotors) {
			left = motorRampL(left);
			right = motorRampR(right);
			prevPowerL = left;
			prevPowerR = right;
		}

		drive(left, right);
	}

	public void driveLeft(double left, boolean enableGears, boolean rampMotors) {
		if (enableGears) {
			left = calculatePowerWithGear(left);
		}

		left = calculatePowerInverted(left);

		if (rampMotors) {
			left = motorRampL(left);
			prevPowerL = left;
		}
		leftMotor.set(-left);
		updateLeftEncoderSafety();
	}

	public void driveRight(double right, boolean enableGears, boolean rampMotors) {
		if (enableGears) {
			right = calculatePowerWithGear(right);
		}

		right = calculatePowerInverted(right);

		if (rampMotors) {
			right = motorRampR(right);
			prevPowerR = right;
		}

		rightMotor.set(right);
		updateRightEncoderSafety();
	}

	// Utils

	private double calculatePowerWithGear(double power) {
		// 1st gear: power * (1/3)
		// 2nd gear: power * (2/3)
		// 3rd gear: power * (3/3)

		// power * (current gear / total number of gears)
		return power * (gear / (gearMax - gearMin + 1.0));
	}

	private double calculatePowerInverted(double power) {
		if (driveInverted)
			return -power;
		return power;
	}

	private double motorRampL(double powerL) {
		// because output from 0 to 1 changes speed to 0 - 12 ft/s = 3.6m/s,
		// -> 1m/s = 1/3.6 output; 1m/s/s = 3.6
		// because the code updates every 20ms -> 50 times per second,
		// -> each time it is allowed to accelerate by +- (? m/s/s) / 50
		// assuming the robot is 45 kg -> F=ma how much force can we stand?; let's just
		// use 1 m/s/s to test first TODO
		final double MaxChange = (10 / 3.6) / 50;
		if (powerL - prevPowerL > MaxChange)
			powerL = prevPowerL + MaxChange;
		if (powerL - prevPowerL < -MaxChange)
			powerL = prevPowerL - MaxChange;
		return powerL;
	}

	private double motorRampR(double powerR) {
		// because output from 0 to 1 changes speed to 0 - 12 ft/s = 3.6m/s,
		// -> 1m/s = 1/3.6 output; 1m/s/s = 3.6
		// because the code updates every 20ms -> 50 times per second,
		// -> each time it is allowed to accelerate by +- (? m/s/s) / 50
		// assuming the robot is 45 kg -> F=ma how much force can we stand?; let's just
		// use 1 m/s/s to test first TODO
		final double MaxChange = (10 / 3.6) / 50;
		if (powerR - prevPowerR > MaxChange)
			powerR = prevPowerR + MaxChange;
		if (powerR - prevPowerR < -MaxChange)
			powerR = prevPowerR - MaxChange;
		return powerR;
	}

	private void updateEncoderSafety() {
		updateRightEncoderSafety();
		updateLeftEncoderSafety();
	}

	private void updateRightEncoderSafety() {
		if (!encoderSafe) {
			return;
		}
		long time = System.currentTimeMillis();
		if (prevPowerR > 0.4 && getEncoderRightDistance() - encoderSafeValR < 0.02) { // assume it's unsafe if at this
		                                                                              // instant it's unsafe, but
		                                                                              // let's wait a little longer to
		                                                                              // call it
			// something's not right, check if it has been happening for 2 seconds
			if (time - encoderSafeTimestamp > 2000) { // it has been assumed to be unsafe for 2
			                                          // seconds, probably actually unsafe, EMERGENCY
			                                          // STOP!
//				encoderSafe = false; //disable safety function. Reason: untested.
//				disablePID();
				System.out.println(
				        "ERROR: DETECTED DRIVE RIGHT ENCODER NOT FUNCTIONING PROPERLY: DISABLING PID FUNCTIONALITY, EVERYTHING ELSE IS OK.");
			}
		} else { // reset to safe
			// expected: update last safe timestamp to now
			encoderSafeTimestamp = time;
			// !IMPORTANT: only record the last safe distance, or else delta distance will
			// never reach 0.02m in 20ms; but this way, it still might not reach it on the
			// first iteration, but it should reach it sometimes under 2 seconds
			encoderSafeValR = getEncoderRightDistance();
		}
	}

	private void updateLeftEncoderSafety() {
		// Oops, there is no encoder on left wheel
	}

	public void log() {
		SmartDashboard.putNumber("Gear", gear);
		SmartDashboard.putNumber("Left Chassis Motor", -prevPowerL);
		SmartDashboard.putNumber("Right Chassis Motor", -prevPowerR);
		SmartDashboard.putNumber("Right Wheel Encoder", getEncoderRightDistance());
		SmartDashboard.putBoolean("Drive Encoder Functional", encoderSafe);
		SmartDashboard.putNumber("Gyro", getGyroAngle());
//		System.out.println("GyroLog:" + getGyroAngle());
		// in test mode, we can directly see AND MODIFY values from these objects
		addChild("Drive R Encoder", rightEncoder);
		addChild("Gyro", gyro);
		addChild("Drive L Motor", (Sendable) leftMotor);
		addChild("Drive R Motor", (Sendable) rightMotor);

	}

}
