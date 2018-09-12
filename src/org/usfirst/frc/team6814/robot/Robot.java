/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6814.robot;

import org.usfirst.frc.team6814.robot.commands.Autonomous;
import org.usfirst.frc.team6814.robot.subsystems.Climb;
import org.usfirst.frc.team6814.robot.subsystems.Drive;
import org.usfirst.frc.team6814.robot.subsystems.Elevator;
import org.usfirst.frc.team6814.robot.subsystems.Intake;
import org.usfirst.frc.team6814.robot.subsystems.Light;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static Drive m_drive;
	public static Elevator m_elevator;
	public static Intake m_intake;
	public static Climb m_climb;
	public static Light m_light;
	public static OI m_oi;

	Command m_autonomousCommand;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		// Initialize all subsystems
		m_drive = new Drive();
		m_elevator = new Elevator();
		m_intake = new Intake();
		m_climb = new Climb();
		m_light = new Light();
		m_oi = new OI();

		// instantiate the command used for the autonomous period
		m_autonomousCommand = new Autonomous();
	}

	@Override
	public void autonomousInit() {
		m_autonomousCommand.start(); // schedule the autonomous command (example)
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
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		m_autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during teleoperated mode.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		log();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}

	/**
	 * The log method puts interesting information to the SmartDashboard.
	 */
	private void log() {
		m_drive.log();
		m_elevator.log();
		m_climb.log();
		m_intake.log();
	}
}