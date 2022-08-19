package com.stuypulse.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;

import com.stuypulse.robot.subsystems.Elevator;

public class ElevatorToSwitchCommand extends CommandBase {
    private final Elevator elevator;

    public ElevatorToSwitchCommand(Elevator elevator) {
        this.elevator = elevator;
        
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        elevator.setSwitch();
    }
}
