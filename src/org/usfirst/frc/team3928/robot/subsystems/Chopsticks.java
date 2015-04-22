package org.usfirst.frc.team3928.robot.subsystems;

import org.usfirst.frc.team3928.robot.Constants;

import edu.wpi.first.wpilibj.Solenoid;

public class Chopsticks
{
	private Solenoid solenoidOpen;
	private Solenoid solenoidClose;
	
	Chopsticks()
	{
		solenoidOpen = new Solenoid(Constants.CHOPSTICKS_SOLENOID_OPEN.getInt());
		solenoidClose = new Solenoid(Constants.CHOPSTICKS_SOLENOID_CLOSE.getInt());
		
		open(true);
	}
	
	public void open(boolean open)
	{
		solenoidOpen.set(open);
		solenoidClose.set(!open);
	}
}
