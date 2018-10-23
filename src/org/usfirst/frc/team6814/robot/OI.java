/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6814.robot;

import org.usfirst.frc.team6814.robot.commands.Autonomous;
import org.usfirst.frc.team6814.robot.commands.DriveAutoStraightEncoderPID;
import org.usfirst.frc.team6814.robot.commands.DriveAutoTurnInEllipsePID;
import org.usfirst.frc.team6814.robot.commands.DriveAutoTurnInEllipseTime;
import org.usfirst.frc.team6814.robot.commands.DriveGearDown;
import org.usfirst.frc.team6814.robot.commands.DriveGearUp;
import org.usfirst.frc.team6814.robot.commands.DriveTeleToggleInverted;
import org.usfirst.frc.team6814.robot.commands.ElevatorSetSpeed;
import org.usfirst.frc.team6814.robot.commands.IntakeIn;
import org.usfirst.frc.team6814.robot.commands.IntakeOut;
import org.usfirst.frc.team6814.robot.commands.IntakeStop;

import Enum.FieldPos;
import Enum.RobotStartingPos;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI { // stands for Operator Interface

	private Joystick doubleJoystick = new Joystick(0);
	private Joystick singleJoystick = new Joystick(1);

	public OI() {
		// // Create some buttons
		// double joystick
		JoystickButton intakeOut = new JoystickButton(doubleJoystick, 4);
		JoystickButton intakeIn = new JoystickButton(doubleJoystick, 1);

		JoystickButton gearUp = new JoystickButton(doubleJoystick, 6);
		JoystickButton gearDown = new JoystickButton(doubleJoystick, 5);

//		JoystickButton climbUp = new JoystickButton(doubleJoystick, 3);
//		JoystickButton climbDown = new JoystickButton(doubleJoystick, 2);

		JoystickButton driveForwards = new JoystickButton(doubleJoystick, 3);
		JoystickButton driveBackwards = new JoystickButton(doubleJoystick, 2);

		// -----------------------------------------------------------
		// single joystick
		JoystickButton intakeIn1 = new JoystickButton(singleJoystick, 2);
		JoystickButton intakeOut1 = new JoystickButton(singleJoystick, 1);

		JoystickButton intakeIn2 = new JoystickButton(singleJoystick, 11); // fast intake
		JoystickButton intakeOut2 = new JoystickButton(singleJoystick, 7);

		JoystickButton intakeIn3 = new JoystickButton(singleJoystick, 12); // slow intake
		JoystickButton intakeOut3 = new JoystickButton(singleJoystick, 8);
//		// Connect the buttons to commands

		gearUp.whenPressed(new DriveGearUp());
		gearDown.whenPressed(new DriveGearDown());

		intakeOut.whileHeld(new IntakeOut(1.0));
		intakeIn.whileHeld(new IntakeIn(1.0));

		// ----------------------------------------------

		intakeIn1.whileHeld(new IntakeIn(1.0));
		intakeOut1.whileHeld(new IntakeOut(1.0));

		intakeIn2.whileHeld(new IntakeIn(1.0));
		intakeOut3.whileHeld(new IntakeOut(1.0));

		intakeIn3.whileHeld(new IntakeIn(0.6));
		intakeOut3.whileHeld(new IntakeOut(0.6));

//		climbUp.whileHeld(new ClimbUp());//not used
//		climbDown.whileHeld(new ClimbDown());

		driveForwards.whenPressed(new DriveTeleToggleInverted(false));
		driveBackwards.whenPressed(new DriveTeleToggleInverted(true));

		// put these controls on the SmartDashboard so we can control them with a mouse
		SmartDashboard.putData("Intake In Fast", new IntakeIn(1.0));
		SmartDashboard.putData("Intake In Slow", new IntakeIn(0.6));
		SmartDashboard.putData("Intake Out Fast", new IntakeOut(1.0));
		SmartDashboard.putData("Intake Out Slow", new IntakeOut(0.6));
		SmartDashboard.putData("Intake Stop", new IntakeStop());

		SmartDashboard.putData("Elev Up", new ElevatorSetSpeed(0.5));
		SmartDashboard.putData("Elev Down", new ElevatorSetSpeed(-0.5));
		SmartDashboard.putData("Elev Stop", new ElevatorSetSpeed(0.5));

		SmartDashboard.putData("Run Auto Line", new Autonomous());
		SmartDashboard.putData("Run Auto M-R", new Autonomous(RobotStartingPos.Middle, FieldPos.Right));
		SmartDashboard.putData("Run Auto M-L", new Autonomous(RobotStartingPos.Middle, FieldPos.Left));
		SmartDashboard.putData("Run Auto L-R", new Autonomous(RobotStartingPos.Left, FieldPos.Right));
		SmartDashboard.putData("Run Auto L-L", new Autonomous(RobotStartingPos.Left, FieldPos.Left));
		SmartDashboard.putData("Run Auto R-R", new Autonomous(RobotStartingPos.Middle, FieldPos.Right));
		SmartDashboard.putData("Run Auto R-L", new Autonomous(RobotStartingPos.Middle, FieldPos.Left));

		
		// setpoint kp ki kd speed tolerance gear ramp
		SmartDashboard.putData("Drive Turn PID", new DriveAutoTurnInEllipsePID(90, 0.7, 2));

		SmartDashboard.putData("Drive Turn Time", new DriveAutoTurnInEllipseTime(2, 0.7));

		SmartDashboard.putData("Drive Straight PID", new DriveAutoStraightEncoderPID(10, 0.1, 0.7));
//double setpoint, double p, double i, double d, double turningP, double tolerance, double speed, boolean enableGear, boolean rampMotors

		System.out.println("Robot OI Started");
	}

	public Joystick getDoubleJoystick() {
		return doubleJoystick;
	}

	public Joystick getSingleJoystick() {
		return singleJoystick;
	}
}
