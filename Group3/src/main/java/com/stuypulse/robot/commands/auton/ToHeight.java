package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;
import com.stuypulse.robot.commands.elevator.ElevatorToBottom;
import com.stuypulse.robot.commands.elevator.ElevatorToHeight;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ToHeight extends SequentialCommandGroup {
    
    public ToHeight(RobotContainer robot) {
        addCommands(
            new ElevatorToHeight(robot.elevator, 20.0),
            new WaitCommand(0.5),
            new ElevatorToHeight(robot.elevator, 112.0),
            new WaitCommand(0.5),
            new ElevatorToBottom(robot.elevator)
        );
    }
}
