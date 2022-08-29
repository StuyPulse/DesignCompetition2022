package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.constants.Settings.Intake.Control;
import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.math.Angle;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Intake/grab cubes
 *
 * Contains:
 *   - 2 drive motors (NEO)
 *   - 1 deploy motor (NEO)
 *   - 1 relative encoder
 * 
 * @author Yuchen Pan
 * @author Zixi Feng
 * @author Maximillian Zeng
*/

public class Intake extends SubsystemBase {

    private CANSparkMax deploy;
    private CANSparkMax driverMotorA;
    private CANSparkMax driverMotorB;
    
    private RelativeEncoder encoder;
    
    private AngleController controller;
    private Angle targetAngle;

    private double speed;

    public Intake() {
        driverMotorA = new CANSparkMax(Ports.Intake.DRIVER_A, MotorType.kBrushless);
        driverMotorB = new CANSparkMax(Ports.Intake.DRIVER_B, MotorType.kBrushless);

        deploy = new CANSparkMax(Ports.Intake.DEPLOY, MotorType.kBrushless);
        encoder = deploy.getEncoder();

        controller = Control.getControl();

        targetAngle = Settings.Intake.RETRACT_ANGLE.get();
    }


    /*** Drive Motors ***/

    private void set(double speed) {
        this.speed = speed;
        driverMotorA.set(speed);
        driverMotorB.set(speed);
    }

    public void acquire() {
        set(Settings.Intake.ACQUIRE_SPEED.get());
    }

    public void deacquire() {
        set(Settings.Intake.DEACQUIRE_SPEED.get());
    }

    public void stop() {
        driverMotorA.stopMotor();
        driverMotorB.stopMotor();
    }

    /*** Deploy Motor ***/

    public Angle getAngle() {
        return Angle.fromRotations(encoder.getPosition());
    }

    private void setAngle(Angle angle) {
        targetAngle = angle;
    }

    public void retract() {
        setAngle(Settings.Intake.RETRACT_ANGLE.get());
    }

    public void extend() {
        setAngle(Settings.Intake.EXTEND_ANGLE.get());
    }
    

    @Override
    public void periodic() {
        deploy.set(controller.update(targetAngle, getAngle()));

        SmartDashboard.putNumber("Intake/Angle", getAngle().toDegrees());
        SmartDashboard.putNumber("Intake/Speed", speed);
    }

}
