/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6814.robot;

import org.usfirst.frc.team6814.robot.Enum.FieldPos;
import org.usfirst.frc.team6814.robot.Enum.RobotStartingPos;
import org.usfirst.frc.team6814.robot.commands.Autonomous;
import org.usfirst.frc.team6814.robot.subsystems.Camera;
import org.usfirst.frc.team6814.robot.subsystems.Climb;
import org.usfirst.frc.team6814.robot.subsystems.Drive;
import org.usfirst.frc.team6814.robot.subsystems.Elevator;
import org.usfirst.frc.team6814.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot { // updates code every 20ms (50 times/second)

	public static Drive drive;
	public static Elevator elevator;
	public static Intake intake;
	public static Climb climb;
	public static Camera camera;
	public static OI oi;

	private Command autonomous;
	private static RobotStartingPos pos = RobotStartingPos.Middle;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		// Initialize all subsystems
		drive = new Drive();
		elevator = new Elevator();
		intake = new Intake();
		climb = new Climb();
		camera = new Camera(0);
		oi = new OI();
	}

	@Override
	public void autonomousInit() {
		System.out.println("Auton Init");
		if (pos == null) {
			autonomous = new Autonomous();
		} else {
			String gameData = DriverStation.getInstance().getGameSpecificMessage();
			System.out.println("GameData: " + gameData);
			if (gameData.length() > 0) {
				if (gameData.charAt(0) == 'L') {
					autonomous = new Autonomous(pos, FieldPos.Left, true);
				} else {
					autonomous = new Autonomous(pos, FieldPos.Right, true);
				}
			}
		}
		autonomous.start(); // schedule the autonomous command
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		log();
	}

	@Override
	public void teleopInit() {
		if (autonomous != null) {
			autonomous.cancel();
		}
		System.out.println("TeleopInit");
	}

	/**
	 * This function is called periodically during teleoperated mode.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		log();
		// System.out.println(drive.gyro.getAngle());
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		Scheduler.getInstance().run();
		log();
	}

	/**
	 * The log method puts interesting information to the SmartDashboard.
	 */
	private void log() {
		drive.log();
		elevator.log();
		climb.log();
		intake.log();
		camera.log();
	}
}