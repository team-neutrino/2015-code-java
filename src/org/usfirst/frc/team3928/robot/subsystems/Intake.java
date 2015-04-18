package org.usfirst.frc.team3928.robot.subsystems;

import org.usfirst.frc.team3928.robot.Constants;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

public class Intake
{
	private Victor motorLeft;
	private Victor motorRight;
	private Solenoid solenoidOpen;
	private Solenoid solenoidClose;

	public Intake()
	{
		motorLeft = new Victor(Constants.INTAKE_MOTOR_LEFT_CHANNEL.getInt());
		motorRight = new Victor(Constants.INTAKE_MOTOR_RIGHT_CHANNEL.getInt());
		solenoidOpen = new Solenoid(
				Constants.INTAKE_SOLENOID_OPEN_CHANNEL.getInt());
		solenoidClose = new Solenoid(
				Constants.INTAKE_SOLENOID_CLOSE_CHANNEL.getInt());
	}

	public void setLeft(double speed)
	{
		motorLeft.set(-speed);
	}

	public void setRight(double speed)
	{
		motorRight.set(speed);
	}

	public void stop()
	{
		motorLeft.set(0);
		motorRight.set(0);
	}

	public void open(boolean open)
	{
		solenoidOpen.set(open);
		solenoidClose.set(!open);
	}
}
