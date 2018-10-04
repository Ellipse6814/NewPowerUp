/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Have the robot drive tank style using the PS3 Joystick until interrupted.
 */
public class ElevatorTeleSingleJoystick extends Command {
	public ElevatorTeleSingleJoystick() {
		requires(Robot.elevator);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double power = Robot.oi.getSingleJoystick().getRawAxis(1);
		power *= -1;

		Robot.elevator.setMotor(power);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false; // Runs until interrupted
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.drive.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

}
