/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6814.robot.subsystems;

import org.usfirst.frc.team6814.robot.Constants;
import org.usfirst.frc.team6814.robot.commands.DriveTele2Joy;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends Subsystem {

	private SpeedController leftMotor = new SpeedControllerGroup(new Spark(Constants.kDriveLeftFrontMotorPort),
			new Spark(Constants.kDriveLeftBackMotorPort));
	private SpeedController rightMotor = new SpeedControllerGroup(new Spark(Constants.kDriveRightFrontMotorPort),
			new Spark(Constants.kDriveRightBackMotorPort));

	private int gear = 1;
	private final int gearMax = 3;
	private final int gearMin = 1;
	private double prevPower = 0; // for motor ramping
	private boolean driveInverted = false;

	// Getters & Setters:

	public Drive() {
		super();
		System.out.println("Drive Subsystem Started");
	}

	public void initDefaultCommand() {
		setDefaultCommand(new DriveTele2Joy());
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

	public void toggleInverted() { // TODO: implement buttons and commands to toggle this
		driveInverted = !driveInverted;
	}

	public void setInverted(boolean inverted) {
		driveInverted = inverted;
	}

	public boolean getInverted() {
		return driveInverted;
	}

	public void stop() {
		drive(0, 0);
	}

	public void reset() { // TODO:
		gear = 1;
		driveInverted = false;
//		m_gyro.reset();
//		m_leftEncoder.reset();
//		m_rightEncoder.reset();
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
			left = motorRamp(left);
			right = motorRamp(right);
		}

		drive(left, right);
	}

	public void drive(double left, double right) {
		leftMotor.set(-left);
		rightMotor.set(right);
	}

	public void driveArcade(double power, double turn, boolean enableGear, boolean rampMotors) {
		// algorithm goes here
		if (enableGear) {
			power = calculatePowerWithGear(power);
			turn = calculatePowerWithGear(turn);

			if (gear == 1)
				turn *= 0.9;
			else if (gear == 2)
				turn *= 0.7;
			else if (gear == 3)
				turn *= 0.5;
		}

		power = calculatePowerInverted(power);
		turn = calculatePowerInverted(turn);

		double left, right;
		left = power - turn;
		right = power + turn;

		if (rampMotors) {
			left = motorRamp(left);
			right = motorRamp(right);
		}

		drive(left, right);
	}

	public void driveLeft(double left, boolean enableGears, boolean rampMotors) {
		if (enableGears) {
			left = calculatePowerWithGear(left);
		}

		left = calculatePowerInverted(left);

		if (rampMotors) {
			left = motorRamp(left);
		}

		leftMotor.set(-left);
	}

	public void driveRight(double right, boolean enableGears, boolean rampMotors) {
		if (enableGears) {
			right = calculatePowerWithGear(right);
		}

		right = calculatePowerInverted(right);
		
		if (rampMotors) {
			right = motorRamp(right);
		}
		
		rightMotor.set(right);
	}

	// Utils

	private double calculatePowerWithGear(double power) {
		// 1st gear: power * (1/3)
		// 2nd gear: power * (2/3)
		// 3rd gear: power * (3/3)

		// TODO: implement a more comfortable calculation with ifs and cases
		// power * (current gear / total number of gears)
		return power * (gear / (gearMax - gearMin + 1.0));
	}

	private double calculatePowerInverted(double power) {
		if (driveInverted)
			return -power;
		return power;
	}

	private double motorRamp(double power) {
		// because output from 0 to 1 changes speed to 0 - 12 ft/s = 3.6m/s,
		// -> 1m/s = 1/3.6 output; 1m/s/s = 3.6
		// because the code updates every 20ms -> 50 times per second,
		// -> each time it is allowed to accelerate by +- (? m/s/s) / 50
		// assuming the robot is 45 kg -> F=ma how much force can we stand?; let's just use 1 m/s/s to test first TODO
		final double MaxChange = (1 / 3.6) / 50;
		if (power - prevPower > MaxChange)
			power = prevPower + MaxChange;
		if (power - prevPower < -MaxChange)
			power = prevPower - MaxChange;
		return power;
	}

	public double getGyroAngle() {
		// TODO:
		return 0;
	}

	public double getEncoderLeft() {
		// TODO:
		return 0;
	}

	public double getEncoderRight() {
		// TODO:
		return 0;
	}

	public void log() {
		SmartDashboard.putNumber("Gear", gear);
	}

}
