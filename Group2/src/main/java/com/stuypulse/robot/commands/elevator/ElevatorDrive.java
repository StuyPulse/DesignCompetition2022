package com.stuypulse.robot.commands.elevator;

import com.stuypulse.robot.subsystems.Elevator;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.constants.Settings.Elevator.Control;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.streams.IStream;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ElevatorDrive extends CommandBase {
    
    private final Elevator elevator;
    private final IStream stick;

    public ElevatorDrive(Elevator elevator, Gamepad gamepad) {
        this.elevator = elevator;

        stick = IStream.create(gamepad::getRightY)
            .filtered(x -> x + Control.kG)
            .filtered(x -> x * Settings.Elevator.DRIVE_SPEED);

        addRequirements(elevator);
    }

    @Override
    public void execute() {
        elevator.move(stick.get());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
