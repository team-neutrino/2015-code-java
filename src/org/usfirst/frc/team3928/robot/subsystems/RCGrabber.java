package org.usfirst.frc.team3928.robot.subsystems;

import org.usfirst.frc.team3928.robot.Constants;

import edu.wpi.first.wpilibj.Solenoid;

public class RCGrabber
{
	private Solenoid solenoidUp;
	private Solenoid solenoidDown;
	
	public RCGrabber()
	{
		solenoidUp = new Solenoid(Constants.RC_GRABBER_SOLENOID_UP.getInt());
		solenoidDown = new Solenoid(Constants.RC_GRABBER_SOLENOID_DOWN.getInt());
	}
	
	public void deploy(boolean deploy)
	{
		solenoidUp.set(deploy);
		solenoidDown.set(!deploy);
	}
}
