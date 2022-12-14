package com.stuypulse.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;

import com.stuypulse.robot.subsystems.IDrivetrain;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;
import com.stuypulse.robot.constants.Settings;

/*
 * @author Kelvin Zhao
 * @author Vincent Wang
 * @author Samuel Chen
 * @author Amber Shen
 * @author Carmin Vuong
 */
public class DrivetrainDriveCommand extends CommandBase {
    private final IDrivetrain drivetrain;

    private final IStream turnFilter;
    private final IStream driveFilter;

    public DrivetrainDriveCommand(IDrivetrain drivetrain, Gamepad gamepad) {
        this.drivetrain = drivetrain;
        turnFilter = IStream.create(() -> -gamepad.getLeftX()).filtered(
                x -> SLMath.deadband(x, Settings.Drivetrain.TURN_DEADBAND.get()),
                x -> SLMath.spow(x, Settings.Drivetrain.TURN_POWER.get()),
                new LowPassFilter(Settings.Drivetrain.TURN_RC.get()),
                x -> x * Settings.Drivetrain.MAX_TURN_SPEED.get());
        driveFilter = IStream.create(() -> gamepad.getLeftTrigger() - gamepad.getRightTrigger()).filtered(
                x -> SLMath.deadband(x, Settings.Drivetrain.DRIVE_DEADBAND.get()),
                x -> SLMath.spow(x, Settings.Drivetrain.DRIVE_POWER.get()),
                new LowPassFilter(Settings.Drivetrain.DRIVE_RC.get()),
                x -> x * Settings.Drivetrain.MAX_SPEED.get());
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrain.arcadeDrive(driveFilter.get(), turnFilter.get());
    }
}
