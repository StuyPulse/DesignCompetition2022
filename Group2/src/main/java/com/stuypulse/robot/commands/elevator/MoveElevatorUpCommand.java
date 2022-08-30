package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Elevator;

public class MoveElevatorUpCommand extends MoveElevatorCommand{
    public MoveElevatorUpCommand(Elevator elevator) {
        super(elevator, -1, true);
    }
}
