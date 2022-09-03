package com.stuypulse.robot.commands.elevator;

import com.stuypulse.robot.subsystems.Elevator;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.util.StopWatch;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ElevatorDrive extends CommandBase {
    
    private final Elevator elevator;
    private final IStream stick;

    private final StopWatch timer;

    public ElevatorDrive(Elevator elevator, Gamepad gamepad) {
        this.elevator = elevator;

        stick = IStream.create(gamepad::getLeftY);

        timer = new StopWatch();

        addRequirements(elevator);
    }

    @Override
    public void execute() {
        elevator.addHeight(stick.get() * timer.reset());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
