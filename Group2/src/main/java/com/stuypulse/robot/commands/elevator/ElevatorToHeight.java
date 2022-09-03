package com.stuypulse.robot.commands.elevator;

import com.stuypulse.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ElevatorToHeight extends CommandBase {
	
	private final Elevator elevator;
	private final double height;

	public ElevatorToHeight(double height, Elevator elevator) {
		this.elevator = elevator;
		this.height = height;

		addRequirements(elevator);
	}

	@Override
	public void initialize() {
		elevator.setHeight(height);
	}

	@Override
	public boolean isFinished() {
		return elevator.atHeight(height);
	}

}
