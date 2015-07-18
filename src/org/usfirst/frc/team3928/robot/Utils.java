package org.usfirst.frc.team3928.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Utils
{
	private static final int COMP_TIME = 1429986600;

	private static final String NAME_FILE_PATH = "/home/lvuser/constants";

	public static void sendDSError(String message)
	{
		DriverStation.reportError("["
				+ (System.currentTimeMillis() - COMP_TIME) + "] " + message
				+ '\n', false);
	}

	public static void updateSmartDashboardName()
	{
		try
		{
			Scanner in = new Scanner(new File(NAME_FILE_PATH));
			if (!in.hasNext())
			{
				SmartDashboard.putString("Robot Name", "No Name");
				return;
			}
			SmartDashboard.putString("Robot Name", in.nextLine());
			in.close();
		} catch (FileNotFoundException e)
		{
			SmartDashboard.putString("Robot Name", "No Name");
		}
	}
}
