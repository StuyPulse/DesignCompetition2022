package com.stuypulse.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;

import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.constants.Motors;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.control.feedforward.Feedforward;
import com.stuypulse.stuylib.network.SmartNumber;

import static com.stuypulse.robot.constants.Ports.Drivetrain.Grayhill.*;
import static com.stuypulse.robot.constants.Settings.Drivetrain.PID.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

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
 * 
 * 
 * @author Kelvin Zhao
 * @author Vincent Wang
 * @author Samuel Chen
 * @author Amber Shen
 * @author Carmin Vuong
 */

public class Drivetrain extends IDrivetrain {
    /** CONTROLS */
    private final DifferentialDriveKinematics driveKinematics;
    private final Controller leftController;
    private final Controller rightController;

    private final SmartNumber leftTargetSpeed;
    private final SmartNumber rightTargetSpeed;

    /** HARDWARE */
    private final MotorControllerGroup left;
    private final MotorControllerGroup right;

    private final Encoder leftGrayhill;
    private final Encoder rightGrayhill;

    private final AHRS navx;

    /** ODOMETRY */
    public final DifferentialDriveOdometry odometry;

    private final Field2d field;

    /** SIMULATION */
    private final DifferentialDrivetrainSim drivetrainSim;
    private final EncoderSim leftEncoderSim;
    private final EncoderSim rightEncoderSim;

    public Drivetrain() {
        setSubsystem("Drivetrain");
        /** CONTROL */
        leftController = new PIDController(kP, kI, kD).add(new Feedforward.Drivetrain(kS, kV, kA).velocity());
        rightController = new PIDController(kP, kI, kD).add(new Feedforward.Drivetrain(kS, kV, kA).velocity());

        driveKinematics = new DifferentialDriveKinematics(Settings.Drivetrain.TRACK_WIDTH);

        leftTargetSpeed = new SmartNumber("Drivetrain/Left Target Speed", 0.0);
        rightTargetSpeed = new SmartNumber("Drivetrain/Right Target Speed", 0.0);

        /** MOTORS */
        WPI_TalonSRX leftFront = new WPI_TalonSRX(Ports.Drivetrain.LEFT_FRONT);
        WPI_TalonSRX leftMiddle = new WPI_TalonSRX(Ports.Drivetrain.LEFT_MIDDLE);
        WPI_TalonSRX leftBack = new WPI_TalonSRX(Ports.Drivetrain.LEFT_BACK);
        Motors.Drivetrain.left.configure(leftFront, leftMiddle, leftBack);
        left = new MotorControllerGroup(leftFront, leftMiddle, leftBack);
        addChild("Left Motor Controller Group", left);

        WPI_TalonSRX rightFront = new WPI_TalonSRX(Ports.Drivetrain.RIGHT_FRONT);
        WPI_TalonSRX rightMiddle = new WPI_TalonSRX(Ports.Drivetrain.RIGHT_MIDDLE);
        WPI_TalonSRX rightBack = new WPI_TalonSRX(Ports.Drivetrain.RIGHT_BACK);
        Motors.Drivetrain.right.configure(rightFront, rightMiddle, rightBack);
        right = new MotorControllerGroup(rightFront, rightMiddle, rightBack);
        addChild("Right Motor Controller Group", right);

        /** ENCODERS */
        leftGrayhill = new Encoder(LEFT_A, LEFT_B);
        leftGrayhill.setDistancePerPulse(Settings.Drivetrain.DISTANCE_PER_PULSE);
        rightGrayhill = new Encoder(RIGHT_A, RIGHT_B);
        rightGrayhill.setDistancePerPulse(Settings.Drivetrain.DISTANCE_PER_PULSE);
        addChild("Left Encoder", leftGrayhill);
        addChild("Right Encoder", rightGrayhill);

        /** GYRO */
        navx = new AHRS(SPI.Port.kMXP);
        addChild("Gyroscope", navx);

        /** ODOMETRY */
        odometry = new DifferentialDriveOdometry(navx.getRotation2d());

        field = new Field2d();
        addChild("Field", field);

        /** SIMULATION */
        drivetrainSim = new DifferentialDrivetrainSim(
                LinearSystemId.identifyDrivetrainSystem(kV.get(), kA.get(), kVA.get(), kAA.get(),
                        Settings.Drivetrain.TRACK_WIDTH),
                DCMotor.getFalcon500(3), 2.7, Settings.Drivetrain.TRACK_WIDTH, Settings.Drivetrain.WHEEL_DIAMETER,
                null);
        leftEncoderSim = new EncoderSim(leftGrayhill);
        rightEncoderSim = new EncoderSim(rightGrayhill);
    }

    @Override
    public Rotation2d getAngle() {
        return navx.getRotation2d();
    }

    @Override
    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    @Override
    public void reset(Pose2d location) {
        leftGrayhill.reset();
        rightGrayhill.reset();
        navx.reset();

        odometry.resetPosition(location, getAngle());
    }

    @Override
    public DifferentialDriveWheelSpeeds getSpeeds() {
        return new DifferentialDriveWheelSpeeds(leftGrayhill.getRate(),
                rightGrayhill.getRate());
    }

    @Override
    public void tankDrive(double left, double right) {
        leftTargetSpeed.set(left);
        rightTargetSpeed.set(right);
    }

    @Override
    public void arcadeDrive(double speed, double angle) {
        var wheelSpeeds = driveKinematics.toWheelSpeeds(new ChassisSpeeds(speed, 0, angle));
        tankDrive(wheelSpeeds.leftMetersPerSecond, wheelSpeeds.rightMetersPerSecond);
    }

    @Override
    public void periodic() {
        odometry.update(getAngle(),
                leftGrayhill.getDistance(), rightGrayhill.getDistance());
        left.setVoltage(leftController.update(leftTargetSpeed.get(),
                leftGrayhill.getRate()));
        right.setVoltage(rightController.update(rightTargetSpeed.get(),
                rightGrayhill.getRate()));

        field.setRobotPose(getPose());
    }

    @Override
    public void simulationPeriodic() {
        var speeds = driveKinematics.toChassisSpeeds(getSpeeds());
        navx.setAngleAdjustment(navx.getAngle() - Math.toDegrees(speeds.omegaRadiansPerSecond * 0.02));

        drivetrainSim.setInputs(leftController.update(leftTargetSpeed.get(), leftEncoderSim.getRate()),
                rightController.update(rightTargetSpeed.get(), rightEncoderSim.getRate()));
        drivetrainSim.update(0.02);

        leftEncoderSim.setRate(drivetrainSim.getLeftVelocityMetersPerSecond());
        rightEncoderSim.setRate(drivetrainSim.getRightVelocityMetersPerSecond());

        leftEncoderSim.setDistance(drivetrainSim.getLeftPositionMeters());
        rightEncoderSim.setDistance(drivetrainSim.getRightPositionMeters());
    }
}
