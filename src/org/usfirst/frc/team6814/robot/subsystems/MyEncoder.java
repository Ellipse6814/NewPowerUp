package org.usfirst.frc.team6814.robot.subsystems;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MyEncoder {
	private Encoder encoder;
	private String name;
	private double gearboxRatio = 1;
	private final double convertToDistanceConst;

	public MyEncoder(String Name, int channelA, int channelB, boolean reverseDirection, EncodingType encodingType,
			int GearboxRatio, int wheelDiameter) {
		encoder = new Encoder(channelA, channelB, reverseDirection, encodingType);
		name = Name;
		gearboxRatio = GearboxRatio;
		convertToDistanceConst = wheelDiameter * Math.PI; // C= pi * D
		// messy settings that we don't care about:
		encoder.setMaxPeriod(0.2); // regard motor as stopped if no movement for 0.2 seconds
		encoder.setMinRate(1); // regard motor as stopped if distance per second < 1
		encoder.setDistancePerPulse(1); // the scaling constant that converts pulses into distance (we are not using
										// that, so set to 1)
		encoder.setSamplesToAverage(5); // used to reduce noise in period
		encoder.reset();
	}

	public double getPulse() {
		return encoder.get(); // encoder.get() already compensates for the EncodingType
	}

	public double getRotations() {
		return getPulse() * gearboxRatio;
	}

	public double getDistance() {
		return getRotations() * convertToDistanceConst;
	}

	public double getDistanceFrom(double startingDistance) {
		return getDistance() - startingDistance;
	}

	public void reset() {
		encoder.reset();
	}

	public void log() {
		SmartDashboard.putNumber("Encoder- " + name, getDistance());
	}

	public Encoder get() {
		return encoder;
	}

}
