/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * Drive the given distance straight (negative values go backwards). Uses a
 * local PID controller to run a simple PID loop that is only enabled while this
 * command is running. The input is the averaged values of the left and right
 * encoders.
 */
public class DriveAutoStraightTime extends TimedCommand {
	private double speed;
	private boolean enableGear;

	public DriveAutoStraightTime(double timeInSec, double Speed, boolean EnableGear) {
		super(timeInSec); // timeout seconds
		requires(Robot.m_drive);
		speed = Speed;
		enableGear = EnableGear;
	}

	@Override
	protected void initialize() {
		Robot.m_drive.reset();
		Robot.m_drive.drive(speed, speed, enableGear, false);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		// Stop PID and the wheels
		Robot.m_drive.stop();
	}
	
	@Override
	protected void interrupted() {
		end();
	}
}
