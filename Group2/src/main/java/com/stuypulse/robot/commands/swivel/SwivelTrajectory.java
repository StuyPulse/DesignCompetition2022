package com.stuypulse.robot.commands.swivel;

import com.stuypulse.robot.constants.Settings.Swivel.Motion;
import com.stuypulse.robot.subsystems.Swivel;
import com.stuypulse.robot.util.TrajectoryLoader;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;

public class SwivelTrajectory extends SwerveControllerCommand {
    
    private final Swivel swivel;

    private final Trajectory trajectory;

    private boolean fieldRelative;

    public SwivelTrajectory(Swivel swivel, Trajectory trajectory) {
        super(
            trajectory,
            swivel::getPosition,
            swivel.getKinematics(),
            Motion.X.getController(),
            Motion.Y.getController(),
            Motion.Theta.getController(),
            swivel::setStates,
            swivel
        );

        this.swivel = swivel;
        this.trajectory = trajectory;

        fieldRelative = true;
    }

    public SwivelTrajectory(Swivel swivel, String path) {
        this(swivel, TrajectoryLoader.getTrajectory(path));
    }

    public SwivelTrajectory fieldRelative() {
        fieldRelative = true;

        return this;
    }

    public SwivelTrajectory robotRelative() {
        fieldRelative = false;

        return this;
    }

    @Override
    public void initialize() {
        if (!fieldRelative) {
            swivel.setPosition(trajectory.getInitialPose());
        }

        super.initialize();
    }

}
