package com.stuypulse.robot.subsystems;

import static com.stuypulse.robot.constants.Ports.Intake.*;
import static com.stuypulse.robot.constants.Motors.Intake.*;
import static com.stuypulse.robot.constants.Settings.Intake.*;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
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
    private CANSparkMax leftDriverMotor;
    private CANSparkMax rightDriverMotor;
    private TalonFX deploymentMotor;

    private Controller controller;
    private SmartNumber targetAngle;

    public Intake() {
        leftDriverMotor = new CANSparkMax(LEFT_DRIVER, MotorType.kBrushless);
        leftDriverMotor = new CANSparkMax(RIGHT_DRIVER, MotorType.kBrushless);
        deploymentMotor = new TalonFX(DEPLOYMENT);

        LEFT_DRIVER_CONFIG.configure(leftDriverMotor);
        RIGHT_DRIVER_CONFIG.configure(rightDriverMotor);
        DEPLOYMENT_CONFIG.configure(deploymentMotor);

        controller = Deployment.FB.getController().add(Deployment.FF.getFeedforward());
        targetAngle = new SmartNumber("Intake/Target Angle", 0.0);
    }

    public void acquire() {
        leftDriverMotor.set(ACQUIRE_SPEED.get());
        rightDriverMotor.set(ACQUIRE_SPEED.get());
    }

    public void deaquire() {
        leftDriverMotor.set(DEACQUIRE_SPEED.get());
        rightDriverMotor.set(DEACQUIRE_SPEED.get());
    }
    
    public void stop() {
        leftDriverMotor.stopMotor();
        rightDriverMotor.stopMotor();
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
        deploymentMotor.setSelectedSensorPosition(position);
        this.targetAngle.set(position);
    }

    public double getAngle() {
        return deploymentMotor.getSelectedSensorPosition();
    }
    
    public double getVelocity() {
        return deploymentMotor.getSelectedSensorVelocity();
    }

    @Override
    public void periodic() {
        if (!controller.isDone(Deployment.MAX_ERROR.get())) {
            deploymentMotor.set(
                TalonFXControlMode.Position,
                controller.update(targetAngle.get(), getAngle())
            );
        } else {
            deploymentMotor.set(TalonFXControlMode.Current, 0);;
        }
        
        SmartDashboard.putNumber("Intake/Left Driver Speed", leftDriverMotor.get());
        SmartDashboard.putNumber("Intake/Right Driver Speed", rightDriverMotor.get());
        SmartDashboard.putNumber("Intake/Deployment Angle", getAngle());
        SmartDashboard.putNumber("Intake/Deployment Speed", getVelocity());
    }   
}
