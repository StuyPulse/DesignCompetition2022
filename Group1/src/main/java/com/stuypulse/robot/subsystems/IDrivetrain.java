package com.stuypulse.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class IDrivetrain extends SubsystemBase {

    abstract public Rotation2d getAngle();

    abstract public void reset(Pose2d location);

    abstract public DifferentialDriveWheelSpeeds getSpeeds();

    abstract public Pose2d getPose();

    abstract public void tankDrive(double leftMetersPerSecond, double rightMetersPerSecond);
    
    abstract public void arcadeDrive(double vxMetersPerSecond, double omegaRadiansPerSecond);
}
