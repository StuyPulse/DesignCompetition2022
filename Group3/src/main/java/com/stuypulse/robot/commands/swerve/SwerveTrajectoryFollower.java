package com.stuypulse.robot.commands.swerve;

import java.util.function.Supplier;

import com.stuypulse.robot.constants.Settings.Swerve.Motion;
import com.stuypulse.robot.subsystems.Swerve;
import com.stuypulse.robot.util.TrajectoryLoader;
import com.stuypulse.stuylib.network.SmartAngle;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;

public class SwerveTrajectoryFollower extends SwerveControllerCommand {
    private final Swerve swerve;
    private final Trajectory trajectory;
    private boolean robotRelative;

    public SwerveTrajectoryFollower(Swerve swerve, Trajectory trajectory) {
        super(
            trajectory,
            swerve::getPose,
            swerve.getKinematics(),
            Motion.X.getController(),
            Motion.Y.getController(),
            Motion.Theta.getController(),
            swerve::setStates,
            swerve
        );

        this.swerve = swerve;
        this.trajectory = trajectory;

        robotRelative = false;
    }

    public SwerveTrajectoryFollower(Swerve swerve, String path) {
        this(swerve, TrajectoryLoader.getTrajectory(path));
    }

    public SwerveTrajectoryFollower robotRelative() {
        robotRelative = true;
        return this;
    }

    public SwerveTrajectoryFollower fieldRelative() {
        robotRelative = false;
        return this;
    }

    public void initialze() {
        if (robotRelative) {
            swerve.reset(trajectory.getInitialPose());
        }

        super.initialize();
    }
}