package org.usfirst.frc.team3928.robot.subsystems;

import org.usfirst.frc.team3928.robot.Constants;

import edu.wpi.first.wpilibj.Victor;

public class Drive
{
	private Victor motorLeft1;
	private Victor motorLeft2;
	private Victor motorRight1;
	private Victor motorRight2;

	public Drive()
	{
		motorLeft1 = new Victor(Constants.DRIVE_LEFT_1_CHANNEL.getInt());
		motorLeft2 = new Victor(Constants.DRIVE_LEFT_2_CHANNEL.getInt());
		motorRight1 = new Victor(Constants.DRIVE_RIGHT_1_CHANNEL.getInt());
		motorRight2 = new Victor(Constants.DRIVE_RIGHT_2_CHANNEL.getInt());
	}

	public void setLeft(double speed)
	{
		motorLeft1.set(speed);
		motorLeft2.set(speed);
	}

	public void setRight(double speed)
	{
		motorRight1.set(-speed);
		motorRight2.set(-speed);
	}
}