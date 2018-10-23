package org.usfirst.frc.team6814.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Camera extends Subsystem {
	private UsbCamera camera;

	public Camera(int cameraPort) {
		// this is called when the robot power button is pushed
		super();

		try {
			camera = CameraServer.getInstance().startAutomaticCapture(cameraPort);
			init();
			System.out.println("Camera Subsystem Started with specified port " + cameraPort);

		} catch (Exception e) {
			System.out.println("ERROR: Camera on port " + cameraPort + " failed to start");
		}
	}

	private void init() {
		camera.setResolution(320, 240);
		camera.setFPS(30);
		camera.setBrightness(100);
		camera.setExposureManual(2);// Lower exposure reduces camera latency
		camera.setPixelFormat(PixelFormat.kMJPEG);

	}

	public void initDefaultCommand() {
//		setDefaultCommand( new ClimbStop());
	}

	public void log() {

	}

}
