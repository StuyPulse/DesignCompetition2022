package com.stuypulse.robot.commands.elevator;

import com.stuypulse.robot.subsystems.Elevator;

public class MoveElevatorToBottom extends MoveElevatorToHeight {

	public MoveElevatorToBottom(Elevator elevator) {
		super(0, elevator);
	}

}
