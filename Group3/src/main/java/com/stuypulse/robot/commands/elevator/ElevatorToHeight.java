package com.stuypulse.robot.commands.elevator;

import com.stuypulse.robot.subsystems.Elevator;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.TrapezoidProfileCommand;

import static com.stuypulse.robot.constants.Settings.Elevator.*;

public class ElevatorToHeight extends TrapezoidProfileCommand {
    
    public ElevatorToHeight(Elevator elevator, double distance) {
        this(elevator, distance, MAX_ACCELERATION, MAX_VELOCITY);
    }

    public ElevatorToHeight(Elevator elevator, TrapezoidProfile tProfile) {
        super(tProfile, elevator::setState, elevator);
    }

    public ElevatorToHeight(Elevator elevator, double distance, double maxAcceleration, double maxVelocity) {
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
