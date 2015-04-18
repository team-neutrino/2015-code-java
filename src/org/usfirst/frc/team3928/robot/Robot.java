package org.usfirst.frc.team3928.robot;

import org.usfirst.frc.team3928.robot.subsystems.Chopsticks;
import org.usfirst.frc.team3928.robot.subsystems.Drive;
import org.usfirst.frc.team3928.robot.subsystems.Intake;
import org.usfirst.frc.team3928.robot.subsystems.Lift;
import org.usfirst.frc.team3928.robot.subsystems.Lights;
import org.usfirst.frc.team3928.robot.subsystems.RCGrabber;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;

public class Robot extends SampleRobot
{
	private Compressor compInst;
	private Joystick joyRight;
	private Joystick joyLeft;
	private Joystick gamepad;
	private Drive driveInst;
	private Chopsticks chopsticksInst;
	private RCGrabber rcGrabberInst;
	private Lights lightsInst;
	private Lift liftInst;
	private Intake intakeInst;
	
	/**
	 * Constructor
	 */
	public Robot()
	{
		
	}

	/**
	 * Robot Initialization
	 */
	@Override
	public void robotInit()
	{

	}

	/**
	 * Disabled mode
	 */
	@Override
	public void disabled()
	{

	}

	/**
	 * Autonomous mode
	 */
	@Override
	public void autonomous()
	{
		
	}

	/**
	 * Operator Control mode
	 */
	@Override
	public void operatorControl()
	{
		
	}

	/**
	 * Test mode
	 */
	@Override
	public void test()
	{
		
	}
}
