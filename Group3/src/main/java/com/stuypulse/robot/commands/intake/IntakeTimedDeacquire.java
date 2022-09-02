package com.stuypulse.robot.commands.intake;

import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.stuylib.util.StopWatch;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeTimedDeacquire extends CommandBase {
    
    private final Intake intake;
    private final double time;
    private final StopWatch stopwatch;

    public IntakeTimedDeacquire(Intake intake, double time) {
        this.intake = intake;
        this.time = time;
        
        stopwatch = new StopWatch();
        
        addRequirements(intake);
    }

    public IntakeTimedDeacquire(Intake intake) {
        this(intake, 0.5);
    }

    @Override
    public void initialize() {
        intake.deacquire();
    }

    @Override
    public boolean isFinished() {
        return stopwatch.getTime() > time;
    }

    @Override
    public void end(boolean interrupted) {
        intake.stop();
    }
}
