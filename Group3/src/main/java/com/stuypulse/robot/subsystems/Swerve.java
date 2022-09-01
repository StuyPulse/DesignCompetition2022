package com.stuypulse.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.stuypulse.stuylib.math.Vector2D;

import static com.stuypulse.robot.constants.Modules.*;

import java.util.Arrays;
import java.util.stream.Stream;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * 
 */
public class Swerve extends SubsystemBase {

    private final SwerveModule[] modules;
    private final AHRS gyroscope;

    private final SwerveDriveKinematics kinematics;
    private final SwerveDriveOdometry odometry;

    private final Field2d field;

    public Swerve() {
        modules = MODULES;
        gyroscope = new AHRS(SPI.Port.kMXP);

        kinematics = new SwerveDriveKinematics(
            getModuleStream()
                .map(x -> x.getOffset())
                .toArray(Translation2d[]::new)
        );

        odometry = new SwerveDriveOdometry(kinematics, getGyroscopeAngle());

        field = new Field2d();
        SmartDashboard.putData("Field", field);
    }

    /** MODULES */

    public SwerveModule getModule(String ID) {
        for (SwerveModule module : MODULES) {
            if (module.getID().equals(ID)) {
                return module;
            }
        }

        throw new IllegalArgumentException("Could not find module: " + ID);
    }

    public SwerveModule[] getModules() {
        return MODULES;
    }

    public Stream<SwerveModule> getModuleStream() {
        return Arrays.stream(getModules());
    }

    public SwerveModuleState[] getStates() {
        return Arrays.stream(modules)
            .map(x -> x.getState())
            .toArray(SwerveModuleState[]::new);
    }

    public void reset(Pose2d pose) {
        odometry.resetPosition(pose, getGyroscopeAngle());
        for (SwerveModule module : MODULES) {
            module.reset();
        }
    }

    public void stop() {
        setStates(new Vector2D(0.0, 0.0), 0.0);
        for (SwerveModule module : MODULES) {
            module.stop();
        }
    }

    /** STATES */

    // module states
    public void setStates(SwerveModuleState... states) {
        if (states.length != modules.length) {
            throw new IllegalArgumentException(
                "Number of module states (" + states.length + ") does not match number of modules (" + modules.length + ")"
            );
        }

        SwerveDriveKinematics.desaturateWheelSpeeds(states, MAX_SPEED);
        
        for (int i = 0; i < states.length; ++i) {
            modules[i].setTargetState(states[i]);
        }
    }

    // chassis speed
    public void setStates(ChassisSpeeds robotSpeed) {
        setStates(kinematics.toSwerveModuleStates(robotSpeed));
    }

    // velocity, omega, field relative
    public void setStates(Vector2D velocity, double omega, boolean fieldRelative) {
        if (fieldRelative) {
            setStates(ChassisSpeeds.fromFieldRelativeSpeeds(velocity.x, -velocity.y, -omega, getAngle()));
        } else {
            setStates(new ChassisSpeeds(velocity.x, -velocity.y, -omega));
        }
    }

    // velocity, omega
    public void setStates(Vector2D velocity, double omega) {
        setStates(velocity, omega, true);
    }

    /** ODOMETRY */

    private void update() {
        odometry.update(
            getGyroscopeAngle(), 
            getStates()
        );
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public Rotation2d getAngle() {
        return getPose().getRotation();
    }

    public SwerveDriveKinematics getKinematics() {
        return kinematics;
    }

    /** GYROSCOPE */

    public Rotation2d getGyroscopeAngle() {
        return gyroscope.getRotation2d();
    }

    @Override 
    public void periodic() {
        update();
        
        field.setRobotPose(getPose());

        // logging
        SmartDashboard.putNumber("Swerve/Gyroscope Angle", gyroscope.getRotation2d().getDegrees());
        SmartDashboard.putNumber("Swerve/Pose Angle", getAngle().getDegrees());
        SmartDashboard.putNumber("Swerve/Pose X", getPose().getTranslation().getX());
        SmartDashboard.putNumber("Swerve/Pose Y", getPose().getTranslation().getY());
    }

    @Override
    public void simulationPeriodic() {
        ChassisSpeeds speeds = kinematics.toChassisSpeeds(getStates());
        System.out.println(getStates()[0]);
        gyroscope.setAngleAdjustment(gyroscope.getAngle() + Math.toDegrees(speeds.omegaRadiansPerSecond * 0.2));
    }
}
