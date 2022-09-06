package com.stuypulse.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;

import com.stuypulse.robot.subsystems.Elevator;

/**
  * @author Amber Shen
 */
 
public class ElevatorToScaleCommand extends CommandBase {
    private final Elevator elevator;
    
    public ElevatorToScaleCommand(Elevator elevator) {
        this.elevator = elevator;
        
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        elevator.setScale();
    }
}
