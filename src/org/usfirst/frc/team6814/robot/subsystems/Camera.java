package org.usfirst.frc.team6814.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.CameraServer;

public class Camera {
	private UsbCamera camera;

	public Camera(int cameraPort) {
		// this is called when the robot power button is pushed
		super();

		try {
			camera = CameraServer.getInstance().startAutomaticCapture(cameraPort);
			camera.setResolution(320, 240);
			camera.setFPS(20);
			camera.setBrightness(90);
			camera.setExposureManual(1);// Lower exposure reduces camera latency
			camera.setPixelFormat(PixelFormat.kMJPEG);
			System.out.println("Camera Subsystem Started with specified port " + cameraPort);

		} catch (Exception e) {
			System.out.println("ERROR: Camera on port " + cameraPort + " failed to start");
		}
	}

	public void log() {

	}

}
