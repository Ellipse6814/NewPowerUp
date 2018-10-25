package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.Constants;
import org.usfirst.frc.team6814.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class DriveAutoStraightEncoderPID extends PIDCommand {

	private double speed;
	private boolean enableGear;
	private boolean rampMotors;
	private double tolerance;
	private double turningP;
	private double setpoint;
	private double output;
	private PIDController PID;

	public DriveAutoStraightEncoderPID(double setpoint, double tolerance, double speed) {
		this(setpoint, tolerance, speed, false, false);
	}

	public DriveAutoStraightEncoderPID(double setpoint, double tolerance, double speed, boolean enableGear,
	        boolean rampMotors) {
		super(Constants.kDriveStraightPIDkP, Constants.kDriveStraightPIDkI, Constants.kDriveStraightPIDkD);
		requires(Robot.drive);
		this.tolerance = tolerance;
		this.enableGear = enableGear;
		this.rampMotors = rampMotors;
		this.turningP = Constants.kDriveStraightTurnPIDkP;
		this.speed = speed;
		this.setpoint = setpoint;
		this.output = 0;
	}

	private void initPIDController() {
		PID = getPIDController();
		PID.setAbsoluteTolerance(tolerance);
		PID.setInputRange(-25535, 25535); // set to somewhat max
		PID.setOutputRange(-speed, speed);
		PID.setSetpoint(setpoint);
		PID.enable();
	}

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return Robot.drive.getEncoderRightDistance();
	}

	@Override
	protected void usePIDOutput(double output) {
		this.output = output;
//		System.out.println("updating output");
//		System.out.println("PID " + output + "  " + (double) (output + turningP * Robot.drive.getGyroAngle())
//		        + enableGear + rampMotors);

	}

	@Override
	protected void initialize() {
		Robot.drive.reset();
		initPIDController();
		System.out.println("Auto turn with PID speed started with setpoint " + setpoint);

	}

	@Override
	protected void execute() {
//		System.out.println("executing");
		if (Robot.drive.getEncoderSafe()) {
			Robot.drive.drive(output, output - turningP * Robot.drive.getGyroAngle(), enableGear, rampMotors);
		}
	}

	// using inherited functionality
	@Override
	protected boolean isFinished() {
//		System.out.println(PID.onTarget() || !Robot.drive.getEncoderSafe());
		return PID.onTarget() || !Robot.drive.getEncoderSafe();
	}

	@Override
	protected void end() {
		PID.disable();
		Robot.drive.stop();
		if (!Robot.drive.getEncoderSafe()) {
			System.out.print("ENCODER UNSAFE: ");
		}
		System.out.println("Auto straight with PID ended");
	}

	@Override
	protected void interrupted() {
		System.out.println("Auto straight with PID interrupted");
		end();
	}

}
