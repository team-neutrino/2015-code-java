package org.usfirst.frc.team3928.robot.subsystems;

import org.usfirst.frc.team3928.robot.Constants;
import org.usfirst.frc.team3928.robot.Utils;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lift implements Runnable
{
	Victor liftMotor;
	DigitalInput beamBreak;
	DigitalInput limitSwitchBottom;
	DigitalInput limitSwitchTop;

	Chopsticks chopsticksInst;

	boolean isLifting;
	boolean overrideEnabled;
	boolean chopsticksOverrideEnabled;

	boolean newCommand;
	boolean resetCommand;
	int levelChangeCommand;

	private final int MONTITOR_REFRESH_RATE = 5000;

	Thread mainThread;
	Thread monitorThread;

	public Lift()
	{
		liftMotor = new Victor(Constants.LIFT_MOTOR_CHANNEL.getInt());

		beamBreak = new DigitalInput(Constants.BEAM_BREAK_CHANNEL.getInt());
		limitSwitchBottom = new DigitalInput(
				Constants.LIMIT_SWITCH_BOTTOM_CHANNEL.getInt());
		limitSwitchTop = new DigitalInput(
				Constants.LIMIT_SWITCH_TOP_CHANNEL.getInt());

		chopsticksInst = new Chopsticks();

		newCommand = false;
		resetCommand = false;
		levelChangeCommand = 0;

		mainThread = new Thread(this);
		mainThread.start();

		monitorThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				boolean beamBreakVal;
				boolean beamBreakValPrev = false;

				boolean limitSwitchTopVal;
				boolean limitSwitchTopValPrev = false;

				boolean limitSwitchBottomVal;
				boolean limitSwitchBottomValPrev = false;

				long lastRefresh = System.currentTimeMillis()
						+ MONTITOR_REFRESH_RATE;

				while (true)
				{
					beamBreakVal = beamBreak.get();
					limitSwitchTopVal = limitSwitchTop.get();
					limitSwitchBottomVal = limitSwitchBottom.get();

					long currTime = System.currentTimeMillis();

					if (beamBreakVal != beamBreakValPrev
							|| limitSwitchTopVal != limitSwitchTopValPrev
							|| limitSwitchBottomVal != limitSwitchBottomValPrev
							|| (currTime - lastRefresh) >= MONTITOR_REFRESH_RATE)
					{
						lastRefresh = currTime;

						SmartDashboard
								.putBoolean("Beam Break", beamBreak.get());
						SmartDashboard.putBoolean("Limit Switch Top",
								limitSwitchTop.get());
						SmartDashboard.putBoolean("Limit Switch Bottom",
								limitSwitchBottom.get());
					}

					beamBreakValPrev = beamBreakVal;
					limitSwitchTopValPrev = limitSwitchTopVal;
					limitSwitchBottomValPrev = limitSwitchBottomVal;

					Thread.yield();
				}
			}
		});

		monitorThread.start();
	}

	public void levelChange(int levels)
	{
		if (overrideEnabled || levelChangeCommand == levels)
		{
			return;
		}

		resetCommand = false;
		levelChangeCommand = levels;
		newCommand = true;
	}

	public void reset()
	{
		if (overrideEnabled || resetCommand)
		{
			return;
		}

		resetCommand = true;
		levelChangeCommand = 0;
		newCommand = true;
	}

	public void manualOverride(double speed)
	{
		overrideEnabled = true;
		isLifting = speed != 0;
		if (!chopsticksOverrideEnabled)
		{
			chopsticksInst.open(speed != 0);
		}

		liftMotor.set(speed);
	}

	public void endManualOverride()
	{
		if (overrideEnabled)
		{
			overrideEnabled = false;
			isLifting = false;

			liftMotor.set(0);
			if (!chopsticksOverrideEnabled)
			{
				chopsticksInst.open(false);
			}
		}
	}

	public boolean isLifting()
	{
		return isLifting;
	}

	public void waitForLift()
	{
		while (isLifting)
		{
			Thread.yield();
		}
	}

	public void chopsticksOverride(boolean enabled)
	{
		chopsticksOverrideEnabled = enabled;

		if (enabled)
		{
			chopsticksInst.open(true);
		} else if (!isLifting)
		{
			chopsticksInst.open(false);
		}
	}

	private void levelChangeThreaded(int levels)
	{
		boolean up = levels > 0;

		if (overrideEnabled || (limitSwitchTop.get() && up)
				|| (limitSwitchBottom.get() && !up))
		{
			return;
		}

		chopsticksInst.open(true);
		
		liftMotor.set(Constants.LIFT_MOTOR_DOWN_SPEED.getDouble()
				* (up ? 1 : -1));

		for (int i = 0; i < Math.abs(levels); i++)
		{
			moveLevelThreaded(up);
		}

		if (!overrideEnabled)
		{
			liftMotor.set(0);
			isLifting = false;

			if (!chopsticksOverrideEnabled)
			{
				chopsticksInst.open(false);
			}
		}
	}

	private void moveLevelThreaded(boolean up)
	{
		if (overrideEnabled || (limitSwitchTop.get() && up)
				|| (limitSwitchBottom.get() && !up))
		{
			return;
		}

		// Runs the motor until we don't see the tape
		long startTime = System.currentTimeMillis();
		int beamBreakCount = 0;
		while (beamBreakCount <= Constants.LIFT_NUM_SAMPLES.getInt()
				&& (!limitSwitchBottom.get() || up)
				&& (!limitSwitchTop.get() || !up)
				&& (System.currentTimeMillis() - startTime) < Constants.LIFT_RESET_TIMEOUT
						.getInt() && !overrideEnabled && !newCommand)
		{
			if (!beamBreak.get())
			{
				beamBreakCount++;
			} else
			{
				beamBreakCount = 0;
			}
			Thread.yield();
		}

		// Runs the motor until we see the tape
		beamBreakCount = 0;
		while (beamBreakCount <= Constants.LIFT_NUM_SAMPLES.getInt()
				&& (!limitSwitchBottom.get() || up)
				&& (!limitSwitchTop.get() || !up)
				&& (System.currentTimeMillis() - startTime) < Constants.LIFT_RESET_TIMEOUT
						.getInt() && !overrideEnabled && !newCommand)
		{
			if (beamBreak.get())
			{
				beamBreakCount++;
			} else
			{
				beamBreakCount = 0;
			}
			Thread.yield();
		}

		if ((System.currentTimeMillis() - startTime) > Constants.LIFT_RESET_TIMEOUT
				.getInt())
		{
			Utils.sendDSError("Lift Timeout");
		}
	}

	private void resetThreaded()
	{
		if (limitSwitchBottom.get())
		{
			return;
		}

		isLifting = true;
		chopsticksInst.open(true);

		liftMotor.set(-Constants.LIFT_MOTOR_DOWN_SPEED.getDouble());

		long startTime = System.currentTimeMillis();
		while (!limitSwitchBottom.get()
				&& (System.currentTimeMillis() - startTime) < Constants.LIFT_RESET_TIMEOUT
						.getInt() && !overrideEnabled && !newCommand)
		{
			Thread.yield();
		}

		if (!overrideEnabled)
		{
			liftMotor.set(0);
			isLifting = false;
		}

		if ((System.currentTimeMillis() - startTime) > Constants.LIFT_RESET_TIMEOUT
				.getInt())
		{
			Utils.sendDSError("Lift Reset Timeout");
		}
	}

	@Override
	public void run()
	{
		while (true)
		{
			if (newCommand)
			{
				newCommand = false;

				if (!overrideEnabled)
				{
					if (resetCommand)
					{
						resetThreaded();
					} else
					{
						levelChangeThreaded(levelChangeCommand);
					}
				}

				resetCommand = false;
				levelChangeCommand = 0;
			}
			Thread.yield();
		}
	}
}
