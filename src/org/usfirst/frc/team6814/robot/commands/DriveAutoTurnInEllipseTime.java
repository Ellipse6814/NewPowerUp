/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveAutoTurnInEllipseTime extends Command {
	private double speed;
	private boolean enableGear;
	private boolean rampMotors;
	private double timeInSec;

	public DriveAutoTurnInEllipseTime(double TimeInSec, double Speed) {
		this(TimeInSec, Speed, false, false);
	}

	public DriveAutoTurnInEllipseTime(double TimeInSec, double Speed, boolean EnableGear, boolean RampMotors) {
		super(); // timeout seconds: (this functionality is built-in to the TimedCommnand base
		         // class)
		requires(Robot.drive);
		speed = Speed;
		enableGear = EnableGear;
		rampMotors = RampMotors;
		timeInSec = TimeInSec;
	}

	@Override
	protected void initialize() {
		Robot.drive.reset();
		setTimeout(timeInSec);
		Robot.drive.drive(speed, -speed, enableGear, rampMotors); // TODO: check if the negative sign is added to the
		                                                          // right wheel
		System.out.println("Auto turn for: " + timeInSec + "s with " + speed + " speed started");

	}

	@Override
	protected void execute() {
		Robot.drive.drive(speed, speed, enableGear, rampMotors); // feeds the motor safety function
	}

	// using inherited functionality
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	@Override
	protected void end() {
		// Stop PID and the wheels
		Robot.drive.stop();
		System.out.println("Auto turn for: " + timeInSec + "s with " + speed + " speed ended");
	}

	@Override
	protected void interrupted() {
		System.out.println("Auto turn for: " + timeInSec + "s with " + speed + " speed interrupted");
		end();
	}
}
