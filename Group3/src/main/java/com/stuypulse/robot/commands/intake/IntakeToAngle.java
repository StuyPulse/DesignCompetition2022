package com.stuypulse.robot.commands.intake;

import com.stuypulse.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class IntakeToAngle extends InstantCommand {
    
    private final Intake intake;
    private final double angle;

    public IntakeToAngle(Intake intake, double angle) {
        this.intake = intake;
        this.angle = angle;
        
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.setTargetAngle(angle);
    }
}
