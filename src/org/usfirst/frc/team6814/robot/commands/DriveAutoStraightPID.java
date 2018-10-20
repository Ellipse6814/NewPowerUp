package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class DriveAutoStraightPID extends PIDCommand {

	private double speed;
	private boolean enableGear;
	private boolean rampMotors;
	private double tolerance;
	private double turningP;
	private double setpoint;
	private PIDController PID;

	public DriveAutoStraightPID(double setpoint, double p, double i, double d, double turningP, double tolerance,
	        double speed, boolean enableGear, boolean rampMotors) {
		super(p, i, d);
		requires(Robot.drive);
		this.tolerance = tolerance;
		this.enableGear = enableGear;
		this.rampMotors = rampMotors;
		this.turningP = turningP;
		this.speed = speed;
		this.setpoint = setpoint;
		initPIDController();
	}

	private void initPIDController() {
		PID = getPIDController();
		PID.setAbsoluteTolerance(tolerance);
		PID.setInputRange(-25535, 25535); // set to somewhat max
		PID.setOutputRange(-speed, speed);
	}

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return Robot.drive.getEncoderRightDistance();
	}

	@Override
	protected void usePIDOutput(double output) {
		Robot.drive.drive(output, output + turningP * Robot.drive.getGyroAngle(), enableGear, rampMotors);
		// TODO: is this the right direction?
	}

	@Override
	protected void initialize() {
		Robot.drive.reset();
		PID.setSetpoint(setpoint);
		PID.enable();
		System.out.println("Auto turn with PID speed started");

	}

	@Override
	protected void execute() {
		Robot.drive.drive(speed, -speed, enableGear, rampMotors); // feeds the motor safety function
	}

	// using inherited functionality
	@Override
	protected boolean isFinished() {
		return PID.onTarget();
	}

	@Override
	protected void end() {
		PID.disable();
		Robot.drive.stop();
		System.out.println("Auto turn with PID ended");
	}

	@Override
	protected void interrupted() {
		System.out.println("Auto turn with PID interrupted");
		end();
	}

}
