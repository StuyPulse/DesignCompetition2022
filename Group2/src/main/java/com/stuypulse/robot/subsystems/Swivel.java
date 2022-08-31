package com.stuypulse.robot.subsystems;

import com.stuypulse.robot.constants.Modules;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.constants.Settings.Swivel.Filtering.Drive;
import com.stuypulse.robot.subsystems.swivel.CANModule;
import com.stuypulse.robot.subsystems.swivel.SimModule;
import com.stuypulse.robot.subsystems.swivel.SwivelModule;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.Vector2D;

import java.util.Arrays;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.FieldObject2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Swivel (swerve) subsytem
 * 
 * Fields:
 * - Modules
 * - Gyro
 * - Kinematics
 * - Odometry 
 * - Field2d
 * 
 * Methods:
 * - setStates(Vector2D, double, boolean)
 * - setStates(ChassisSpeeds)
 * - setStates(SwerveModuleState[])
 * - getModulePositions()
 * - getPosition()
 * - getRotation()
 * - getGyroAngle()
 * 
 * @author Tracey Lin
 * @author Marc Jiang
 * @author Ben Goldfisher
 */
public class Swivel extends SubsystemBase {

    private SwivelModule[] modules;

    // keep track of robot angle
    private AHRS gyro;

    // converts from drivetrain state to module state
    private SwerveDriveKinematics kinematics;
    // uses kinematics to keep track of robot position
    private SwerveDriveOdometry odometry;

    private Field2d field;
    private FieldObject2d[] fieldModules;

    public Swivel() {
        // modules = new CANModule[] {
        //     new CANModule(Modules.FRONT_LEFT),
        //     new CANModule(Modules.FRONT_RIGHT),
        //     new CANModule(Modules.BACK_LEFT),
        //     new CANModule(Modules.BACK_RIGHT),
        // };
        modules = new SimModule[] {
            new SimModule(Modules.FRONT_LEFT),
            new SimModule(Modules.FRONT_RIGHT),
            new SimModule(Modules.BACK_LEFT),
            new SimModule(Modules.BACK_RIGHT),
        };
        
        gyro = new AHRS(SPI.Port.kMXP);

        // init kinematics
        kinematics = new SwerveDriveKinematics(getModulePositions());
        odometry = new SwerveDriveOdometry(kinematics, getGyroAngle());

        field = new Field2d();
        
        fieldModules = new FieldObject2d[modules.length];
        for (int i = 0; i < modules.length; i++) {
            fieldModules[i] = field.getObject(modules[i].getID());
        }

        SmartDashboard.putData(field);

        reset();
    }

    public SwerveDriveKinematics getKinematics() {
        return kinematics;
    }

    // Module State Methods //

    public void setStates(Vector2D translation, double angularVelocity, boolean fieldRelative) {
        if (fieldRelative) {
            setStates(ChassisSpeeds.fromFieldRelativeSpeeds(translation.y, -translation.x, -angularVelocity, getRotation()));
        } else {
            setStates(new ChassisSpeeds(translation.y, -translation.x, -angularVelocity));
        }
    }

    public void setStates(ChassisSpeeds speed) {
        setStates(kinematics.toSwerveModuleStates(speed));
    }

    public void setStates(SwerveModuleState[] states) {
        SwerveDriveKinematics.desaturateWheelSpeeds(states, Drive.MAX_SPEED.get());
    
        for (int i = 0; i < 4; i++) {
            modules[i].setState(states[i]);
        }
    }

    public SwerveModuleState[] getStates() {
        return Arrays.stream(modules)
            .map(m -> m.getState())
            .toArray(SwerveModuleState[]::new);
    }

    private Translation2d[] getModulePositions() {
        return Arrays.stream(modules)
            .map(m -> m.offset.getTranslation2d())
            .toArray(Translation2d[]::new);
    }

    // Odometry //
    
    public Pose2d getPosition() {
        return odometry.getPoseMeters();
    }

    public Rotation2d getRotation() {
        return getPosition().getRotation();
    }

    // Gyro //

    public Rotation2d getGyroAngle() {
        return gyro.getRotation2d();
    }

    // Reset //

    public void reset() {
        reset(new Pose2d());
    }

    public void reset(Pose2d pos) {
        odometry.resetPosition(pos, getGyroAngle());
    }

    // Field //

    private void updateField() {
        field.setRobotPose(getPosition());

        Vector2D center = new Vector2D(getPosition().getTranslation());

        for (int i = 0; i < modules.length; i++) {
            Vector2D pos = modules[i].getOffset()
                .rotate(Angle.fromRotation2d(getRotation()))
                .add(center);

            fieldModules[i].setPose(pos.x, pos.y, modules[i].getState().angle);
        }
    }

    @Override
    public void periodic() {
        odometry.update(getGyroAngle(), getStates());

        updateField();

        SmartDashboard.putNumber("Swivel/X Position", getPosition().getX());
        SmartDashboard.putNumber("Swivel/Y Position", getPosition().getY());
        SmartDashboard.putNumber("Swivel/Gyro Angle", getGyroAngle().getDegrees());
        SmartDashboard.putNumber("Swivel/Rotation", getRotation().getDegrees());
    }

    @Override
    public void simulationPeriodic() {
        ChassisSpeeds speeds = kinematics.toChassisSpeeds(getStates());

        gyro.setAngleAdjustment(gyro.getAngle() - Math.toDegrees(speeds.omegaRadiansPerSecond * Settings.DT));
    }
}
