package com.stuypulse.robot.commands.elevator;

import com.stuypulse.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ElevatorToHeight extends InstantCommand {

    private final Elevator elevator;
    private final double distance;
    
    public ElevatorToHeight(double distance, Elevator elevator) {
        this.elevator = elevator;
        this.distance = distance;
        
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        elevator.setHeight(distance);
    }
    
}
