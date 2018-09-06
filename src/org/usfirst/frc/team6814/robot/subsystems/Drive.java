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
	
	
	public void initDefaultCommand() {
		setDefaultCommand(new DriveTeleDPad());
	}

	public void drive(double left, double right) {
		drive.tankDrive(left, right);
	}
	
	public void gearUp() {
		if (gear<=3)
			gear++;
	}
	
	public void gearDown() {
		if (gear>=0)
			gear--;
	}
	
	public void drive(double left, double right, boolean Enablegear, boolean squaredInputs) {
		//algorithm goes here
		left *= gear;
		right *= gear;
		
		
		
		drive.tankDrive(left, right);
	}

	public void stop() {
		drive.tankDrive(0, 0);
	}

}
