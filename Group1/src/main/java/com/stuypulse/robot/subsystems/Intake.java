package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.control.feedforward.Feedforward;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.network.SmartAngle;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.stuypulse.robot.constants.Settings.Intake.PID.*;
import static com.stuypulse.robot.constants.Settings.Intake.FF.*;

import com.stuypulse.robot.constants.Motors;

/**
 * Intake Fields:
 * MotorControllerGroup driver
 * Motor deploy
 * Controller controller (PID and FeedForward)
 * targetAngle
 * deployEncoder
 * 
 * Intake Methods:
 * setAngle()
 * retract()
 * extend()
 * acquire()
 * deacquire()
 */
public class Intake extends SubsystemBase {
    private final MotorControllerGroup driver;
    private final CANSparkMax deploy;
    private final Encoder encoder;

    private final Controller controller;

    private final SmartAngle targetAngle;
    
    public Intake() {
        setSubsystem("Intake");
        CANSparkMax left = new CANSparkMax(Ports.Intake.LEFT_DRIVER, MotorType.kBrushless);
        CANSparkMax right = new CANSparkMax(Ports.Intake.RIGHT_DRIVER, MotorType.kBrushless);
        Motors.Intake.left.configure(left);
        Motors.Intake.right.configure(right);
        driver = new MotorControllerGroup(left, right);
        addChild("Driver Motors", driver);
        
        deploy = new CANSparkMax(Ports.Intake.DEPLOY, MotorType.kBrushless);
        Motors.Intake.deploy.configure(deploy);
        encoder = new Encoder(Ports.Intake.DEPLOYER_A, Ports.Intake.DEPLOYER_B);
        addChild("Deploy Encoder", encoder);
        controller = new PIDController(kP, kI, kD).add(new Feedforward.Motor(kS, kV, kA).position());

        targetAngle = new SmartAngle("Intake/Target Angle", Angle.fromDegrees(0));
    }

    private void setAngle(Angle angle) {
        targetAngle.set(angle);
    }

    public void retract() {
        setAngle(Settings.Intake.RETRACT_ANGLE.get());
    }

    public void extend() {
        setAngle(Settings.Intake.EXTEND_ANGLE.get());
    }

    public void acquire() {
        driver.set(1);
    }

    public void deacquire() {
        driver.set(0);
    }

    @Override
    public void periodic() {
        deploy.setVoltage(controller.update(targetAngle.get().toDegrees(), encoder.getDistance()));
    }
} 