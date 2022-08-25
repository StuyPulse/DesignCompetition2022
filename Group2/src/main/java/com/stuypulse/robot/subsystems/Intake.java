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

*/

public class Intake extends SubsystemBase {
    private CANSparkMax deploy;
    private CANSparkMax driverMotor;
    private CANSparkMax deploymentMotor;
    private Controller controller;
    private SmartNumber targetAngle;
    private RelativeEncoder encoder;

    public Intake() {
        driverMotor = new CANSparkMax(DRIVER, MotorType.kBrushless)
        deploymentMotor = new CANSparkMax(DEPLOYMENT, MotorType.kBrushless);

        deploy = new CANSparkMax(Ports.Intake.DEPLOY, MotorType.kBrushless);
        encoder = new Encoder(Ports.Intake.DEPLOYER_A, Ports.Intake.DEPLOYER_B)

        controller = Deployment.getController();

        targetAngle = new SmartNumber("Intake/Target Angle", 0.0)
    }

    public void acquire() {
        driverMotor.set(ACQUIRE_SPEED.get());
    }

    public void deacquire() {
        driverMotor.set(-1 * ACQUIRE_SPEED.get());
    }

    public void stop() {
        driverMotor.stopMotor();
    }

    public double getAngle() {
        return encoder.getPosition();
    }

    private void setAngle(Angle angle) {
        targetAngle.set(angle);
    }

    public void retract() {
        setAngle(RETRACT_ANGLE.get());
    }

    public void extend() {
        setAngle(EXTEND_ANGLE.get());
    }
    
    @Override
    public void periodic() {
        deploy.set(controller.update(targetAngle.get(), ((Encoder) encoder).getDistance()));
    }

}





