/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6814.robot.commands;

import Enum.FieldPos;
import Enum.RobotStartingPos;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Autonomous extends CommandGroup {
	public Autonomous(RobotStartingPos robotPos, FieldPos gameData) {
		if (robotPos == RobotStartingPos.M) { // M
			if (gameData == FieldPos.L) {

			} else if (gameData == FieldPos.R) {
				addSequential(new DriveAutoStraightPID(setpoint, p, i, d, turningP, tolerance, speed, enableGear, rampMotors));
			}
		} else if (robotPos == RobotStartingPos.L) { // L
			if (gameData == FieldPos.L) {

			} else if (gameData == FieldPos.R) {

			}
		} else if (robotPos == RobotStartingPos.R) { // R
			if (gameData == FieldPos.L) {

			} else if (gameData == FieldPos.R) {

			}
		}

	}

	public Autonomous() {
		addSequential(new AutoWait(5));// time in seconds
		addSequential(new DriveAutoStraightTime(1.6, 0.7, false, true)); // time, speed, gears, ramp
	}

}

//	addSequential(new IntakeOut(), 1.5);// you can set timeouts for commands by adding a double after it 
//parallel commands parallel the next command (the next scheduler ignores the parallel command's finish and starts the next 
//command right after the start of the parallel command) 
