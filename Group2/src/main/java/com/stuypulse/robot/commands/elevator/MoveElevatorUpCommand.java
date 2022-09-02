package com.stuypulse.robot.commands.elevator;

import com.stuypulse.robot.subsystems.Elevator;

import edu.wpi.first.math.util.Units;

public class MoveElevatorUpCommand extends MoveElevatorCommand {

    private static final double UP_SPEED = Units.feetToMeters(1);

    public MoveElevatorUpCommand(Elevator elevator) {
        super(elevator, UP_SPEED, true);
    }
}
