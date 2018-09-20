/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6814.robot.subsystems;

import org.usfirst.frc.team6814.robot.commands.DriveTele2Joy;
import org.usfirst.frc.team6814.robot.commands.DriveTeleDPad;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends Subsystem {

	private Spark leftFrontMotor = new Spark(0);
	private Spark leftBackMotor = new Spark(1);
	private Spark rightFrontMotor = new Spark(2);
	private Spark rightBackMotor = new Spark(3);

	private SpeedController leftMotor = new SpeedControllerGroup(leftFrontMotor, leftBackMotor);
	private SpeedController rightMotor = new SpeedControllerGroup(rightFrontMotor, rightBackMotor);

	private int gear = 1;
	private int gearMax = 3;
	private int gearMin = 1;
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

	public void drive(double left, double right, boolean enableGear) {
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

		drive(left, right);
	}

	public void drive(double left, double right) {
		leftMotor.set(-left);
		rightMotor.set(right);
	}

	public void driveArcade(double power, double turn, boolean enableGear) {
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

		drive(left, right);
	}

	public void driveLeft(double left, boolean enableGears) {
		if (enableGears) {
			left = calculatePowerWithGear(left);
		}

		left = calculatePowerInverted(left);

		leftMotor.set(-left);
	}

	public void driveRight(double right, boolean enableGears) {
		if (enableGears) {
			right = calculatePowerWithGear(right);
		}

		right = calculatePowerInverted(right);

		rightMotor.set(right);
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

	private double motorRamp(double power) {
		// TODO: implement this!!
		return 0;
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
