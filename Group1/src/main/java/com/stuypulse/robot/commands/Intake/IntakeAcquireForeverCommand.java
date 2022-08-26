package com.stuypulse.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import com.stuypulse.robot.subsystems.Intake;

/**
  * @author Amber Shen
 */

public class IntakeAcquireForeverCommand extends InstantCommand {
    private final Intake intake;

    public IntakeAcquireForeverCommand(Intake intake) {
        this.intake = intake;
    }

    @Override
    public void initialize() {
        intake.acquire();
    }
}
