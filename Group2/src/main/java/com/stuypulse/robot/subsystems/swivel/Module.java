package com.stuypulse.robot.subsystems.swivel;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Module extends SubsystemBase {
    private final CANSparkMax turnMotor;
    private final CANSparkMax driveMotor;

    private final RelativeEncoder turnEncoder;
    private final RelativeEncoder driveEncoder;

    private double targetSpeed;
    private Rotation2d targetAngle;

    // TODO: pass in ports, location, etc
    public Module() {
        driveMotor = new CANSparkMax(-1, MotorType.kBrushless);
        turnMotor = new CANSparkMax(-1, MotorType.kBrushless);

        driveEncoder = driveMotor.getEncoder();
        turnEncoder = turnMotor.getEncoder();
    }


    public void setState(SwerveModuleState state) {
        state = SwerveModuleState.optimize(state, getAngle());

        targetSpeed = state.speedMetersPerSecond;
        targetAngle = state.angle;
    }
}
