/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * Drive the given distance straight (negative values go backwards). Uses a
 * local PID controller to run a simple PID loop that is only enabled while this
 * command is running. The input is the averaged values of the left and right
 * encoders.
 */
public class ElevatorAutoTime extends TimedCommand {
	private double speed;
	private double timeInSec;

	public ElevatorAutoTime(double TimeInSec, double speed) {
		super(TimeInSec); // timeout seconds: (this functionality is built-in to the TimedCommnand base
							// class)
		requires(Robot.elevator);
		this.speed = speed;
		timeInSec = TimeInSec;
	}

	@Override
	protected void initialize() {
		Robot.elevator.setMotor(speed);
		System.out.println("Auto elevator for: "+ timeInSec +"s with "+speed+" speed started");

	}

	@Override
	protected void execute() {
		Robot.elevator.setMotor(speed);// feeds the motor safety function
	}

	// using inherited functionality
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	@Override
	protected void end() {
		// Stop PID and the wheels
		Robot.elevator.stop();
		System.out.println("Auto elevator for: "+ timeInSec +"s with "+speed+" speed ended");
	}

	@Override
	protected void interrupted() {
		System.out.println("Auto elevator for: "+ timeInSec +"s with "+speed+" speed interrupted");
		end();
	}
}
