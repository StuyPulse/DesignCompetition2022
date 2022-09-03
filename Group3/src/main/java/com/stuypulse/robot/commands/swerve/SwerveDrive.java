package com.stuypulse.robot.commands.swerve;

import static com.stuypulse.robot.constants.Settings.Swerve.*;
import com.stuypulse.robot.subsystems.Swerve;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.vectors.VStream;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SwerveDrive extends CommandBase {
    
    private final Swerve swerve;

    private VStream speed;
    private IStream turn;

    public SwerveDrive(Swerve swerve, Gamepad driver) {

        this.swerve = swerve;
        speed = VStream.create(driver::getLeftStick)
                        .filtered(Drive.getFilter());
        turn = IStream.create(driver::getRightX)
                        .filtered(Turn.getFilter());

        addRequirements(swerve);
    }

    @Override
    public void execute() {
        // ChassisSpeeds speeds = ChassisSpeeds.fromFieldRelativeSpeeds(speed.get().x, speed.get().y, turn.get(), swerve.getAngle());
        // System.out.println(speeds);
        swerve.setStates(speed.get(), turn.get(), true);
    }

    @Override
    public boolean isFinished() { return false; }

    @Override
    public void end(boolean wasInterrupted) {}
}
