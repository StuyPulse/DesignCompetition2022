package com.stuypulse.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.control.feedforward.Feedforward;
import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
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

    private ElevatorSim elevatorSim;
    public MechanismLigament2d elevatorSimArm;
    public EncoderSim encoderSim;

    public Elevator() {
        setSubsystem("Elevator");

        /** HARDWARE */
        WPI_TalonSRX firstMotor = new WPI_TalonSRX(Ports.Elevator.FIRST);
        WPI_TalonSRX secondMotor = new WPI_TalonSRX(Ports.Elevator.SECOND);
        WPI_TalonSRX thirdMotor = new WPI_TalonSRX(Ports.Elevator.THIRD);
        Motors.Elevator.elevator.configure(firstMotor, secondMotor, thirdMotor);
        motors = new MotorControllerGroup(firstMotor, secondMotor, thirdMotor);
        addChild("Motor Controller", motors);

        /** CONTROL */
        controller = new PIDController(kP, kI, kD)
                .add(new Feedforward.Elevator(kG, kS, kV, kA).position());

        lowerLimitSwitch = new DigitalInput(Ports.Elevator.LOWER);
        addChild("Lower Limit Switch", lowerLimitSwitch);
        upperLimitSwitch = new DigitalInput(Ports.Elevator.UPPER);
        addChild("Upper Limit Switch", upperLimitSwitch);

        grayhill = new Encoder(Ports.Elevator.A, Ports.Elevator.B);
        grayhill.setDistancePerPulse(Settings.Elevator.DISTANCE_PER_PULSE);
        addChild("Encoder", grayhill);

        targetDistance = new SmartNumber("Elevator/Target Distance", 0.0);

        /** SIMULATION */
        elevatorSim = new ElevatorSim(LinearSystemId.identifyPositionSystem(kV.get(), kA.get()), DCMotor.getFalcon500(3), GEARING, DRUM_RADIUS, MIN_ELEVATOR_HEIGHT, MAX_ELEVATOR_HEIGHT);
        encoderSim = new EncoderSim(grayhill);

        Mechanism2d mech = new Mechanism2d(WIDTH, HEIGHT);
        MechanismRoot2d root = mech.getRoot("Elevator Root", ROOT_WIDTH, HEIGHT);
        elevatorSimArm = root.append(
                new MechanismLigament2d(
                        "Arm",
                        ARM_WIDTH, 0));
        addChild("Elevator Sim", mech);
    }

    public void setPosition(double distance) {
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

    private void setVoltage(double volts) {
        if (upperLimitSwitch.get() && volts > 0.0) volts = 0.0;
        else if (lowerLimitSwitch.get() && volts < 0.0) volts = 0.0;
        motors.setVoltage(volts);
    }

    @Override
    public void periodic() {
       setVoltage(controller.update(targetDistance.get(),
       grayhill.getDistance()));
    }

    @Override
    public void simulationPeriodic() {
        elevatorSim.setInput(motors.get() * RoboRioSim.getVInVoltage());
        elevatorSim.update(0.02);
        encoderSim.setDistance(elevatorSim.getOutput(0));
        RoboRioSim.setVInVoltage(BatterySim.calculateDefaultBatteryLoadedVoltage(elevatorSim.getCurrentDrawAmps()));
        elevatorSimArm.setLength(encoderSim.getDistance());
    }
}
