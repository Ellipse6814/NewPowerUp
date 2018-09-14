package org.usfirst.frc.team6814.robot.subsystems;

import org.usfirst.frc.team6814.robot.commands.IntakeStop;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends Subsystem {

	private int status = 0; // 1:out -1:in 0:stop
	private Spark leftIntake = new Spark(4);
	private Spark rightIntake = new Spark(5);

	public Intake() {
		super();


		// Let's name everything on the LiveWindow
		addChild("Left Motor", leftIntake);
		addChild("Right Motor", rightIntake);
		
		System.out.println("Intake Subsystem Started");
	}

	@Override
	protected void initDefaultCommand() {
//		setDefaultCommand(new IntakeStop());
	}

	// actions

	public void in() {
		leftIntake.set(-1);
		rightIntake.set(1);
		status = -1;
	}

	public void out() {
		leftIntake.set(1);
		rightIntake.set(-1);
		status = 1;
	}

	public void stop() {
		leftIntake.set(0);
		rightIntake.set(0);
		status = 0;
	}

	public int status() {
		return status;
	}

	public void log() {
		if (status == 1)
			SmartDashboard.putString("Intake", "Out");
		else if (status == -1)
			SmartDashboard.putString("Intake", "In");
		else
			SmartDashboard.putString("Intake", "Stopped");
	}

}
