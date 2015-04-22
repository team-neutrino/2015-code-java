package org.usfirst.frc.team3928.robot;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CurrentMonitor implements Runnable
{
	private PowerDistributionPanel pdp;
	private Thread thread;

	private boolean pdpAttached;

	private final double WARGING_CURRENT = 120;

	public CurrentMonitor()
	{
		pdpAttached = Constants.PDP_ATTACHED.getBoolean();

		if (pdpAttached)
		{
			pdp = new PowerDistributionPanel();

			thread = new Thread(this);
			thread.start();
		}
	}

	public boolean warning()
	{
		return pdpAttached && WARGING_CURRENT <= pdp.getTotalCurrent();
	}

	@Override
	public void run()
	{
		while (pdpAttached)
		{
			// Total Current
			SmartDashboard.putBoolean("Warning",
					WARGING_CURRENT <= pdp.getTotalCurrent());
			SmartDashboard.putNumber("Total Current", pdp.getTotalCurrent());

			// Drive
			double driveLeft1Current = pdp
					.getCurrent(Constants.DRIVE_LEFT_1_POWER_CHANNEL.getInt());
			double driveLeft2Current = pdp
					.getCurrent(Constants.DRIVE_LEFT_2_POWER_CHANNEL.getInt());
			double driveRight1Current = pdp
					.getCurrent(Constants.DRIVE_RIGHT_1_POWER_CHANNEL.getInt());
			double driveRight2Current = pdp
					.getCurrent(Constants.DRIVE_RIGHT_2_POWER_CHANNEL.getInt());

			SmartDashboard.putNumber("Total Drive Current", driveLeft1Current
					+ driveLeft2Current + driveRight1Current
					+ driveRight2Current);
			SmartDashboard.putNumber("Drive Left 1 Current", driveLeft1Current);
			SmartDashboard.putNumber("Drive Left 2 Current", driveLeft2Current);
			SmartDashboard.putNumber("Drive Right 1 Current",
					driveRight1Current);
			SmartDashboard.putNumber("Drive Right 2 Current",
					driveRight2Current);

			// Lift
			SmartDashboard
					.putNumber("Total Lift Current", pdp
							.getCurrent(Constants.LIFT_MOTOR_POWER_CHANNEL
									.getInt()));

			// Intake
			double intakeLeftCurrent = pdp
					.getCurrent(Constants.INTAKE_MOTOR_LEFT_POWER_CHANNEL
							.getInt());
			double intakeRightCurrent = pdp
					.getCurrent(Constants.INTAKE_MOTOR_RIGHT_POWER_CHANNEL
							.getInt());

			SmartDashboard.putNumber("Total Sucky Current", intakeLeftCurrent
					+ intakeRightCurrent);
			SmartDashboard.putNumber("Sucky Left Current", intakeLeftCurrent);
			SmartDashboard.putNumber("Sucky Right Current", intakeRightCurrent);
			
			Thread.yield();
		}
	}
}
