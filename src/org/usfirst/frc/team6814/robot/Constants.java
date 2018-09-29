package org.usfirst.frc.team6814.robot;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;

public class Constants {
	// This is a class for global static constants for the robot.

	// Elevator

	public static final int kElevatorMotorPort = 6; // PWM

	public static final double kElevatorKp = 0;
	public static final double kElevatorKi = 0;
	public static final double kElevatorKd = 0;
	public static final double kElevatorMaxHeightByPID = 2.44;// meters
	public static final double kElevatorPIDTolerance = 0.01;// meters
	public static final int kElevatorEncoderReduceNoiseAverageSampleNum = 5;

	public static final int kElevatorEncoderChannelA = 1;
	public static final int kElevatorEncoderChannelB = 0;
	public static final boolean kElevatorEncoderReversed = false;
	public static final EncodingType kElevatorEncoderEncodingType = EncodingType.k4X;
	public static final double kElevatorEncoderRegardStop = 0.2; // seconds

	public static final double kElevatorGearboxRatio = 1 / 49; // output rotations with one motor rotation
	public static final double kElevatorShaftDiameter = 0.02; // meters
	public static final double kElevatorPulse2Distance = (kElevatorGearboxRatio) * (Math.PI * kElevatorShaftDiameter);

	//
	// Intake

	public static final int kIntakeLeftMotorPort = 4;
	public static final int kIntakeRightMotorPort = 5;

	public static final double kIntakeLeftSpeed = 1;
	public static final double kIntakeRightSpeed = 1;

	//
	// Drive
	
	public static final int kDriveLeftFrontMotorPort = 0;
	public static final int kDriveLeftBackMotorPort = 1;
	public static final int kDriveRightFrontMotorPort = 2;
	public static final int kDriveRightBackMotorPort = 3;

	

	// static PID constants:
	// static acceleration, current limits
	// static motor, sensor ports:
	// static field parameters:
}
