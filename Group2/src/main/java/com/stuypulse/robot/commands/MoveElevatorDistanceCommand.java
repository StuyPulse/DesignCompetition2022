package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Elevator;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveElevatorDistanceCommand extends CommandBase {

    private final Elevator elevator;
    private final double distance;

    public MoveElevatorDistanceCommand(double distance, Elevator elevator) {
        this.elevator = elevator;
        this.distance = distance;

        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        elevator.setDistance(distance);
    }
}
