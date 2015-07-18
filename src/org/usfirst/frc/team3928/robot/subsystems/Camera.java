package org.usfirst.frc.team3928.robot.subsystems;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;

public class Camera implements Runnable
{
	int session;
	Image frame;

	Thread thread;

	public Camera()
	{
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

		// the camera name (ex "cam0") can be found through the roborio web
		// interface
		session = NIVision.IMAQdxOpenCamera("cam0",
				NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		NIVision.IMAQdxConfigureGrab(session);

		thread = new Thread(this);
	}

	public void startCapture()
	{
		thread.start();
	}

	@Override
	public void run()
	{
		NIVision.IMAQdxStartAcquisition(session);

		while (true)
		{

			NIVision.IMAQdxGrab(session, frame, 1);

			CameraServer.getInstance().setImage(frame);

			/** robot code here! **/
			Timer.delay(0.005); // wait for a motor update time
		}
	}
}
