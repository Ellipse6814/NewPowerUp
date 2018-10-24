/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveTele2Joy extends Command {
	public DriveTele2Joy() {
		requires(Robot.drive);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
//		double pov = Robot.oi.getJoystick().getPOV();
//		System.out.println(pov);
//		if (pov != 0) {
//			usePOV(pov);
//			System.out.println("using POV"+pov);
//			return;
//		}

		double power = -Robot.oi.getDoubleJoystick().getRawAxis(1);
		double turn = -Robot.oi.getDoubleJoystick().getRawAxis(4);

		if (Math.abs(power)<0.01) {
			power = 0;
		}
		if (Math.abs(turn)<0.01) {
			turn = 0;
		}
		
		Robot.drive.driveArcade(power, turn, true, true);
	}

	private void usePOV(double dir) {
		double l = 0, r = 0;
		if (dir == 0) {
			l = 1;
			r = 1;
			l *= .89;
			r *= .89;
		} else if (dir == 90) {
			l = -1;
			r = 1;
			l *= .89;
			r *= .89;
		} else if (dir == 180) {
			l = -1;
			r = -1;
			l *= .89;
			r *= .89;
		} else if (dir == 270) {
			l = 1;
			r = -1;
			l *= .89;
			r *= .89;
		} else if (dir == 360) {
			l = 1;
			r = 1;
			l *= .89;
			r *= .89;
		}
		Robot.drive.drive(l, r, true, true);
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
