package com.stuypulse.robot.commands.elevator;

import com.stuypulse.robot.subsystems.Elevator;
import static com.stuypulse.robot.constants.Settings.Elevator.*;

public class ElevatorToHeight extends ElevatorMoveDistance {
    
    public ElevatorToHeight(Elevator elevator, double height) {
        super(elevator, height, MAX_ACCELERATION, MAX_VELOCITY);
    }
}
