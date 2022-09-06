package com.stuypulse.robot.commands.elevator;

import com.stuypulse.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ElevatorToHeightInstant extends InstantCommand {

    private final Elevator elevator;
    private final double height;
    
    public ElevatorToHeightInstant(double height, Elevator elevator) {
        this.elevator = elevator;
        this.height = height;
        
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        elevator.setHeight(height);
    }
    
}
