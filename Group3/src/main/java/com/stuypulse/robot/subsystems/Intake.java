package com.stuypulse.robot.subsystems;

import static com.stuypulse.robot.constants.Ports.Intake.*;
import static com.stuypulse.robot.constants.Motors.Intake.*;
import static com.stuypulse.robot.constants.Settings.Intake.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** 
 * Fields: 
 * - Driver motor
 * - Deployment motor
 * - Deployment encoder
 * - Controller
 * - Target Angle
 * 
 * Methods: 
 * - Set TargetAngle
 * - Acquire / Deacquire
 * - Extend / Retract
 * - Get CurrentAngle
 * - periodic
 *
 * @author Ivan Chen 
 * @author Eric Lin
 * @author Niki Chen
 * @author Jiayu Yan
 * @author Ian Jiang
 * @author Jennifer Ye
 */
public class Intake extends SubsystemBase {
    private CANSparkMax driverMotor;
    private CANSparkMax deploymentMotor;
    private RelativeEncoder deploymentEncoder;

    private Controller controller;
    private SmartNumber targetAngle;

    public Intake() {
        driverMotor = new CANSparkMax(DRIVER, MotorType.kBrushless);
        deploymentMotor = new CANSparkMax(DEPLOYMENT, MotorType.kBrushless);

        DRIVER_CONFIG.configure(driverMotor);
        DEPLOYMENT_CONFIG.configure(deploymentMotor);

        deploymentEncoder = deploymentMotor.getEncoder();
        deploymentEncoder.setPositionConversionFactor(POSITION_MULTIPLIER);

        controller = Deployment.getController();
        targetAngle = new SmartNumber("Intake/Target Angle", 0.0);
    }

    public void acquire() {
        driverMotor.set(ACQUIRE_SPEED.get());
    }

    public void deaquire() {
        driverMotor.set(DEACQUIRE_SPEED.get());
    }
    
    public void stop() {
        driverMotor.stopMotor();
    }

    public void setTargetAngle(double angle) {
        this.targetAngle.set(angle);
    }

    public void extend() {
        setTargetAngle(EXTEND_ANGLE.get());
    }

    public void retract() {
        setTargetAngle(RETRACT_ANGLE.get());
    }

    public void reset(double position) {
        deploymentEncoder.setPosition(position);
        this.targetAngle.set(position);
    }

    public double getAngle() {
        return deploymentEncoder.getPosition();
    }

    @Override
    public void periodic() {
        if (!controller.isDone(Deployment.MAX_ERROR.get())) {
            deploymentMotor.set(controller.update(targetAngle.get(), getAngle()));
        } else {
            deploymentMotor.stopMotor();
        }
        
        SmartDashboard.putNumber("Intake/Driver Speed", driverMotor.get());
        SmartDashboard.putNumber("Intake/Deployment Angle", getAngle());
        SmartDashboard.putNumber("Intake/Deployment Speed", deploymentMotor.get());
    }   
}
