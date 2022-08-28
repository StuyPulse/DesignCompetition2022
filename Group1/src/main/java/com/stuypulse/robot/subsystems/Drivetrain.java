package com.stuypulse.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.constants.Motors;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.control.feedforward.Feedforward;
import com.stuypulse.stuylib.network.SmartNumber;

import static com.stuypulse.robot.constants.Ports.Drivetrain.Grayhill.*;
import static com.stuypulse.robot.constants.Settings.Drivetrain.PID.*;
import static com.stuypulse.robot.constants.Settings.Drivetrain.FF.*;

/**
 * Drivetrain fields:
 * 
 * Control Group:
 * - FeedForward
 * - PIDController
 * - Kinematics
 * 
 * Hardware stuff:
 * - Motor controller group (two)
 * - Gyroscope (navx)
 * - Encoders (grayhills)
 * 
 * Drivetrain methods:
 * - arcadeDrive()
 * - tankDrive()
 * - reset()
 * - getAngle()
 */

public class Drivetrain extends SubsystemBase {
    /** CONTROLS */
    private final DifferentialDriveKinematics driveKinematics;
    private final Controller leftController;
    private final Controller rightController;

    private SmartNumber leftTargetSpeed;
    private SmartNumber rightTargetSpeed;

    /** HARDWARE */
    private final MotorControllerGroup left;
    private final MotorControllerGroup right;

    private final Encoder leftGrayhill;
    private final Encoder rightGrayhill;

    private final AHRS navx;

    /** ODOMETRY */
    public final DifferentialDriveOdometry odometry;

    private final Field2d field;

    public Drivetrain() {
        setSubsystem("Drivetrain");
        /** CONTROL */
        leftController = new PIDController(kP, kI, kD).add(new Feedforward.Drivetrain(kS, kV, kA).velocity());
        rightController = new PIDController(kP, kI, kD).add(new Feedforward.Drivetrain(kS, kV, kA).velocity());

        driveKinematics = new DifferentialDriveKinematics(Settings.Drivetrain.TRACK_WIDTH);

        leftTargetSpeed = new SmartNumber("Drivetrain/Left Target Speed", 0.0);
        rightTargetSpeed = new SmartNumber("Drivetrain/Right Target Speed", 0.0);

        /** MOTORS */
        CANSparkMax leftFront = new CANSparkMax(Ports.Drivetrain.LEFT_FRONT, MotorType.kBrushed);
        CANSparkMax leftMiddle = new CANSparkMax(Ports.Drivetrain.LEFT_MIDDLE, MotorType.kBrushless);
        CANSparkMax leftBottom = new CANSparkMax(Ports.Drivetrain.LEFT_BOTTOM, MotorType.kBrushless);
        Motors.Drivetrain.left.configure(leftFront, leftMiddle, leftBottom);
        left = new MotorControllerGroup(leftFront, leftMiddle, leftBottom);
        addChild("Left Motor Controller Group", left);

        CANSparkMax rightFront = new CANSparkMax(Ports.Drivetrain.RIGHT_FRONT, MotorType.kBrushless);
        CANSparkMax rightMiddle = new CANSparkMax(Ports.Drivetrain.RIGHT_MIDDLE, MotorType.kBrushless);
        CANSparkMax rightBottom = new CANSparkMax(Ports.Drivetrain.RIGHT_BOTTOM, MotorType.kBrushless);
        Motors.Drivetrain.right.configure(rightFront, rightMiddle, rightBottom);
        right = new MotorControllerGroup(rightFront, rightMiddle, rightBottom);
        addChild("Right Motor Controller Group", right);

        /** ENCODERS */
        leftGrayhill = new Encoder(LEFT_A, LEFT_B);
        rightGrayhill = new Encoder(RIGHT_A, RIGHT_B);
        addChild("Left Encoder", leftGrayhill);
        addChild("Right Encoder", rightGrayhill);

        /** GYRO */
        navx = new AHRS(SPI.Port.kMXP);
        addChild("Gyroscope", navx);

        /** ODOMETRY */
        odometry = new DifferentialDriveOdometry(navx.getRotation2d());

        field = new Field2d();
        addChild("Field", field);
    }

    public Rotation2d getAngle() {
        return navx.getRotation2d();
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public void reset(Pose2d location) {
        leftGrayhill.reset();
        rightGrayhill.reset();
        navx.reset();

        odometry.resetPosition(location, getAngle());
    }

    private void setSpeed(double left, double right) {
        leftTargetSpeed.set(left);
        rightTargetSpeed.set(right);
    }

    private void setSpeed(DifferentialDriveWheelSpeeds speeds) {
        leftTargetSpeed.set(speeds.leftMetersPerSecond);
        rightTargetSpeed.set(speeds.rightMetersPerSecond);
    }

    public DifferentialDriveWheelSpeeds getSpeed() {
        return new DifferentialDriveWheelSpeeds(leftGrayhill.getRate(), 
                                                rightGrayhill.getRate());
    }

    public void tankDrive(double left, double right) {
        setSpeed(left, right);
    }

    public void arcadeDrive(double speed, double angle) {
        setSpeed(driveKinematics.toWheelSpeeds(
                new ChassisSpeeds(speed, 0, angle)));
    }
    
    public void setSpeeds(double leftVoltage, double rightVoltage) {
        left.setVoltage(leftController.update(leftVoltage, leftGrayhill.getRate()));
        right.setVoltage(rightController.update(rightVoltage, rightGrayhill.getRate()));
    }

    @Override
    public void periodic() {
        odometry.update(getAngle(),
                leftGrayhill.getDistance(), rightGrayhill.getDistance());
        left.setVoltage(leftController.update(leftTargetSpeed.get(),
                leftGrayhill.getRate()));
        right.setVoltage(rightController.update(rightTargetSpeed.get(),
                rightGrayhill.getRate()));

        field.setRobotPose(odometry.getPoseMeters());
    }
}
