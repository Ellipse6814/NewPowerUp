/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6814.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team6814.robot.Robot;

/**
 * An example command. You can replace me with your own command.
 */
public class DriveTeleToggleInverted extends Command {
	public DriveTeleToggleInverted() {
		// Use requires() here to declare subsystem dependencies
//		requires(Robot.m_drive); does not conflict with other drive commands, don't interrupt the joystick drive command
		Robot.drive.toggleInverted();
	}

	public DriveTeleToggleInverted(boolean setInverted) {
		Robot.drive.setInverted(setInverted);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true; // exit immediately
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		// Nothing to do.
	}

	// Called when another command which needs this subsystem
	@Override
	protected void interrupted() {
		end();
	}
}
