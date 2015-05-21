package org.usfirst.frc.team3928.robot.autonomous.modes;

import org.usfirst.frc.team3928.robot.autonomous.AutonomousDriver;
import org.usfirst.frc.team3928.robot.autonomous.AutonomousMode;
import org.usfirst.frc.team3928.robot.subsystems.Intake;
import org.usfirst.frc.team3928.robot.subsystems.Lift;

public class ThreeToteStack implements AutonomousMode
{
	AutonomousDriver driver;
	Intake intake;
	Lift lift;
	
	public ThreeToteStack(AutonomousDriver driver, Intake intake, Lift lift)
	{
		this.driver = driver;
		this.intake = intake;
		this.lift = lift;
	}

	@Override
	public String getName()
	{
		return "Three tote stacked";
	}

	@Override
	public void run() throws InterruptedException
	{
		// Lift goes up two levels
		lift.levelChange(2);
		lift.waitForLift();
		Thread.sleep(500);
		
		// Pushes the recycling container out of the way
		intake.open(false);
		intake.setLeft(.75);
		intake.setRight(-.75);
		
		// Moves to the second tote
		driver.moveDistance(3);
		
		// Opens for the second tote
		intake.open(true);
		
		// Continues to move to the second tote
		driver.moveDistance(3);
		
		// Intake second toe
		intake.open(false);
		
		// Picks up the second tote
		
		// Closes the intake and pushes away the container
		
		// Slowly moves to the third tote and opens the intake
		
		// Intakes the third tote
		
		// Picks up the third tote
		
		// Turns and drives to the auto zone
	}

}
