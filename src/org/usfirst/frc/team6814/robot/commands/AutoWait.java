/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6814.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive the given distance straight (negative values go backwards). Uses a
 * local PID controller to run a simple PID loop that is only enabled while this
 * command is running. The input is the averaged values of the left and right
 * encoders.
 */
public class AutoWait extends Command {
	private double timeInSec;

	public AutoWait(double TimeInSec) {
		super(); // timeout seconds: (this functionality is built-in to the TimedCommnand base
		         // class)
		timeInSec = TimeInSec;
	}

	@Override
	protected void initialize() {
		setTimeout(timeInSec);
		System.out.println("Started auto wait for: " + timeInSec + "s");
	}

	@Override
	protected void execute() {
	}

	// using inherited functionality
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	@Override
	protected void end() {
		System.out.println("Auto wait for: " + timeInSec + "s finished");
	}

	@Override
	protected void interrupted() {
		System.out.println("Started auto wait for: " + timeInSec + "s was interrupted");
		end();
	}
}
