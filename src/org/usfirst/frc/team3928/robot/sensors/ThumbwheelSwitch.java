package org.usfirst.frc.team3928.robot.sensors;

import edu.wpi.first.wpilibj.DigitalInput;

public class ThumbwheelSwitch
{
	private DigitalInput input1;
	private DigitalInput input2;
	private DigitalInput input4;
	private DigitalInput input8;

	public ThumbwheelSwitch(int input1Channel, int input2Channel,
			int input4Channel, int input8Channel)
	{
		input1 = new DigitalInput(input1Channel);
		input2 = new DigitalInput(input2Channel);
		input4 = new DigitalInput(input4Channel);
		input8 = new DigitalInput(input8Channel);
	}

	public int read()
	{
		return (input1.get() ? 0 : 1) + (input2.get() ? 0 : 2)
				+ (input4.get() ? 0 : 4) + (input8.get() ? 0 : 8);
	}
}
