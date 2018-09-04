/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Have the robot drive tank style using the PS3 Joystick until interrupted.
 */
public class DriveTeleDPad extends Command {
	public DriveTeleDPad() {
		requires(Robot.m_drive);
	}
	

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double dir = Robot.m_oi.getJoystick().getPOV();
		double l = 0, r = 0;
		if (dir == 0) {
			l = 1;
			r = 1;
			l *= .89;
			r *= .89;
		} else if (dir == 90) {
			l = -1;
			r = 1;
			l *= .89;
			r *= .89;
		} else if (dir == 180) {
			l = -1;
			r = -1;
			l *= .89;
			r *= .89;
		} else if (dir == 270) {
			l = 1;
			r = -1;
			l *= .89;
			r *= .89;
		} else if (dir == 360) {
			l = 1;
			r = 1;
			l *= .89;
			r *= .89;
		}
		Robot.m_drive.drive(l, r);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false; // Runs until interrupted
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.m_drive.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
	
}
