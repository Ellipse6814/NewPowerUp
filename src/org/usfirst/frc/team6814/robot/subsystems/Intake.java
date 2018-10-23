package org.usfirst.frc.team6814.robot.subsystems;

import org.usfirst.frc.team6814.robot.Constants;
import org.usfirst.frc.team6814.robot.commands.IntakeStop;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends Subsystem {

	private double speed = 0; // +: out, -: in
	private Spark leftIntake = new Spark(Constants.kIntakeLeftMotorPort);
	private Spark rightIntake = new Spark(Constants.kIntakeRightMotorPort);

	public Intake() {
		super();

		System.out.println("Intake Subsystem Started");

	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new IntakeStop());
	}

	// actions

	public void in(double spd) {
		leftIntake.set(-spd);
		rightIntake.set(-spd);
		speed = -spd;
	}

	public void out(double spd) {
		leftIntake.set(spd);
		rightIntake.set(spd);
		speed = spd;
	}

	public void stop() {
		leftIntake.set(0);
		rightIntake.set(0);
		speed = 0;
	}

	// ---------------------------------

	public double getSpeed() {
		return speed;
	}

	public void log() {
		SmartDashboard.putNumber("Intake", speed);

		// in test mode, we can directly see AND MODIFY values from these objects
		addChild("Intake L Motor", leftIntake);
		addChild("Intake R Motor", rightIntake);
	}

	protected void end() {
		stop();
		System.out.println("Intake Subsystem Stopped");
	}

}
