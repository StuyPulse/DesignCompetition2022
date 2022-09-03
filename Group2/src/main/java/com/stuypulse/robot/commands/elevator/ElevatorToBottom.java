package com.stuypulse.robot.commands.elevator;

import com.stuypulse.robot.subsystems.Elevator;

public class ElevatorToBottom extends ElevatorToHeight {

	public ElevatorToBottom(Elevator elevator) {
		super(0, elevator);
	}

}
