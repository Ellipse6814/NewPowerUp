/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6814.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * The main autonomous command to pickup and deliver the soda to the box.
 */
public class Autonomous extends CommandGroup {
	public Autonomous() {
		addSequential(new AutoWait(5));
		addSequential(new DriveAutoStraightTime(3.5, 1.0, false, true));
	}
}
