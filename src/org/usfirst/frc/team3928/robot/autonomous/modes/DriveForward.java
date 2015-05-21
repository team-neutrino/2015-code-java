package org.usfirst.frc.team3928.robot.autonomous.modes;

import org.usfirst.frc.team3928.robot.autonomous.AutonomousDriver;
import org.usfirst.frc.team3928.robot.autonomous.AutonomousMode;

public class DriveForward implements AutonomousMode
{
	AutonomousDriver driver;
	
	public DriveForward(AutonomousDriver driver)
	{
		this.driver = driver;
	}
	
	@Override
	public String getName()
	{
		return "Drive Forward";
	}

	@Override
	public void run() throws InterruptedException
	{
		driver.moveDistance(1);
	}

}
