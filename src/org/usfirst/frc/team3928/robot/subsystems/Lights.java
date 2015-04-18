package org.usfirst.frc.team3928.robot.subsystems;

import org.usfirst.frc.team3928.robot.Constants;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;

public class Lights
{
	private Relay relay = new Relay(Constants.LIGHTS_CHANNEL.getInt());
	
	public void set(boolean on)
	{
		relay.set(on ? Value.kReverse : Value.kOff);
	}
}
