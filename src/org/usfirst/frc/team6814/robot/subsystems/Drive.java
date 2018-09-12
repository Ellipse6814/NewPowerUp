/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6814.robot.subsystems;

import org.usfirst.frc.team6814.robot.commands.DriveTeleDPad;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drive extends Subsystem {

	private Spark leftFrontMotor = new Spark(0);
	private Spark rightFrontMotor = new Spark(2);
	private Spark leftBackMotor = new Spark(1);
	private Spark rightBackMotor = new Spark(3);

	private SpeedController leftMotor = new SpeedControllerGroup(leftFrontMotor, leftBackMotor);
	private SpeedController rightMotor = new SpeedControllerGroup(rightFrontMotor, rightBackMotor);

	private DifferentialDrive drive = new DifferentialDrive(leftMotor, rightMotor);

	private int gear = 1;
	private int gearMax = 3;
	private int gearMin = 1;

	public void initDefaultCommand() {
		setDefaultCommand(new DriveTeleDPad());
	}

	public void drive(double left, double right) {
		drive.tankDrive(left, right);
	}

	public void gearUp() {
		if (gear < gearMax)
			gear++;
	}

	public void gearDown() {
		if (gear > gearMin)
			gear--;
	}

	public void drive(double left, double right, boolean Enablegear, boolean squaredInputs) {
		// algorithm goes here
		left *= gear / (gearMax - gearMin + 1);
		right *= gear / (gearMax - gearMin + 1);

		drive.tankDrive(left, right);
	}

	public void stop() {
		drive.tankDrive(0, 0);
	}

}
