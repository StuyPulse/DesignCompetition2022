package com.stuypulse.robot.subsystems;

import static com.stuypulse.robot.constants.Ports.Intake.*;
import static com.stuypulse.robot.constants.Motors.Intake.*;
import static com.stuypulse.robot.constants.Settings.Intake.*;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXSimCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** 
 * Fields: 
 * - Driver motors
 * - Deployment motor
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
    private final MotorControllerGroup drivers;
    private final WPI_TalonFX deployer;
    private final TalonFXSimCollection deployer_sim;

    private final Controller controller;
    private SmartNumber targetAngle;

    public Intake() {
        final CANSparkMax leftDriverMotor = new CANSparkMax(LEFT_DRIVER, MotorType.kBrushless);
        final CANSparkMax rightDriverMotor = new CANSparkMax(RIGHT_DRIVER, MotorType.kBrushless);
        drivers = new MotorControllerGroup(leftDriverMotor, rightDriverMotor);
        deployer = new WPI_TalonFX(DEPLOYMENT);
        deployer_sim = deployer.getSimCollection();

        LEFT_DRIVER_CONFIG.configure(leftDriverMotor);
        RIGHT_DRIVER_CONFIG.configure(rightDriverMotor);
        DEPLOYMENT_CONFIG.configure(deployer);

        controller = Deployment.FB.getPIDController().add(Deployment.FF.getFeedforward());
        targetAngle = new SmartNumber("Intake/Target Angle", 0.0);
    }

    public void acquire() {
        drivers.set(ACQUIRE_SPEED.get());
    }

    public void deacquire() {
        drivers.set(DEACQUIRE_SPEED.get());
    }
    
    public void stop() {
        drivers.stopMotor();
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
        deployer.setSelectedSensorPosition(position);
        this.targetAngle.set(position);
    }

    public double getAngle() {
        return deployer.getSelectedSensorPosition();
    }
    
    public double getVelocity() {
        return deployer.getSelectedSensorVelocity();
    }

    @Override
    public void periodic() {
        deployer.set(
            TalonFXControlMode.Position,
            controller.update(targetAngle.get(), getAngle())
        );
        
        SmartDashboard.putNumber("Intake/Driver Speed", drivers.get());
        SmartDashboard.putNumber("Intake/Deployment Angle", getAngle());
        SmartDashboard.putNumber("Intake/Deployment Speed", getVelocity());
    }   
}
