package com.stuypulse.robot.commands.drivetrain;

import com.stuypulse.robot.subsystems.Drivetrain;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

import static com.stuypulse.robot.constants.Settings.Drivetrain.RAMSETE.*;

import java.io.IOException;

import static com.stuypulse.robot.constants.Settings.Drivetrain.FF.*;
import static com.stuypulse.robot.constants.Settings.Drivetrain.PID.*;
import static com.stuypulse.robot.constants.Settings.Drivetrain.*;

public class DrivetrainRamsetteCommand extends RamseteCommand {

    private boolean resetPosition;
    private Trajectory trajectory;
    private Drivetrain drivetrain;

    public DrivetrainRamsetteCommand(Drivetrain drivetrain, Trajectory trajectory) {
        super(trajectory, 
            drivetrain::getPose, 
            new RamseteController(b.get(), zeta.get()), 
            new SimpleMotorFeedforward(kS.get(), kV.get(), kA.get()), 
            new DifferentialDriveKinematics(TRACK_WIDTH), 
            drivetrain::getSpeed, 
            new PIDController(kP.get(), kI.get(), kD.get()),
            new PIDController(kP.get(), kI.get(), kD.get()),
            drivetrain::setVoltage,
            drivetrain
            );
        this.drivetrain = drivetrain;
        this.trajectory = trajectory;
        this.resetPosition = true;

        addRequirements(drivetrain);
    }

    public DrivetrainRamsetteCommand(Drivetrain drivetrain, String path) {
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