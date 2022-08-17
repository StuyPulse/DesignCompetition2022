package com.stuypulse.robot.subsystems.swivel;

import com.stuypulse.robot.constants.Modules.ModuleConfig;
import com.stuypulse.robot.constants.Settings.Swivel;
import com.stuypulse.stuylib.control.PIDController;
import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.math.Vector2D;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class SwivelModule extends SubsystemBase {
    
    private final SimpleMotorFeedforward driveFeedforward;
    
    private final PIDController driveFeedback;
    private final AngleController turnFeedback;

    private SwerveModuleState state;

    public final Vector2D position;

    public final String id;


    public SwivelModule(ModuleConfig config) {
        state = new SwerveModuleState(0, new Rotation2d(0));

        this.position = config.position;
        this.id = config.id;

        this.driveFeedforward = Swivel.Drive.Feedforward.getFeedforward();
        this.driveFeedback = Swivel.Drive.Feedback.getFeedback();
        this.turnFeedback = Swivel.Turn.Feedback.getFeedback();
    }


    protected abstract void setDriveVolts(double volts);

    protected abstract void setTurnVolts(double volts);

    protected abstract Rotation2d getAngle();

    protected abstract double getSpeed();


    public void setState(SwerveModuleState state) {
        this.state = SwerveModuleState.optimize(state, getAngle());
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(getSpeed(), getAngle());
    }


    private double prevSpeed = 0;

    @Override
    public void periodic() {
        // drive control
        double speed = getSpeed();
        double accel = speed - prevSpeed;

        setDriveVolts(
            driveFeedforward.calculate(state.speedMetersPerSecond, accel) +
            driveFeedback.update(state.speedMetersPerSecond, speed));

        prevSpeed = speed;

        // turn control
        setTurnVolts(
            turnFeedback.update(state.angle, getAngle()));
    }

}
