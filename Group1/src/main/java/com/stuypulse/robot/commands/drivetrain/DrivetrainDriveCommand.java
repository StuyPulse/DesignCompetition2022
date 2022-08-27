package com.stuypulse.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.streams.filters.IFilter;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;
import com.stuypulse.robot.constants.Settings;

public class DrivetrainDriveCommand extends CommandBase {
    private final Drivetrain drivetrain;
    private final Gamepad gamepad;

    private final IFilter turnFilter;
    private final IFilter driveFilter;
    
    public DrivetrainDriveCommand(Drivetrain drivetrain, Gamepad gamepad) {
        this.drivetrain = drivetrain;
        this.gamepad = gamepad;

        turnFilter = IFilter.create(
            (x) -> SLMath.deadband(x, Settings.Drivetrain.TURN_DEADBAND.get()),
            (x) -> SLMath.spow(x, Settings.Drivetrain.TURN_POWER.get()),
            new LowPassFilter(Settings.Drivetrain.TURN_RC.get()),
            (x) -> x * Settings.Drivetrain.MAX_TURN_SPEED.get()
        );
        driveFilter = IFilter.create(
            (x) -> SLMath.deadband(x, Settings.Drivetrain.DRIVE_DEADBAND.get()),
            (x) -> SLMath.spow(x, Settings.Drivetrain.DRIVE_POWER.get()),
            new LowPassFilter(Settings.Drivetrain.DRIVE_RC.get()),
            (x) -> x * Settings.Drivetrain.MAX_SPEED.get()
        );
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrain.arcadeDrive(driveFilter.get(gamepad.getLeftTrigger() - gamepad.getRightTrigger()), 
                                turnFilter.get(gamepad.getLeftX()));
    }
}
