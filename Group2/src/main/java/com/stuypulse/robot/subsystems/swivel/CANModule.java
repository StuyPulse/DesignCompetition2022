package com.stuypulse.robot.subsystems.swivel;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.constants.Modules.ModuleConfig;
import com.stuypulse.robot.constants.Settings.Swivel;
import com.stuypulse.robot.constants.Ports;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class CANModule extends SwivelModule {
    
    private final CANSparkMax turnMotor;
    private final CANSparkMax driveMotor;

    private final DutyCycleEncoder turnEncoder;
    private final RelativeEncoder driveEncoder;

    private final Rotation2d offset;

    public CANModule(ModuleConfig config) {
        super(config);

        driveMotor = new CANSparkMax(Ports.Swivel.DRIVE_MOTOR, MotorType.kBrushless);
        turnMotor = new CANSparkMax(Ports.Swivel.TURN_MOTOR, MotorType.kBrushless);

        driveEncoder = driveMotor.getEncoder();
        turnEncoder = new DutyCycleEncoder(Ports.Swivel.ABSOLUTE_ENCODER);

        // by default the position is in rotations
        driveEncoder.setPositionConversionFactor(Swivel.WHEEL_CIRCUMFERENCE);
        // by defqault velocity is in rpm
        driveEncoder.setVelocityConversionFactor(Math.PI * Swivel.WHEEL_DIAMETER / 60.0);

        offset = config.zeroAngle;
    }


    @Override
    protected void setDriveVolts(double volts) {
        driveMotor.setVoltage(volts);
    }

    @Override
    protected void setTurnVolts(double volts) {
        turnMotor.setVoltage(volts);
    }

    @Override
    protected Rotation2d getAngle() {
        double radians = turnEncoder.getAbsolutePosition() % (2 * Math.PI);
        if (radians > Math.PI)
            radians -= 2 * Math.PI;
    
        return new Rotation2d(radians).minus(offset);
    }

    @Override
    protected double getSpeed() {
        return driveEncoder.getVelocity();
    }

}
