package org.usfirst.frc.team6814.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Camera extends Subsystem {
	private UsbCamera camera;

	public Camera() {
		// this is called when the robot power button is pushed
		super();
		init();
		camera = CameraServer.getInstance().startAutomaticCapture();
		System.out.println("Camera Subsystem Started with automatic port");
	}

	public Camera(int cameraPort) {
		// this is called when the robot power button is pushed
		super();
		init();
		camera = CameraServer.getInstance().startAutomaticCapture(cameraPort);
		System.out.println("Camera Subsystem Started with specified port " + cameraPort);
	}

	private void init() {
		camera.setResolution(320, 240);
		camera.setFPS(20);
		camera.setBrightness(55);
		camera.setExposureManual(1);// Lower exposure reduces camera latency
		camera.setPixelFormat(PixelFormat.kMJPEG);

	}

	public void initDefaultCommand() {
//		setDefaultCommand( new ClimbStop());
	}

	public void log() {

	}

}
