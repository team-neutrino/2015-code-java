package org.usfirst.frc.team3928.robot.autonomous;

import org.usfirst.frc.team3928.robot.Constants;
import org.usfirst.frc.team3928.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;

public class AutonomousDriver
{
	private Drive driveInst;
	private Gyro gyroInst;
	private Encoder encoderInst;

	public AutonomousDriver(Drive drive)
	{
		driveInst = drive;
		gyroInst = new Gyro(Constants.GYRO_ANALOG_CHANNEL.getInt());
		encoderInst = new Encoder(Constants.ENCODER_CHANNEL_A.getInt(),
				Constants.ENCODER_CHANNEL_B.getInt());
	}

	public void turnDegrees(double degrees, double speed)
	{
		if (degrees == 0)
		{
			return;
		}

		gyroInst.reset();
		driveInst.setLeft(speed * (Math.abs(degrees) / degrees));
		driveInst.setRight(-speed * (Math.abs(degrees) / degrees));

		while (Math.abs(gyroInst.getAngle()) < Math.abs(degrees)
				&& DriverStation.getInstance().isAutonomous()
				&& DriverStation.getInstance().isEnabled())
		{
			Thread.yield();
		}

		driveInst.setLeft(0);
		driveInst.setRight(0);
	}

	public void turnDegrees(double degrees)
	{
		turnDegrees(degrees, Constants.AUTO_MOVE_SPEED.getDouble());
	}

	public void moveDistance(double feet, double speed)
			throws InterruptedException
	{
		if (Constants.USE_TIME.getBoolean())
		{
			moveTime(
					(int) (1000 * feet * Constants.FEET_TO_TIME_RATIO
							.getDouble()),
					speed);
		} else
		{
			moveEncoder(
					(int) (feet * Constants.ENCODER_TICK_PER_FOOT.getDouble()),
					speed);
		}
	}

	public void moveDistance(double feet) throws InterruptedException
	{
		moveDistance(feet, Constants.AUTO_MOVE_SPEED.getDouble());
	}

	public void moveTime(long milliseconds, double speed)
			throws InterruptedException
	{
		driveInst.setLeft(speed * (Math.abs(milliseconds) / milliseconds));
		driveInst.setRight(speed * (Math.abs(milliseconds) / milliseconds));
		Thread.sleep(milliseconds);
		driveInst.setLeft(0);
		driveInst.setRight(0);
	}

	public void moveTime(long milliseconds) throws InterruptedException
	{
		moveTime(milliseconds, Constants.AUTO_MOVE_SPEED.getDouble());
	}

	private void moveEncoder(int ticks, double speed)
	{
		driveInst.setLeft(speed * (Math.abs(ticks) / ticks));
		driveInst.setRight(speed * (Math.abs(ticks) / ticks));
		encoderInst.reset();
		while (Math.abs(encoderInst.get()) < Math.abs(ticks)
				&& DriverStation.getInstance().isAutonomous()
				&& DriverStation.getInstance().isEnabled())
		{
			Thread.yield();
		}
		driveInst.setLeft(0);
		driveInst.setRight(0);
	}
}
