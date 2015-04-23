package org.usfirst.frc.team3928.robot.autonomous;

import org.usfirst.frc.team3928.robot.Constants;
import org.usfirst.frc.team3928.robot.Utils;
import org.usfirst.frc.team3928.robot.sensors.ThumbwheelSwitch;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousSwitcher implements Runnable
{
	private AutonomousMode[] modes;
	private ThumbwheelSwitch selectorSwitch;

	private Thread autoThread;

	private Thread monitorThread;

	private final int MONTITOR_REFRESH_RATE = 5000;

	private int currentMode;

	public AutonomousSwitcher()
	{
		modes = new AutonomousMode[10];
		selectorSwitch = new ThumbwheelSwitch(
				Constants.AUTO_SWITCH_INPUT_1_CHANNEL.getInt(),
				Constants.AUTO_SWITCH_INPUT_2_CHANNEL.getInt(),
				Constants.AUTO_SWITCH_INPUT_4_CHANNEL.getInt(),
				Constants.AUTO_SWITCH_INPUT_8_CHANNEL.getInt());

		autoThread = new Thread(this);

		currentMode = getMode();

		SmartDashboard.putBoolean("Auto Switch Override", false);
		SmartDashboard.putNumber("Auto Switch Override Number", 0);

		monitorThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				int mode;
				int modePrev = 0;

				long lastRefresh = System.currentTimeMillis()
						+ MONTITOR_REFRESH_RATE;

				while (true)
				{
					mode = getMode();
					long currTime = System.currentTimeMillis();

					if (mode != modePrev
							|| (currTime - lastRefresh) >= MONTITOR_REFRESH_RATE)
					{
						lastRefresh = currTime;

						SmartDashboard.putNumber("Autonomous Mode", mode);
						SmartDashboard.putString("Autonomous Mode Description",
								modes[mode].getName());
					}

					Thread.yield();
				}
			}
		});

		monitorThread.start();
	}

	public void assignMode(AutonomousMode mode, int num)
	{
		modes[num] = mode;
	}

	@SuppressWarnings("deprecation")
	public void runAuto()
	{
		currentMode = getMode();

		if (modes[currentMode] == null)
		{
			Utils.sendDSError("Auto [" + currentMode + "] not implemented");
			return;
		}

		if (autoThread.isAlive())
		{
			Utils.sendDSError("Could not start Auto: is already running");
			return;
		}

		autoThread.start();

		Utils.sendDSError("Auto [" + currentMode + "]: "
				+ modes[currentMode].getName());

		while (DriverStation.getInstance().isAutonomous()
				&& DriverStation.getInstance().isEnabled())
		{
			Thread.yield();
		}

		// verify that the thread has quit
		if (!autoThread.isAlive())
		{
			return;
		}

		// the thread has not quit, try interrupting
		autoThread.interrupt();

		// wait a little bit
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
		}

		// verify that the thread has quit
		if (!autoThread.isAlive())
		{
			return;
		}

		// the thread still has not quit, force stop it
		autoThread.stop();
		Utils.sendDSError("Auto thread force stopped");
	}

	@Override
	public void run()
	{
		try
		{
			modes[currentMode].run();
		} catch (InterruptedException e)
		{
		}

		if (!DriverStation.getInstance().isAutonomous()
				|| !DriverStation.getInstance().isEnabled())
		{
			Utils.sendDSError("Autonomous Interupted");
		}
	}

	private int getMode()
	{
		int ret;
		
		if (SmartDashboard.getBoolean("Auto Switch Override", false))
		{
			ret = (int) SmartDashboard.getNumber(
					"Auto Switch Override Number", 0);
		} else
		{
			ret = selectorSwitch.read();
		}

		return Math.max(0, Math.min(9, ret));
	}
}
