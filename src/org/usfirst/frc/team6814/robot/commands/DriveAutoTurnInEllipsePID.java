package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class DriveAutoTurnInEllipsePID extends PIDCommand {

	private double speed;
	private boolean enableGear;
	private boolean rampMotors;
	private double timeout;
	private double tolerance;
	private PIDController PID;

	public DriveAutoTurnInEllipsePID(String name, double p, double i, double d, double timeout, double tolerance,
	        boolean enableGear, boolean rampMotors) {
		super(name, p, i, d);
		initPIDController();
		this.timeout = timeout;
		this.tolerance = tolerance;
		this.enableGear = enableGear;
		this.rampMotors = rampMotors;
	}

	private void initPIDController() {
		PID = getPIDController();
		PID.setAbsoluteTolerance(tolerance);
		PID.setInputRange(-25535, 25535); // set to max
		PID.setOutputRange(-1, 1);
	}

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return Robot.drive.getGyroAngle();
	}

	@Override
	protected void usePIDOutput(double output) {
		Robot.drive.drive(output, -output, enableGear, rampMotors);
	}

	@Override
	protected void initialize() {
		Robot.drive.reset();
		PID.enable();
		setTimeout(timeout);
		System.out.println("Auto turn for: " + timeout + "s with PID speed started");

	}

	@Override
	protected void execute() {
		Robot.drive.drive(speed, -speed, enableGear, rampMotors); // feeds the motor safety function
	}

	// using inherited functionality
	@Override
	protected boolean isFinished() {
		return isTimedOut() || PID.onTarget();
	}

	@Override
	protected void end() {
		PID.disable();
		Robot.drive.stop();
		System.out.println("Auto turn for: " + timeout + "s with PID ended");
	}

	@Override
	protected void interrupted() {
		System.out.println("Auto turn for: " + timeout + "s with PID interrupted");
		end();
	}

}
