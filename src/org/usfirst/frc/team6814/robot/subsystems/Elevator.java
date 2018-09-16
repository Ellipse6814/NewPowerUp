/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6814.robot.subsystems;

import org.usfirst.frc.team6814.robot.commands.ElevatorTeleShoulder;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends Subsystem {

	private Spark motor = new Spark(6);
	private double motorSpeed = 0;
	
public Elevator() {
	super();
	System.out.println("Elevator Subsystem Started");
}
	
	public void initDefaultCommand() {
		setDefaultCommand(new ElevatorTeleShoulder());
	}

	public void setMotor(double speed) {
		//speed is actually voltage
		motor.set(speed);
		motorSpeed = speed;
	}
	
	public void setPoint(double point) {
		//TODO: don't call me yet
		System.err.println("[ELEVATOR PID] THIS FEATURE IS NOT READY YET. NOTHING WILL BE DONE, THIS IS NOT AN ERROR.");
	}

	public void stop() {
		motor.set(0);
	}
	
	public void log() {
		//put log and smartDashboard things here
		SmartDashboard.putNumber("Elevator Motor Speed", motorSpeed);
	}

}
