package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeOut extends Command {

	private double speed;

	public IntakeOut(double speed) {
		requires(Robot.intake);
		this.speed = speed;
	}

	public IntakeOut() {
		this(1.0);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.intake.out(speed);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.intake.out(speed); // feeds the motor safety function
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