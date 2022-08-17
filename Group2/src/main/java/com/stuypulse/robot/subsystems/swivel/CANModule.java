package com.stuypulse.robot.subsystems.swivel;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.constants.Modules.ModuleConfig;
import com.stuypulse.robot.constants.Settings.Swivel;

import edu.wpi.first.math.geometry.Rotation2d;

public class CANModule extends SwivelModule {
    
    private final CANSparkMax turnMotor;
    private final CANSparkMax driveMotor;

    private final RelativeEncoder turnEncoder;
    private final RelativeEncoder driveEncoder;

    public CANModule(ModuleConfig config) {
        super(config);

        driveMotor = new CANSparkMax(-1, MotorType.kBrushless);
        turnMotor = new CANSparkMax(-1, MotorType.kBrushless);

        driveEncoder = driveMotor.getEncoder();
        turnEncoder = turnMotor.getEncoder();

        // by default the position is in rotations
        driveEncoder.setPositionConversionFactor(Swivel.WHEEL_CIRCUMFERENCE);
        // by defqault velocity is in rpm
        driveEncoder.setVelocityConversionFactor(Math.PI * Swivel.WHEEL_DIAMETER / 60.0);

        turnEncoder.setPositionConversionFactor(1.0 / 360.0);
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
        return Rotation2d.fromDegrees(turnEncoder.getPosition());
    }

    @Override
    protected double getSpeed() {
        return driveEncoder.getVelocity();
    }

}
