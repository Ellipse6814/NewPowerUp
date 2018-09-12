package org.usfirst.frc.team6814.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climb extends Subsystem{
	private Spark motor = new Spark(5);
	
	public Climb() {
		//this is called when the robot power button is called
		super();
	}
	public void up() {
		motor.set(1);
	}

	public void down() {
		motor.set(-1);
	}

	public void stop() {
		motor.set(0);
	}

	
	public void initDefaultCommand() {
//		setDefaultCommand( new ClimbStop());
	}

	public void setMotor(double speed) {
		//speed is actually voltage
		motor.set(speed);
	}
	public void log() {
		
	}


}
