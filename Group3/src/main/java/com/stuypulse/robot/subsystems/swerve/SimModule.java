package com.stuypulse.robot.subsystems.swerve;

import com.stuypulse.robot.constants.Settings.Swerve.Turn;
import com.stuypulse.robot.util.EncoderSim;
import com.stuypulse.robot.util.MotorSim;
import com.stuypulse.robot.util.MotorSim.MotorType;
import com.stuypulse.stuylib.math.Angle;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class SimModule extends SwerveModule {
    
    private final MotorSim drive;
    private final MotorSim turn;

    private final EncoderSim driveEncoder;
    private final DutyCycleEncoder turnEncoder;
    private final Angle offset;

    public SimModule(String id, Translation2d moduleOffset, int drivePort, int turnPort, int turnEncoderPort, Angle turnOffset) {
        super(id, moduleOffset);

        drive = new MotorSim(MotorType.FALCON, 1, 1);
        turn = new MotorSim(MotorType.FALCON, 1, 1);

        driveEncoder = drive.getEncoder();
        turnEncoder = new DutyCycleEncoder(turnEncoderPort);
        this.offset = turnOffset;
    }

    @Override
    protected void setTargetVelocity(double velocity) {
        drive.set(velocity);
    }

    @Override
    protected void setTargetAngle(double angle) {
        turn.set(angle);
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
        return driveEncoder.getVelocity();
    }

    @Override
    public void simulationPeriodic() {
        drive.update(0.2);
        turn.update(0.2);
    }
}
