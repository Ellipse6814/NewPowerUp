/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6814.robot.subsystems;

import org.usfirst.frc.team6814.robot.Constants;
import org.usfirst.frc.team6814.robot.commands.ElevatorTeleShoulder;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends PIDSubsystem {

	private Spark motor = new Spark(Constants.kElevatorMotorPort);
	private double motorSpeed = 0;
	private Encoder encoder;

	public Elevator() {
		super("Elevator PIDSubsystem", Constants.kElevatorKp, Constants.kElevatorKi, Constants.kElevatorKd);
		initEncoder();
		initPID();
		System.out.println("Elevator Subsystem Started");
	}

	public void initDefaultCommand() {
		setDefaultCommand(new ElevatorTeleShoulder());
	}

	private void initEncoder() {
		encoder = new Encoder(Constants.kElevatorEncoderChannelA, Constants.kElevatorEncoderChannelA,
				Constants.kElevatorEncoderReversed, Constants.kElevatorEncoderEncodingType);
		encoder.setMaxPeriod(Constants.kElevatorEncoderRegardStop); // regard motor as stopped if no movement for 0.2
																	// seconds
//		encoder.setMinRate(1); // regard motor as stopped if distance per second < 1
		// gearbox ratio 1:49; 0.5 inch changing diameter TODO;
//		final double PulseToDistanceConst = (1 / 49) * Math.PI * 0.02; // rotations -> meters
		encoder.setDistancePerPulse(Constants.kElevatorPulse2Distance); // the scaling constant that converts pulses
																		// into distance
		encoder.setSamplesToAverage(Constants.kElevatorEncoderReduceNoiseAverageSampleNum); // used to reduce noise in
																							// period
		encoder.reset();
	}

	private void initPID() {
		setInputRange(0, Constants.kElevatorMaxHeightByPID); // set the min & max input value: 0m - 2.44m ()
		setOutputRange(-1, 1);
		setAbsoluteTolerance(Constants.kElevatorPIDTolerance);
		disablePID();
	}

	public void setMotor(double speed) {
		// speed is actually voltage
		disablePID();
		motor.set(speed);
		motorSpeed = speed;
	}

	public void stop() {
		disablePID();
		motor.set(0);
	}

	public double getEncoderDistance() {
		// this distance is actually a fake distance because the diameter of the shaft
		// changes
		return encoder.getDistance();
	}

	public void disablePID() {
		disable();
	}

	public void enablePID() {
		enable();
	}

	public void PIDsetpoint(double position) {
		setSetpoint(position);
		enablePID();
	}

	public void log() {
		// put log and smartDashboard things here
		SmartDashboard.putNumber("Elevator Motor Speed", motorSpeed);
		SmartDashboard.putNumber("Elevator PID Setpoint", getSetpoint());
		SmartDashboard.putNumber("Elevator Encoder", getEncoderDistance());
	}

	@Override
	protected double returnPIDInput() {
		return getEncoderDistance();
	}

	@Override
	protected void usePIDOutput(double output) {
		// add any post-processing here, such as motor ramps, and output clamps
//TODO:
		motor.set(output);
	}
}
