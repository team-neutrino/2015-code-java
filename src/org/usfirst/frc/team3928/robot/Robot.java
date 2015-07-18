package org.usfirst.frc.team3928.robot;

import org.usfirst.frc.team3928.robot.autonomous.AutonomousSwitcher;
import org.usfirst.frc.team3928.robot.autonomous.modes.DoNothing;
import org.usfirst.frc.team3928.robot.subsystems.Drive;
import org.usfirst.frc.team3928.robot.subsystems.Intake;
import org.usfirst.frc.team3928.robot.subsystems.Lift;
import org.usfirst.frc.team3928.robot.subsystems.Lights;
import org.usfirst.frc.team3928.robot.subsystems.RCGrabber;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends SampleRobot
{
	// Joysticks
	private Joystick joyRight;
	private Joystick joyLeft;
	private Joystick gamepad;
	
	// Subsystems
	private Drive driveInst;
	private Lift liftInst;
	private Intake intakeInst;
	private RCGrabber rcGrabberInst;
	private Lights lightsInst;
	
	// Misc
	private AutonomousSwitcher autoSwitcherInst;
	private CurrentMonitor currentMonitorInst;

	/**
	 * Constructor
	 */
	public Robot()
	{
		// Joysticks
		joyRight = new Joystick(Constants.JOY_RIGHT_PORT.getInt());
		joyLeft = new Joystick(Constants.JOY_LEFT_PORT.getInt());
		gamepad = new Joystick(Constants.GAMEPAD_PORT.getInt());
		
		// Subsystems
		driveInst = new Drive();
		liftInst = new Lift();
		intakeInst = new Intake();
		rcGrabberInst = new RCGrabber();
		lightsInst = new Lights();
		
		// Misc
		autoSwitcherInst = new AutonomousSwitcher();
		currentMonitorInst = new CurrentMonitor();
	}

	/**
	 * Robot Initialization
	 */
	@Override
	public void robotInit()
	{
		lightsInst.set(true);
		autoSwitcherInst.assignMode(new DoNothing(), 0);
	}

	/**
	 * Disabled mode
	 */
	@Override
	public void disabled()
	{
		Utils.updateSmartDashboardName();
	}

	/**
	 * Autonomous mode
	 */
	@Override
	public void autonomous()
	{
		autoSwitcherInst.runAuto();
		Utils.updateSmartDashboardName();
	}

	/**
	 * Operator Control mode
	 */
	@Override
	public void operatorControl()
	{
		Utils.updateSmartDashboardName();
		boolean chopsticksOverridePrev = false;
		boolean choopstickOverrideCurr = false;
		
		boolean smartDashboardTestPrev = true;
		boolean smartDashboardTestCurr = false;

		while (isOperatorControl() && isEnabled())
		{
			// Drive Controls
			double driveSpeedMultiplier = ((joyLeft
					.getRawButton(Constants.SPEED_BOOST_BUTTON.getInt()) || joyRight
					.getRawButton(Constants.SPEED_BOOST_BUTTON.getInt())) ? Constants.DRIVE_FAST_MUTLIPLIER
					.getDouble() : Constants.DRIVE_SLOW_MULTIPLIER.getDouble());
			double leftSpeed = joyLeft.getY();
			double rightSpeed = joyRight.getY();
			driveInst.setLeft(-leftSpeed * Math.abs(leftSpeed)
					* driveSpeedMultiplier);
			driveInst.setRight(-rightSpeed * Math.abs(rightSpeed)
					* driveSpeedMultiplier);

			// Intake Controls
			intakeInst.setLeft(gamepad.getRawAxis(Constants.INTAKE_Y_AXIS
					.getInt())
					+ gamepad.getRawAxis(Constants.INTAKE_X_AXIS.getInt()));
			intakeInst.setRight(gamepad.getRawAxis(Constants.INTAKE_Y_AXIS
					.getInt())
					- gamepad.getRawAxis(Constants.INTAKE_X_AXIS.getInt()));

			intakeInst.open(gamepad.getRawButton(Constants.INTAKE_OPEN_BUTTON
					.getInt()));

			// Lift Controls
			if (!gamepad.getRawButton(Constants.LIFT_OVERRIDE_UP_BUTTON
					.getInt())
					&& !gamepad
							.getRawButton(Constants.LIFT_OVERRIDE_DOWN_BUTTON
									.getInt()))
			{
				// Ends manual override if it was enabled
				liftInst.endManualOverride();

				// Normal Control
				if (joyLeft.getRawButton(Constants.LIFT_RESET_BUTTON.getInt())
						|| joyRight.getRawButton(Constants.LIFT_RESET_BUTTON
								.getInt()))
				{
					liftInst.reset();
				}
				// sends lift up a level
				else if (gamepad
						.getRawButton(Constants.LIFT_UP_BUTTON.getInt()))
				{
					liftInst.levelChange(1);
				}
				// sends lift down a level
				else if (gamepad.getRawButton(Constants.LIFT_DOWN_BUTTON
						.getInt()))
				{
					liftInst.levelChange(-1);
				}
			} else
			{
				// Override Control

				// Moves lift up while button is pressed
				if (gamepad.getRawButton(Constants.LIFT_OVERRIDE_UP_BUTTON
						.getInt()))
				{
					liftInst.manualOverride(Constants.LIFT_MOTOR_UP_OVERRIDE_SPEED
							.getDouble());
				}
				// Moves lift down while button is pressed
				else
				{
					liftInst.manualOverride(-Constants.LIFT_MOTOR_DOWN_OVERRIDE_SPEED
							.getDouble());
				}
			}

			// RC Grabber Override
			if (joyLeft.getRawButton(Constants.RC_GRABBER_BUTTON.getInt()))
			{
				rcGrabberInst.deploy(true);
			} else if (joyRight.getRawButton(Constants.RC_GRABBER_BUTTON
					.getInt()))
			{
				rcGrabberInst.deploy(false);
			}

			// Chopsticks Override
			choopstickOverrideCurr = gamepad
					.getRawButton(Constants.CHOPSTICKS_OVERRIDE_BUTTON.getInt());
			if (chopsticksOverridePrev != choopstickOverrideCurr)
			{
				liftInst.chopsticksOverride(choopstickOverrideCurr);
			}

			chopsticksOverridePrev = choopstickOverrideCurr;

			// Current Warning && Test - rumbles when the breaker is about to blow
			smartDashboardTestCurr = joyRight.getRawButton(11);
			
			if (currentMonitorInst.warning() || smartDashboardTestCurr)
			{
				gamepad.setRumble(RumbleType.kLeftRumble, 1);
				gamepad.setRumble(RumbleType.kRightRumble, 1);
			} else
			{
				gamepad.setRumble(RumbleType.kLeftRumble, 0);
				gamepad.setRumble(RumbleType.kRightRumble, 0);
			}

			// SmartDashboard Test
			if (smartDashboardTestCurr != smartDashboardTestPrev)
			{
				SmartDashboard.putBoolean("Test", smartDashboardTestCurr);
			}
			smartDashboardTestPrev = smartDashboardTestCurr;
			
			Thread.yield();
		}
	}

	/**
	 * Test mode
	 */
	@Override
	public void test()
	{

	}
}
