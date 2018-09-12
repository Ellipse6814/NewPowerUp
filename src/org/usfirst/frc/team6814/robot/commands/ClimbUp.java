package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ClimbUp extends Command {
	public ClimbUp() {
		requires(Robot.m_climb);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.m_climb.up();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	  //nothing to loop
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.m_climb.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}