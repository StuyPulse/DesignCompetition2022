package com.stuypulse.robot.subsystems.swivel;

import com.stuypulse.robot.constants.Modules.ModuleConfig;
import com.stuypulse.robot.constants.Settings.Swivel.Drive;
import com.stuypulse.robot.constants.Settings.Swivel.Turn;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.control.angle.feedback.AnglePIDController;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.control.feedforward.Feedforward;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.Vector2D;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class SwivelModule extends SubsystemBase {
    
    private final Controller driveControl;
    private final AngleController turnControl;

    private SwerveModuleState targetState;

    public final Vector2D offset;

    public final String id;


    public SwivelModule(ModuleConfig config) {
        targetState = new SwerveModuleState(0, new Rotation2d(0));

        this.offset = config.offset;
        this.id = config.id;

        this.driveControl = new Feedforward.Motor(
                Drive.Feedforward.kS,
                Drive.Feedforward.kV,
                Drive.Feedforward.kA).velocity()
            .add(new PIDController(
                Drive.Feedback.kP,
                Drive.Feedback.kI,
                Drive.Feedback.kD));
        
        this.turnControl = new AnglePIDController(
            Turn.Feedback.kP,
            Turn.Feedback.kI,
            Turn.Feedback.kD);
    }


    protected abstract void setDriveVolts(double volts);

    protected abstract void setTurnVolts(double volts);

    protected abstract Rotation2d getAngle();

    protected abstract double getSpeed();

    public void stop() {
        targetState = new SwerveModuleState(0, getAngle());
    }


    public void setState(SwerveModuleState state) {
        targetState = SwerveModuleState.optimize(state, getAngle());
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(getSpeed(), getAngle());
    }

    public Vector2D getOffset() {
        return offset;
    }

    public String getID() {
        return id;
    }

    @Override
    public void periodic() {
        setDriveVolts(driveControl.update(
            targetState.speedMetersPerSecond, getSpeed()));

        // turn control
        setTurnVolts(
            turnControl.update(
                Angle.fromRotation2d(targetState.angle),
                Angle.fromRotation2d(getAngle())));

        SmartDashboard.putNumber("Swivel/" + id + "/Speed", getSpeed());
        SmartDashboard.putNumber("Swivel/" + id + "/Angle", getAngle().getDegrees());
        SmartDashboard.putNumber("Swivel/" + id + "/Target Angle", targetState.angle.getDegrees());
        SmartDashboard.putNumber("Swivel/" + id + "/Target Speed", targetState.speedMetersPerSecond);
    }

}
