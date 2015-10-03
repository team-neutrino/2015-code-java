package org.usfirst.frc.team3928.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Constants
{
	private static final ArrayList<Constant> constList = new ArrayList<Constant>();
	
	// Autonomous Constants
	public static final Constant AUTO_SWITCH_INPUT_1_CHANNEL = new Constant("AutoSwitchInput0Channel", 4);
	public static final Constant AUTO_SWITCH_INPUT_2_CHANNEL = new Constant("AutoSwitchInput1Channel", 5);
	public static final Constant AUTO_SWITCH_INPUT_4_CHANNEL = new Constant("AutoSwitchInput2Channel", 6);
	public static final Constant AUTO_SWITCH_INPUT_8_CHANNEL = new Constant("AutoSwitchInput3Channel", 7);
	public static final Constant GYRO_ANALOG_CHANNEL = new Constant("GyroAnalogChannel", 1);
	public static final Constant ENCODER_CHANNEL_A = new Constant("Encoder1ChannelA", 8);
	public static final Constant ENCODER_CHANNEL_B = new Constant("Encoder1ChannelB", 9);
	public static final Constant USE_TIME = new Constant("UseTime", false);
	public static final Constant FEET_TO_TIME_RATIO = new Constant("FeetToTimeRatio", .55);
	public static final Constant ENCODER_TICK_PER_FOOT = new Constant("EncoderTickPerFoot", 1500);
	public static final Constant AUTO_MOVE_SPEED = new Constant("AutoMoveSpeed", .45);

	// Controllers
	public static final Constant JOY_RIGHT_PORT = new Constant("JoyRightPort", 1);
	public static final Constant JOY_LEFT_PORT = new Constant("JoyLeftPort", 0);
	public static final Constant GAMEPAD_PORT = new Constant("GamepadPort", 2);

	// Button Mapping - Joysticks
	public static final Constant SPEED_BOOST_BUTTON = new Constant("SpeedBoostButton", 1);
	public static final Constant LIFT_RESET_BUTTON = new Constant("LiftResetButton", 2);
	public static final Constant RC_GRABBER_BUTTON = new Constant("RCGrabberButton", 7);

	// Button Mapping - Gamepad
	public static final Constant INTAKE_X_AXIS = new Constant("IntakeXAxis", 0);
	public static final Constant INTAKE_Y_AXIS = new Constant("IntakeYAxis", 1);
	public static final Constant INTAKE_OPEN_BUTTON = new Constant("IntakeOpenButton", 5);
	public static final Constant LIFT_UP_BUTTON = new Constant("LiftUpButton", 4);
	public static final Constant LIFT_DOWN_BUTTON = new Constant("LiftDownButton", 1);
	public static final Constant LIFT_OVERRIDE_UP_BUTTON = new Constant("LiftOverrideUpButton", 2);
	public static final Constant LIFT_OVERRIDE_DOWN_BUTTON = new Constant("LiftOverrideDownButton", 3);
	public static final Constant LIFT_AUTO_STACK_BUTTON = new Constant("LiftAutoStackButton", 2);
	public static final Constant CHOPSTICKS_OVERRIDE_BUTTON = new Constant("ChopsticksOverrideOpen", 6);

	// Drive Constants
	public static final Constant DRIVE_LEFT_1_CHANNEL = new Constant("DriveLeft1Channel", 1);
	public static final Constant DRIVE_LEFT_1_POWER_CHANNEL = new Constant("DriveLeft1PowerChannel", 15);
	public static final Constant DRIVE_LEFT_2_CHANNEL = new Constant("DriveLeft2Channel", 2);
	public static final Constant DRIVE_LEFT_2_POWER_CHANNEL = new Constant("DriveLeft2PowerChannel", 14);
	public static final Constant DRIVE_RIGHT_1_CHANNEL = new Constant("DriveRight1Channel", 5);
	public static final Constant DRIVE_RIGHT_1_POWER_CHANNEL = new Constant("DriveRight1PowerChannel", 3);
	public static final Constant DRIVE_RIGHT_2_CHANNEL = new Constant("DriveRight2Channel", 6);
	public static final Constant DRIVE_RIGHT_2_POWER_CHANNEL = new Constant("DriveRight2PowerChannel", 0);

	public static final Constant DRIVE_SLOW_MULTIPLIER = new Constant("DriveSlowMultiplier", .5);
	public static final Constant DRIVE_FAST_MUTLIPLIER = new Constant("DriveFastMultiplier", 1);

	// Lift Constants
	public static final Constant LIFT_MOTOR_CHANNEL = new Constant("LiftMotorChannel", 4);
	public static final Constant LIFT_MOTOR_POWER_CHANNEL = new Constant("LiftMotorPowerChannel", 8);

	public static final Constant LIFT_MOTOR_UP_OVERRIDE_SPEED = new Constant("LiftMotorUpOverideSpeed", .5);
	public static final Constant LIFT_MOTOR_DOWN_OVERRIDE_SPEED = new Constant("LiftMotorDownOverideSpeed", .5);

	public static final Constant LIFT_MOTOR_UP_SPEED = new Constant("LiftMotorUpSpeed", 1);
	public static final Constant LIFT_MOTOR_DOWN_SPEED = new Constant("LiftMotorDownSpeed", .6);

	public static final Constant BEAM_BREAK_CHANNEL = new Constant("BeamBreakChannel", 0);
	public static final Constant LIMIT_SWITCH_TOP_CHANNEL = new Constant("LimitSwitchTopChannel", 2);
	public static final Constant LIMIT_SWITCH_BOTTOM_CHANNEL = new Constant("LimitSwitchBottomChannel", 3);

	public static final Constant LIFT_TIME_OUT = new Constant("LiftTimeOut", 3000);
	public static final Constant LIFT_SAMPLE_RATE = new Constant("LiftSampleRate", 0.001);
	public static final Constant LIFT_NUM_SAMPLES = new Constant("LiftNumSamples", 5);
	public static final Constant LIFT_RESET_TIMEOUT = new Constant("LiftResetTimeOut", 3000);

	// Intake Constants
	public static final Constant INTAKE_MOTOR_RIGHT_CHANNEL = new Constant("IntakeMotorRightChannel", 7);
	public static final Constant INTAKE_MOTOR_RIGHT_POWER_CHANNEL = new Constant("IntakeMotorRightPowerChannel", 4);
	public static final Constant INTAKE_MOTOR_LEFT_CHANNEL = new Constant("IntakeMotorLeftChannel", 3);
	public static final Constant INTAKE_MOTOR_LEFT_POWER_CHANNEL = new Constant("IntakeMotorLeftPowerChannel", 11);

	public static final Constant INTAKE_SOLENOID_OPEN_CHANNEL = new Constant("IntakeSolenoidOpenChannel", 1);
	public static final Constant INTAKE_SOLENOID_CLOSE_CHANNEL = new Constant("IntakeSolenoidCloseChannel", 0);

	// Chopsticks Grabber Constants
	public static final Constant CHOPSTICKS_SOLENOID_OPEN = new Constant("ChopsticksSolenoidOpen", 2);
	public static final Constant CHOPSTICKS_SOLENOID_CLOSE = new Constant("ChopsticksSolenoidClose", 3);

	// Recycling Container Constants
	public static final Constant RC_GRABBER_SOLENOID_UP = new Constant("RCGrabberSolenoidUp", 4);
	public static final Constant RC_GRABBER_SOLENOID_DOWN = new Constant("RCGrabberSolenoidDown", 5);

	// Lights Constants
	public static final Constant LIGHTS_CHANNEL = new Constant("LightsChannel", 0);
	public static final Constant LIGHTS_POWER_CHANNEL = new Constant("LightsChannel", 10);

	// Misc Constants
	public static final Constant PDP_ATTACHED = new Constant("PdpAttached", true);
	public static final Constant SMART_DASHBOARD_TEST_BUTTON = new Constant("SmartDashboardTest", 11);

	private static final String CONSTANTS_FILE_PATH = "/home/lvuser/constants";

	static
	{
		// read constant overrides
		try
		{
			Scanner in = new Scanner(new File(CONSTANTS_FILE_PATH));
			while (in.hasNext())
			{
				Scanner line = new Scanner(in.nextLine());
				line.useDelimiter(":");

				if (line.hasNext())
				{
					String name = line.next();
					if (line.hasNextDouble())
					{
						double value = line.nextDouble();
						overrideConst(name, value);
					} else if (line.hasNextBoolean())
					{
						boolean value = line.nextBoolean();
						overrideConst(name, value);
					}
				}

				line.close();
			}

			in.close();
		} catch (FileNotFoundException e)
		{
			Utils.sendDSError("No Constants File Found");
		}
	}

	private static void overrideConst(String name, double value)
	{
		Constant constant = getConstByName(name);

		if (constant == null)
		{
			Utils.sendDSError("Can not override not existent constant [" + name
					+ "]");
			return;
		}

		constant.updateValue(value);
		Utils.sendDSError("Overriding constant [" + name + "] with [" + value
				+ "]");
	}

	private static void overrideConst(String name, boolean value)
	{
		Constant constant = getConstByName(name);

		if (constant == null)
		{
			Utils.sendDSError("Can not override not existent constant [" + name
					+ "]");
			return;
		}

		constant.updateValue(value);
		Utils.sendDSError("Overriding constant [" + name + "] with [" + value
				+ "]");
	}

	private static Constant getConstByName(String name)
	{
		for (Constant constant : constList)
		{
			if (constant.getName().equals(name))
			{
				return constant;
			}
		}

		return null;
	}

	public static class Constant
	{

		private String name;

		private double value;

		public Constant(String name, double value)
		{
			this.name = name;
			this.value = value;
			constList.add(this);
		}

		public Constant(String name, boolean value)
		{
			this.name = name;
			this.value = value ? 1 : 0;
		}

		public void updateValue(double value)
		{
			this.value = value;
		}

		public void updateValue(boolean value)
		{
			this.value = value ? 1 : 0;
		}

		public String getName()
		{
			return name;
		}

		public int getInt()
		{
			return (int) value;
		}

		public double getDouble()
		{
			return value;
		}

		public float getFloat()
		{
			return (float) value;
		}

		public boolean getBoolean()
		{
			return !(value == 0);
		}
	}
}
