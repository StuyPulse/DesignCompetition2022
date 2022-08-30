package com.stuypulse.robot.commands.elevator;

import com.stuypulse.robot.subsystems.Elevator;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.TrapezoidProfileCommand;

public class ElevatorMoveDistance extends TrapezoidProfileCommand {
    
    public ElevatorMoveDistance(Elevator elevator, double distance) {
        this(elevator, distance, 0.0, 0.0);
    }

    public ElevatorMoveDistance(Elevator elevator, TrapezoidProfile tProfile) {
        super(tProfile, elevator::setState, elevator);
    }

    public ElevatorMoveDistance(Elevator elevator, double distance, double maxAcceleration, double maxVelocity) {
        super(
            new TrapezoidProfile(
                new TrapezoidProfile.Constraints(maxVelocity, maxAcceleration),
                new TrapezoidProfile.State(distance, 0)
            ),
            elevator::setState,
            elevator
        );
    }
}
