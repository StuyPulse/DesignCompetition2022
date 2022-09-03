package com.stuypulse.robot.commands.elevator;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.Elevator;

public class ElevatorToTopInstant extends ElevatorToHeightInstant {

	public ElevatorToTopInstant(Elevator elevator) {
		super(Settings.Elevator.MAX_DIST, elevator);
	}

}
