package com.stuypulse.robot.commands.elevator;

import com.stuypulse.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ElevatorAutoClimb extends SequentialCommandGroup {
    public ElevatorAutoClimb(Elevator elevator) {
        addCommands(
            new ElevatorToHeight(elevator, 90),
            new WaitCommand(0.5),
            new ElevatorToBottom(elevator)
        );
    }
}
