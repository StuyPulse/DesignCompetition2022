package com.stuypulse.robot.commands.Intake;

import com.stuypulse.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
  * @author Amber Shen
 */
 
public class IntakeAcquireCommand extends CommandBase {
    private final Intake intake;

    public IntakeAcquireCommand(Intake intake) {
        this.intake = intake;

        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.acquire();
    }

    @Override
    public void end(boolean interrupted) {
        intake.stop();
    }
}