package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

@Deprecated public class IntakeTeleSetSpeed extends Command {

	private double speed;
	private boolean isOut;
	private boolean exitImmediately = false;

	public IntakeTeleSetSpeed(boolean isOut, double speed) {
		requires(Robot.intake);
		this.speed = speed;
		this.isOut = isOut;
	}

	public IntakeTeleSetSpeed(boolean isOut) {
		requires(Robot.intake);
		this.speed = 1.0;
		this.isOut = isOut;
	}

	public IntakeTeleSetSpeed() {
		requires(Robot.intake);
		Robot.intake.stop();
		exitImmediately = true;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if (isOut)
			Robot.intake.out(speed);
		else
			Robot.intake.in(speed);

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// feeds the motor safety function
		if (isOut)
			Robot.intake.out(speed);
		else
			Robot.intake.in(speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return exitImmediately;
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