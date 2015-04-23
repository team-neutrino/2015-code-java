package org.usfirst.frc.team3928.robot.autonomous;

public interface AutonomousMode
{
	public String getName();
	public void run() throws InterruptedException;
}
