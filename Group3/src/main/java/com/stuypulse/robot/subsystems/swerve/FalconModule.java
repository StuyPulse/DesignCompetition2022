package com.stuypulse.robot.subsystems.swerve;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.stuypulse.robot.constants.Settings.Swerve.Turn;
import com.stuypulse.stuylib.math.Angle;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class FalconModule extends SwerveModule {
    
    private final WPI_TalonFX drive;
    private final WPI_TalonFX turn;

    private final DutyCycleEncoder turnEncoder;
    private final Angle offset;

    public FalconModule(String id, Translation2d moduleOffset, int drivePort, int turnPort, int turnEncoderPort, Angle turnOffset) {
        super(id, moduleOffset);

        drive = new WPI_TalonFX(drivePort);
        turn = new WPI_TalonFX(turnPort);

        turnEncoder = new DutyCycleEncoder(turnEncoderPort);
        this.offset = turnOffset;
    }

    @Override
    protected void setTargetVelocity(double velocity) {
        drive.set(ControlMode.Velocity, velocity);
    }

    @Override
    protected void setTargetAngle(double angle) {
        turn.set(ControlMode.Position, angle);
    }

    @Override
    protected Rotation2d getAngle() {
        return new Rotation2d(getRawRadians()).minus(offset.getRotation2d());
    }

    public double getRawRadians() {
        return Turn.Encoder.getRadians(turnEncoder.getAbsolutePosition());
    }

    @Override
    protected double getVelocity() {
        return drive.getSelectedSensorVelocity();
    }
}
