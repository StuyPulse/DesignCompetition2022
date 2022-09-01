package com.stuypulse.robot.subsystems.intake;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.stuylib.math.Angle;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class CANIntake extends Intake {

    private final CANSparkMax deployMotor;
    private final MotorControllerGroup driveMotors;
    
    private final RelativeEncoder encoder;

    public CANIntake() {
        driveMotors = new MotorControllerGroup(
            new CANSparkMax(Ports.Intake.DRIVER_A, MotorType.kBrushless),
            new CANSparkMax(Ports.Intake.DRIVER_B, MotorType.kBrushless)
        );

        deployMotor = new CANSparkMax(Ports.Intake.DEPLOY, MotorType.kBrushless);
        encoder = deployMotor.getEncoder();
    }

    @Override
    public void set(double speed) {
        driveMotors.set(speed);
    }

    @Override
    public void stop() {
        driveMotors.stopMotor();
    }

    @Override
    public Angle getAngle() {
        return Angle.fromRotations(encoder.getPosition());
    }

    @Override
    public void setDeploy(double speed) {
        deployMotor.set(speed);
    }
    
}
