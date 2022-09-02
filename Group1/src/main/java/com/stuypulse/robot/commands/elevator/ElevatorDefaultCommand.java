package com.stuypulse.robot.commands.elevator;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.Elevator;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ElevatorDefaultCommand extends CommandBase {
    private final Elevator elevator;

    private final IStream lift;

    public ElevatorDefaultCommand(Elevator elevator, Gamepad operator) {
        this.elevator = elevator;

        lift = IStream.create( () -> operator.getLeftY() ).filtered(
                x -> SLMath.deadband(x, Settings.Elevator.LIFT_DEADBAND.get()),
                x -> SLMath.spow(x, Settings.Elevator.LIFT_POWER.get()),
                new LowPassFilter(Settings.Elevator.LIFT_RC.get()),
                x -> x + 1,
                x -> x * Settings.Elevator.MAX_ELEVATOR_HEIGHT);

        addRequirements(elevator);
    }

    @Override
    public void execute() {
        elevator.setPosition(lift.get());
    }
}
