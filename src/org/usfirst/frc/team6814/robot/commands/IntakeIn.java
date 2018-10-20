package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeIn extends Command { //DELETEME: SWITCH IS @ PORT 4
	
	private double speed= 0;
	
	public IntakeIn(double speed) {
		requires(Robot.intake);
		this.speed = speed;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.intake.in(speed);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.intake.in(speed); //feeds the motor safety function 
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.intake.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}