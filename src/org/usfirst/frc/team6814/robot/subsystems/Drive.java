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
		right *=

				drive.tankDrive(left, right);
	}

	private double CalculatePowerWithGear(double power) {
		return power * (gear / (gearMax - gearMin + 1)); //for example: 1st gear: power * (1/3)
		//                                                              2nd gear: power * (2/3)
		//                                                              3rd gear: power * (3/3)
	}

	public void stop() {
		drive.tankDrive(0, 0);
	}

	public void log() {

	}

}
