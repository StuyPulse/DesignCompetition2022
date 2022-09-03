package com.stuypulse.robot.commands.elevator;

import com.stuypulse.robot.subsystems.Elevator;

public class ElevatorToBottomInstant extends ElevatorToHeightInstant {

	public ElevatorToBottomInstant(Elevator elevator) {
		super(0, elevator);
	}

}
