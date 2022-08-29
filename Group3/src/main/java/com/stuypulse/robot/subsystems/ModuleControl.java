package com.stuypulse.robot.subsystems;

import static com.stuypulse.robot.constants.Settings.Swerve.*;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.util.StopWatch;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Fields:
 * Motor (CANSparkMax)
 * Relative Encoder (RelativeEncoder)
 * Controller
 * Target Velocity
 * 
 * Motor (CANSparkMax)
 * Absolute Encoder (DutyCycleEncoder)
 * Controller
 * Target Angle
 * 
 * Methods:
 * Set Target Velocity
 * Get Current Velocity
 * 
 * Set Target Angle
 * Get Current Angle
 */
public class ModuleControl extends SubsystemBase {
    
    /** DRIVE */
    
    private final TalonFX driveMotor;
    private final Controller driveController;
    private final SimpleMotorFeedforward driveFeedforward;

    private double targetVelocity;
    private double previousTargetVelocity;
    private StopWatch velocityTimer;

     /** TURN */

    private final TalonFX turnMotor;
    private final DutyCycleEncoder turnEncoder;
    private final AngleController turnController;

    private Rotation2d targetAngle;
    private Angle offset;

    public ModuleControl(int drivePort, int turnPort, int turnEncoder, Angle turnOffset) {
        
        /** DRIVE */

        driveMotor = new TalonFX(drivePort);
        driveController = Drive.Feedback.getFeedbackController();
        driveFeedforward = Drive.Feedforward.getFeedforwardController();

        targetVelocity = 0.0;
        previousTargetVelocity = 0.0;
        velocityTimer = new StopWatch();

        /** TURN */

        turnMotor = new TalonFX(turnPort);
        this.turnEncoder = new DutyCycleEncoder(turnEncoder);
        this.turnController = Turn.Feedback.getFeedbackController();
        
        this.targetAngle = new Rotation2d();
        this.offset = turnOffset;
    }

    /** DRIVE */

    public double getVelocity() {
        return driveMotor.getSelectedSensorVelocity();
    }

    public void setTargetVelocity(double target) {
        this.targetVelocity = target;
    }

    /** TURN */
    
    public Rotation2d getAngle() {
		return new Rotation2d(getRawRadians()).minus(offset.getRotation2d());
    }

    public double getRawRadians() {
        return Turn.Encoder.getRadians(turnEncoder.getAbsolutePosition());
    }

    public void setTargetAngle(Rotation2d target) {
        this.targetAngle = target;
    }

    /** RESET */

    public void stop() {
        this.targetAngle = new Rotation2d(0);
        this.targetVelocity = 0.0;
    }

    @Override
    public void periodic() {
        double driveAcceleration = (targetVelocity - previousTargetVelocity) / velocityTimer.reset();

        driveMotor.set(TalonFXControlMode.Velocity, 
                        driveFeedforward.calculate(targetVelocity, driveAcceleration) 
                        + driveController.update(targetVelocity, getVelocity()));

        turnMotor.set(TalonFXControlMode.Position, turnController.update(targetAngle, getAngle()));

        previousTargetVelocity = targetVelocity;
    }
}
