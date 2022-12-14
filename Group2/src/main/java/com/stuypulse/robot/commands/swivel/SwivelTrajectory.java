package com.stuypulse.robot.commands.swivel;

import java.io.IOException;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.constants.Settings.Swivel.Motion;
import com.stuypulse.robot.subsystems.Swivel;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;

public class SwivelTrajectory extends SwerveControllerCommand {
    
    private final Swivel swivel;

    private final Trajectory trajectory;

    private boolean fieldRelative;
    private boolean stop;

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
        stop = false;
    }

    public SwivelTrajectory(Swivel swivel, String path) {
        this(swivel, loadTrajectory(path));
    }

    private static Trajectory loadTrajectory(String path) {
        try {
            return TrajectoryUtil.fromPathweaverJson(Settings.DEPLOY_DIRECTORY.resolve(path));
        } catch (IOException e) {
            DriverStation.reportError("Error Opening \"" + path + "\"!", e.getStackTrace());

            System.err.println("Error Opening \"" + path + "\"!");
            System.out.println(e.getStackTrace());
            System.exit(694);

            return null;
        }
    }

    public SwivelTrajectory fieldRelative() {
        fieldRelative = true;

        return this;
    }

    public SwivelTrajectory robotRelative() {
        fieldRelative = false;

        return this;
    }

    public SwivelTrajectory withStop() {
        stop = true;

        return this;
    }

    @Override
    public void initialize() {
        if (!fieldRelative) {
            swivel.reset(trajectory.getInitialPose());
        }

        super.initialize();
    }

    @Override
    public void end(boolean wasInterrupted) {
        super.end(wasInterrupted);

        if (stop) {
            swivel.stop();
        }
    }

}
