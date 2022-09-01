package com.stuypulse.robot.subsystems;

import static com.stuypulse.robot.constants.Settings.Swerve.*;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.control.angle.feedback.AnglePIDController;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.control.feedforward.Feedforward;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.util.StopWatch;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Iteration #3 :skull:
 * send help
*/
public abstract class SwerveModule extends SubsystemBase {

    private final String id;
    private final Translation2d moduleOffset;

    private final Controller driveController;
    private final AngleController turnController;

    private SwerveModuleState targetState;
    
    public SwerveModule(String id, Translation2d moduleOffset) {
        this.id = id;
        this.moduleOffset = moduleOffset;

        driveController = new Feedforward.Motor(Drive.FF.S, Drive.FF.V, Drive.FF.A).velocity()
                                .add(Drive.FB.getController());

        turnController = Turn.FB.getController();

        this.targetState = new SwerveModuleState();
    }

    /** CONFIG */

    public String getID() {
        return this.id;
    }

    public Translation2d getOffset() {
        return this.moduleOffset;
    }

    /** SENSORS */

    protected abstract double getVelocity();

    protected abstract Rotation2d getAngle();

    protected abstract void setTargetVelocity(double velocity);

    protected abstract void setTargetAngle(double angle);

    /** STATES */

    public SwerveModuleState getState() {
        return new SwerveModuleState(getVelocity(), getAngle());
    }

    public void setTargetState(double velocity, Rotation2d angle) {
        targetState = SwerveModuleState.optimize(
            new SwerveModuleState(velocity, angle),
            getAngle()
        );
    }

    public void setTargetState(SwerveModuleState state) {
        targetState = SwerveModuleState.optimize(state, getAngle());
    }

    /** RESET */

    public void stop() {
        setTargetState(0.0, getAngle());
    }

    public void reset() {
        setTargetState(0.0, new Rotation2d(0.0));
    }

    @Override
    public void periodic() {
        // control
        setTargetVelocity(driveController.update(targetState.speedMetersPerSecond, getVelocity()));
        setTargetAngle(turnController.update(Angle.fromRotation2d(targetState.angle), Angle.fromRotation2d(getAngle())));

        // logging
        SmartDashboard.putNumber("Module " + id + "/Velocity", getVelocity());
        SmartDashboard.putNumber("Module " + id + "/Angle", getAngle().getDegrees());
        
        SmartDashboard.putNumber("Module " + id + "/Target Velocity", targetState.speedMetersPerSecond);
        SmartDashboard.putNumber("Module " + id + "/Target Angle", targetState.angle.getDegrees());
    }
}
