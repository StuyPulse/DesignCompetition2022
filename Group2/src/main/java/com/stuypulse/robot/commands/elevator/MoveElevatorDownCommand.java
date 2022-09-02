package com.stuypulse.robot.commands.elevator;

import com.stuypulse.robot.subsystems.Elevator;

import edu.wpi.first.math.util.Units;

public class MoveElevatorDownCommand extends MoveElevatorCommand {

    private static final double DOWN_SPEED = Units.feetToMeters(1);

    public MoveElevatorDownCommand(Elevator elevator) {
        super(elevator, DOWN_SPEED, false);
    }
}
