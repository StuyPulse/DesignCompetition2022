package com.stuypulse.robot.commands.Intake;

import com.stuypulse.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
    *@author Amber (ambers7)
 */

public class IntakeExtendCommand extends CommandBase {
    private final Intake intake;

    public IntakeExtendCommand(Intake intake) {
        this.intake = intake;
        
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.extend();
    }
}
