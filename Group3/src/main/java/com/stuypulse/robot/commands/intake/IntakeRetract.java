package com.stuypulse.robot.commands.intake;

import com.stuypulse.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class IntakeRetract extends InstantCommand {
    
    private final Intake intake;

    public IntakeRetract(Intake intake) {
        this.intake = intake;

        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.retract();
    }
}
