package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Elevator;

public class MoveElevatorDownCommand extends MoveElevatorCommand{
    public MoveElevatorDownCommand(Elevator elevator) {
        super(elevator, -1, false);
    }
}
