/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6814.robot;

import org.usfirst.frc.team6814.robot.commands.ClimbDown;
import org.usfirst.frc.team6814.robot.commands.ClimbUp;
import org.usfirst.frc.team6814.robot.commands.DriveGearDown;
import org.usfirst.frc.team6814.robot.commands.DriveGearUp;
import org.usfirst.frc.team6814.robot.commands.DriveTeleDPad;
import org.usfirst.frc.team6814.robot.commands.IntakeIn;
import org.usfirst.frc.team6814.robot.commands.IntakeOut;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI { // stands for Operator Interface
	private Joystick doubleJoystick = new Joystick(0);
	private Joystick singleJoystick = new Joystick(1);

//	// Create some buttons
	// double joystick
	JoystickButton intakeOut = new JoystickButton(doubleJoystick, 4);
	JoystickButton intakeIn = new JoystickButton(doubleJoystick, 1);

	JoystickButton gearUp = new JoystickButton(doubleJoystick, 6);
	JoystickButton gearDown = new JoystickButton(doubleJoystick, 5);

	JoystickButton climbUp = new JoystickButton(doubleJoystick, 3);
	JoystickButton climbDown = new JoystickButton(doubleJoystick, 2);

	// -----------------------------------------------------------
	// single joystick
	JoystickButton intakeIn1 = new JoystickButton(singleJoystick, 2);
	JoystickButton intakeOut1 = new JoystickButton(singleJoystick, 1);

	public OI() {
		// Put Some buttons on the SmartDashboard
//		SmartDashboard.putData("Elevator Up", new SetElevatorSetpoint(0));
//		SmartDashboard.putData("Elevator Down", new SetElevatorSetpoint(0.2));
//		SmartDashboard.putData("Elevator Stop", new SetElevatorSetpoint(0.3));
//
//		SmartDashboard.putData("Wrist Horizontal", new SetWristSetpoint(0));
//		SmartDashboard.putData("Raise Wrist", new SetWristSetpoint(-45));
//
//		SmartDashboard.putData("Open Claw", new OpenClaw());
//		SmartDashboard.putData("Close Claw", new CloseClaw());
//
//		SmartDashboard.putData("Deliver Soda", new Autonomous());
//

//		JoystickButton dpadUp = new JoystickButton(m_joystick, 5);
//		JoystickButton dpadRight = new JoystickButton(m_joystick, 6);
//		JoystickButton dpadDown = new JoystickButton(m_joystick, 7);
//		JoystickButton dpadLeft = new JoystickButton(m_joystick, 8);
//		JoystickButton l2 = new JoystickButton(m_joystick, 9);
//		JoystickButton r2 = new JoystickButton(m_joystick, 10);
//		JoystickButton l1 = new JoystickButton(m_joystick, 11);
//		JoystickButton r1 = new JoystickButton(m_joystick, 12);
//
//		// Connect the buttons to commands
		intakeOut.whileHeld(new IntakeOut());
		intakeIn.whileHeld(new IntakeIn());

		gearUp.whenPressed(new DriveGearUp());
		gearDown.whenPressed(new DriveGearDown());

		climbUp.whileHeld(new ClimbUp());
		climbDown.whileHeld(new ClimbDown());

		// ----------------------------------------------

		intakeIn1.whileHeld(new IntakeIn());
		intakeOut1.whileHeld(new IntakeOut());

//		dpadUp.whenPressed(new SetElevatorSetpoint(0.2));
//		dpadDown.whenPressed(new SetElevatorSetpoint(-0.2));
//		dpadRight.whenPressed(new CloseClaw());
//		dpadLeft.whenPressed(new OpenClaw());
//
//		r1.whenPressed(new PrepareToPickup());
//		r2.whenPressed(new Pickup());
//		l1.whenPressed(new Place());
//		l2.whenPressed(new Autonomous());
		System.out.println("Robot OI started");
	}

	public Joystick getDoubleJoystick() {
		return doubleJoystick;
	}
	public Joystick getSingleJoystick() {
		return singleJoystick;
	}
}
