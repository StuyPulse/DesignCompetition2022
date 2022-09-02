package com.stuypulse.robot.commands.elevator;

import com.stuypulse.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveElevatorCommand extends CommandBase {
    
    private final Elevator elevator;
    private final Number speed;
    private final boolean movingUp;

    public MoveElevatorCommand(Elevator elevator, Number speed, boolean movingUp) {
        this.elevator = elevator;
        this.speed = speed;
        this.movingUp = movingUp;

        addRequirements(elevator);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (movingUp) {
            elevator.move(+this.speed.doubleValue());
        } else {
            elevator.move(-this.speed.doubleValue());
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
