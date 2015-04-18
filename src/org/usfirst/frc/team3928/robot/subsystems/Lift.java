package org.usfirst.frc.team3928.robot.subsystems;

import org.usfirst.frc.team3928.robot.Constants;
import org.usfirst.frc.team3928.robot.Utils;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;

public class Lift implements Runnable
{
	// TODO add smartdashboard monitor

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

	public Lift(Chopsticks chopsticksInst)
	{
		this.chopsticksInst = chopsticksInst;
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
		if (levels > 0)
		{
			for (int i = 0; i < levels; i++)
			{
				levelUpThreaded();
			}
		} else
		{
			for (int i = 0; i < -levels; i++)
			{
				levelDownThreaded();
			}
		}
	}

	private void levelUpThreaded()
	{

	}

	private void levelDownThreaded()
	{
		if (limitSwitchBottom.get())
		{
			return;
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

		liftMotor.set(Constants.LIFT_MOTOR_DOWN_SPEED.getDouble());

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
		
		if ((System.currentTimeMillis() - startTime) < Constants.LIFT_RESET_TIMEOUT
						.getInt())
		{
			Utils.sendDSError("Lift Reset Timeout");
		}
	}

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
