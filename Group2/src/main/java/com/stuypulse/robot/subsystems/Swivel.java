package com.stuypulse.robot.subsystems;

import com.stuypulse.robot.subsystems.swivel.Module;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Swivel extends SubsystemBase {

    private Module[] modules;

    private AHRS gyro;

    private SwerveDriveKinematics kinematics;
    private SwerveDriveOdometry odometry;

    private Field2d field;

    public Swivel() {
        modules = new Module[4];
        
        // fix: get the port
        gyro = new AHRS();

        // init kinematics
        odometry = new SwerveDriveOdometry(kinematics, getGyroAngle());
        kinematics = new SwerveDriveKinematics();
    }

    public void setStates(ChassisSpeeds speed) {

    }

    public void setStates(SwerveModuleState[] states) {
        SwerveDriveKinematics.desaturateWheelSpeeds(states, ZOOM);
    
        for (int i = 0; i < 4; i++) {
            modules[i].setState(states[i]);
        }
    }
    
    public Pose2d getPosition() {
        return odometry.getPoseMeters();
    }

    public Rotation2d getRotation() {
        return getPosition().getRotation();
    }

    public Rotation2d getGyroAngle() {
        return gyro.getRotation2d();
    }
}
