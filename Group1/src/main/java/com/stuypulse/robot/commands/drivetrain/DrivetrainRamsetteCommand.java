package com.stuypulse.robot.commands.drivetrain;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.IDrivetrain;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

import java.io.IOException;

import static com.stuypulse.robot.constants.Settings.Drivetrain.*;

/*
 * @author Kelvin Zhao
 * @author Vincent Wang
 * @author Samuel Chen
 * @author Amber Shen
 * @author Carmin Vuong
 */
public class DrivetrainRamsetteCommand extends RamseteCommand {

    private boolean resetPosition;
    private Trajectory trajectory;
    private IDrivetrain drivetrain;

    public DrivetrainRamsetteCommand(IDrivetrain drivetrain, Trajectory trajectory) {
        super(trajectory,
                drivetrain::getPose,
                new RamseteController(),
                new DifferentialDriveKinematics(TRACK_WIDTH),
                drivetrain::tankDrive,
                drivetrain);
        this.drivetrain = drivetrain;
        this.trajectory = trajectory;
        this.resetPosition = true;

        addRequirements(drivetrain);
    }

    public DrivetrainRamsetteCommand(IDrivetrain drivetrain, String path) {
        this(drivetrain, getTrajectory(path));
    }

    public static Trajectory getTrajectory(String path) {
        try {
            return TrajectoryUtil.fromPathweaverJson(Filesystem.getDeployDirectory().toPath().resolve(path));
        } catch (IOException e) {
            DriverStation.reportError("Error Opening \"" + path + "\"!", e.getStackTrace());

            System.err.println("Error Opening \"" + path + "\"!");
            System.out.println(e.getStackTrace());
            System.exit(694);

            return new Trajectory();
        }
    }

    public DrivetrainRamsetteCommand robotRelative() {
        resetPosition = true;
        return this;
    }

    public DrivetrainRamsetteCommand fieldRelative() {
        resetPosition = false;
        return this;
    }

    @Override
    public void initialize() {
        super.initialize();
        if (resetPosition) {
            drivetrain.reset(trajectory.getInitialPose());
        }
    }
}