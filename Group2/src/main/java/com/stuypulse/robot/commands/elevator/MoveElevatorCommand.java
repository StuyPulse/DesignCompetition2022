package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveElevatorCommand extends CommandBase {
    private final Elevator elevator;
    private final Number number;
    private final boolean movingUp;

    public MoveElevatorCommand(Elevator elevator, Number speed, boolean movingUp) {
        this.elevator = elevator;
        this.number = speed;
        this.movingUp = movingUp;

        addRequirements(elevator);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (movingUp) {
            elevator.move(+this.number.doubleValue());
        } else {
            elevator.move(-this.number.doubleValue());
        }
    }

    @Override
    public void end(boolean interrupted) {
        elevator.setMotorStop();
    }

    @Override
    public boolean isFinished() {
        return (movingUp)
                ? elevator.getTopLimitReached()
                : elevator.getBottomLimitReached();
    }
}
