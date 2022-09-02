package com.stuypulse.robot.commands.elevator;

import com.stuypulse.robot.constants.Settings.Elevator.Control;
import com.stuypulse.robot.subsystems.Elevator;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.control.feedforward.Feedforward;
import com.stuypulse.stuylib.streams.filters.MotionProfile;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveElevatorToHeight extends CommandBase {
    
    private static final double EPSILON = 0.1;

    private final Elevator elevator;
    private final Controller controller;

    private final double distance;

    public MoveElevatorToHeight(double distance, Elevator elevator) {
        this.elevator = elevator;
        this.distance = distance;

        controller = new Feedforward.Elevator(
                Control.kG, Control.kS, Control.kV, Control.kA).position()
            .add(new PIDController(Control.kP, Control.kI, Control.kD))
            .setSetpointFilter(new MotionProfile(Control.MAX_VEL, Control.MAX_ACCEL));

        addRequirements(elevator);
    }

    @Override
    public void execute() {
        elevator.move(
            controller.update(distance, elevator.getDistance()));
    }

    @Override
    public boolean isFinished() {
        return Math.abs(elevator.getDistance() - distance) < EPSILON;
    }

    @Override
    public void end(boolean wasInterrupted) {
        elevator.move(Control.kG);
    }
    
}
