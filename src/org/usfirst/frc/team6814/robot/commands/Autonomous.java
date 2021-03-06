/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.Enum.FieldPos;
import org.usfirst.frc.team6814.robot.Enum.RobotStartingPos;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Autonomous extends CommandGroup {

	private static double sT = 0.05; // Drive Straight Tolerance
	private static double tT = 2; // Turning Tolerance
	private static double sS = 0.5; // Straight Max Speed
	private static double tS = 0.47; // Turning Max Speed
//	private static double et = 2.5; // Elevator Time for Switch

	public Autonomous(RobotStartingPos robotPos, FieldPos gameData, boolean useEncoders) {
//		System.out.println("Auton Command Group Inited");
		if (robotPos == RobotStartingPos.Middle) { // M (to the right)
			if (gameData == FieldPos.Left) {
				addParallel(new IntakeIn(0.2));
				addParallel(new ElevatorSetSpeed(1), 0.3);
				
				addSequential(new DriveAutoStraightEncoderPID(0.7, sT, sS), 3);
				addSequential(new DriveAutoTurnInEllipsePID(-80, tT, tS), 3);
				addSequential(new DriveAutoStraightEncoderPID(2.5, sT, sS), 3);
				addSequential(new DriveAutoTurnInEllipsePID(80, tT, tS), 3);
				
				addSequential(new ElevatorSetSpeed(1), 2);
				
				addSequential(new DriveAutoStraightEncoderPID(2.86, sT, sS), 3); //forward distance should add to 3.56m
				addSequential(new IntakeOut(0.7), 1);
				
				
			} else if (gameData == FieldPos.Right) {
				addParallel(new IntakeIn(0.2));
				addSequential(new ElevatorSetSpeed(1), 2.3);
				
				addSequential(new DriveAutoStraightEncoderPID(3.56, sT, sS), 3);
				addSequential(new IntakeOut(0.7), 1);
			}

		} else if (robotPos == RobotStartingPos.Left) { // L
			if (gameData == FieldPos.Left) {
				addParallel(new IntakeIn(0.2));
				addParallel(new ElevatorSetSpeed(1), 0.3);
				
				addSequential(new DriveAutoStraightEncoderPID(4, sT, sS),3);
				addSequential(new DriveAutoTurnInEllipsePID(80, tT, tS), 3);
				
				addSequential(new ElevatorSetSpeed(1), 2);
				
				addSequential(new DriveAutoStraightEncoderPID(1, sT, sS), 1.5);
				addSequential(new IntakeOut(0.7), 1);
				
				
			} else if (gameData == FieldPos.Right) {
				addSequential(new AutoWait(5));// time in seconds
				addSequential(new DriveAutoStraightTime(1.6, sS)); // time, speed, gears, ramp
			}

		} else if (robotPos == RobotStartingPos.Right) { // R
			if (gameData == FieldPos.Left) {
				addSequential(new AutoWait(5));// time in seconds
				addSequential(new DriveAutoStraightTime(1.6, sS)); // time, speed, gears, ramp
				
				
			} else if (gameData == FieldPos.Right) {
				addParallel(new IntakeIn(0.2));
				addParallel(new ElevatorSetSpeed(1), 0.3);
				
				addSequential(new DriveAutoStraightEncoderPID(4, sT, sS),3);
				addSequential(new DriveAutoTurnInEllipsePID(-80, tT, tS), 3);
				
				addSequential(new ElevatorSetSpeed(1), 2);
				
				addSequential(new DriveAutoStraightEncoderPID(1, sT, sS), 1.5);
				addSequential(new IntakeOut(0.7), 1);
			}

		}

	}

	public Autonomous() {
		addSequential(new AutoWait(5));// time in seconds
		addSequential(new DriveAutoStraightTime(1.6, 0.7)); // time, speed, gears, ramp
	}

}

//	addSequential(new IntakeOut(), 1.5);// you can set timeouts for commands by adding a double after it 
//parallel commands parallel the next command (the next scheduler ignores the parallel command's finish and starts the next 
//command right after the start of the parallel command) 
