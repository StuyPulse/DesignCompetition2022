package com.stuypulse.robot.commands.elevator;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.Elevator;

public class ElevatorToTop extends ElevatorToHeight {

	public ElevatorToTop(Elevator elevator) {
		super(Settings.Elevator.MAX_DIST, elevator);
	}

}
