package org.usfirst.frc.team3928.robot.autonomous.modes;

import org.usfirst.frc.team3928.robot.autonomous.AutonomousMode;

public class DoNothing implements AutonomousMode
{

	@Override
	public String getName()
	{
		return "Do Nothing";
	}

	@Override
	public void run() throws InterruptedException
	{
	}

}
