package org.usfirst.frc.team3928.robot;

import edu.wpi.first.wpilibj.DriverStation;

public class Utils
{
	private static final int COMP_TIME = 1428051600;

	public static void sendDSError(String message)
	{
		DriverStation.reportError("["
				+ (System.currentTimeMillis() - COMP_TIME) + "] " + message
				+ '\n', false);
	}
}
