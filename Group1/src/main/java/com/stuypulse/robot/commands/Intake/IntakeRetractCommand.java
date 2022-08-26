package com.stuypulse.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

import com.stuypulse.robot.subsystems.Intake;

/**
    *@author Amber (ambers7)
 */

public class IntakeRetractCommand extends CommandBase {
    private final Intake intake;

    public IntakeRetractCommand(Intake intake) {
        this.intake = intake;
        
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.retract();
    }
}
