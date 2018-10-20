package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.Constants;
import org.usfirst.frc.team6814.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class DriveAutoTurnInEllipsePID extends PIDCommand {

	private double speed;
	private boolean enableGear;
	private boolean rampMotors;
	private double tolerance;
	private double setpoint;
	private PIDController PID;

	public DriveAutoTurnInEllipsePID(double setpoint, double tolerance, double speed) {
		this(setpoint, tolerance, speed, false, false);
	}

	public DriveAutoTurnInEllipsePID(double setpoint, double tolerance, double speed, boolean enableGear,
	        boolean rampMotors) {
		super(Constants.kDriveTurnPIDkP, Constants.kDriveTurnPIDkI, Constants.kDriveTurnPIDkD);
		requires(Robot.drive);
		this.tolerance = tolerance;
		this.enableGear = enableGear;
		this.rampMotors = rampMotors;
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
		return Robot.drive.getGyroAngle();
	}

	@Override
	protected void usePIDOutput(double output) {
		Robot.drive.drive(output, -output, enableGear, rampMotors); // TODO: is this the right direction?
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
