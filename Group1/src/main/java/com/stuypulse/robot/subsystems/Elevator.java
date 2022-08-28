package com.stuypulse.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.control.feedforward.Feedforward;
import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.stuypulse.robot.constants.Motors;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;

import static com.stuypulse.robot.constants.Settings.Elevator.PID.*;
import static com.stuypulse.robot.constants.Settings.Elevator.FF.*;
import static com.stuypulse.robot.constants.Settings.Elevator.SIMULATION.*;

/**
 * @author Kelvin Zhao
 * @author Amber Shen
 * @author Samuel Chen
 * @author Vincent Wang
 * @author Carmin Vong
 * 
 * Elevator fields:
 * - Motor Controller Group
 * - Controller
 * - Digital Input (Limit Switches)?
 * - Encoder?
 * - Target Distance
 * 
 * Elevator methods:
 * - setPosition()
 * - setBox()
 * - setScale()
 * - setSwitch()
 * - setClimb()
 *
 * Heights:
 * rung 7ft
 * scale 6ft 4in
 * switch 15 in
 */

public class Elevator extends SubsystemBase {
    
    private final MotorControllerGroup motors;
    private final Controller controller;
    private final DigitalInput lowerLimitSwitch;
    private final DigitalInput upperLimitSwitch;

    private final Encoder grayhill;

    private SmartNumber targetDistance;

    private Mechanism2d elevator;

    public Elevator() {
        setSubsystem("Elevator");
        WPI_TalonSRX firstMotor = new WPI_TalonSRX(Ports.Elevator.FIRST);
        WPI_TalonSRX secondMotor = new WPI_TalonSRX(Ports.Elevator.SECOND);
        WPI_TalonSRX thirdMotor = new WPI_TalonSRX(Ports.Elevator.THIRD);
        Motors.Elevator.elevator.configure(firstMotor, secondMotor, thirdMotor);
        motors = new MotorControllerGroup(firstMotor, secondMotor, thirdMotor);
        addChild("Motor Controller", motors);

        controller = new PIDController(kP, kI, kD)
                            .add(new Feedforward.Elevator(kG, kS, kV, kA).position());

        lowerLimitSwitch = new DigitalInput(Ports.Elevator.LOWER);
        addChild("Lower Limit Switch", lowerLimitSwitch);
        upperLimitSwitch = new DigitalInput(Ports.Elevator.UPPER);
        addChild("Upper Limit Switch", upperLimitSwitch);
        grayhill = new Encoder(Ports.Elevator.A, Ports.Elevator.B);
        addChild("Encoder", grayhill);

        targetDistance = new SmartNumber("Elevator/Target Distance", 0.0);
        
        elevator = new Mechanism2d(WIDTH, HEIGHT);

        MechanismRoot2d root = elevator.getRoot("Elevator", ROOT_WIDTH, HEIGHT);
        
        
    }

    private void setPosition(double distance) {
        targetDistance.set(distance);
    }

    public void setBox() {
        setPosition(Settings.Elevator.BOX);
    }

    public void setScale() {
        setPosition(Settings.Elevator.SCALE);
    }

    public void setSwitch() {
        setPosition(Settings.Elevator.SWITCH);
    }

    public void setClimb() {
        setPosition(Settings.Elevator.RUNG);
    }

    @Override
    public void periodic() {
        motors.setVoltage(controller.update(targetDistance.get(), 
                        grayhill.getDistance()));

    }
}
