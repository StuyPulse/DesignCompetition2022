package com.stuypulse.robot.commands.Intake;

import com.stuypulse.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
    *@author Amber (ambers7)
 */

public class IntakeDeacquireCommand extends CommandBase {
    private final Intake intake;
    
    public IntakeDeacquireCommand(Intake intake) {
        this.intake = intake;
        
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.deacquire();
    }

    @Override
    public void end(boolean interrupted) {
        intake.stop();
    }
}