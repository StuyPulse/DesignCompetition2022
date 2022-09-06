package com.stuypulse.robot.commands.elevator;

import static com.stuypulse.robot.constants.Settings.Elevator.*;
import com.stuypulse.robot.subsystems.Elevator;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;

import edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ElevatorMove extends CommandBase {

    public final Elevator elevator;
    public final IStream input;

    public ElevatorMove(Elevator elevator, Gamepad operator) {
        this.elevator = elevator;
        input = IStream.create(operator::getLeftY)
            .filtered(
                x -> SLMath.deadband(x, DEADBAND.get()),
                x -> -x,
                new LowPassFilter(RC)
                );

        addRequirements(elevator);
    }

    @Override
    public void execute() {
        elevator.setState(new State(0, input.get())); 
        SmartDashboard.putNumber("Elevator/Input", input.get());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        elevator.setState(new State(elevator.getHeight(), 0));
    }
}
