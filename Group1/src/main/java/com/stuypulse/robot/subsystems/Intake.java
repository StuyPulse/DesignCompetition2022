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

import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.simulation.LinearSystemSim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.stuypulse.robot.constants.Settings.Intake.PID.*;
import static com.stuypulse.robot.constants.Settings.Intake.FF.*;
import static com.stuypulse.robot.constants.Settings.Intake.SIMULATION.*;

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
 * stop()
 */
public class Intake extends SubsystemBase {
    /** HARDWARE */
    private final MotorControllerGroup driver;
    private final CANSparkMax deploy;
    private final Encoder grayhill;

    /** CONTROL */
    private final Controller controller;

    private final SmartAngle targetAngle;

    /** SIMULATION */
    private EncoderSim encoderSim;
    private LinearSystemSim<N2, N1, N1> intakeSim;
    private MechanismLigament2d intakeArm;
    private MechanismLigament2d targetArm;

    public Intake() {
        setSubsystem("Intake");

        /** HARDWARE */
        CANSparkMax left = new CANSparkMax(Ports.Intake.LEFT_DRIVER, MotorType.kBrushless);
        CANSparkMax right = new CANSparkMax(Ports.Intake.RIGHT_DRIVER, MotorType.kBrushless);
        Motors.Intake.driver.configure(left, right);
        driver = new MotorControllerGroup(left, right);
        addChild("Driver Motors", driver);
        deploy = new CANSparkMax(Ports.Intake.DEPLOY, MotorType.kBrushless);
        Motors.Intake.deploy.configure(deploy);

        grayhill = new Encoder(Ports.Intake.DEPLOYER_A, Ports.Intake.DEPLOYER_B);
        grayhill.setDistancePerPulse(Settings.Intake.DISTANCE_PER_PULSE);
        addChild("Grayhill", grayhill);

        /** CONTROL */
        controller = new PIDController(kP, kI, kD).add(new Feedforward.Motor(kS, kV, kA).position());

        targetAngle = new SmartAngle("Intake/Target Angle", Angle.fromDegrees(0));

        /** SIMULATION */
        encoderSim = new EncoderSim(grayhill);
        intakeSim = new LinearSystemSim<>(LinearSystemId.identifyPositionSystem(kV.get(), kA.get()));
        Mechanism2d mech = new Mechanism2d(WIDTH, HEIGHT);
        MechanismRoot2d root = mech.getRoot("Intake Root", ROOT_WIDTH, ROOT_HEIGHT);
        intakeArm = root.append(
                new MechanismLigament2d(
                        "Intake Arm",
                        ARM_WIDTH,
                        Settings.Intake.RETRACT_ANGLE.get().toDegrees()));
        targetArm = root.append(
            new MechanismLigament2d(
                    "Intake Target Arm",
                    ARM_WIDTH,
                    targetAngle.get().toDegrees()));
        addChild("Intake Sim", mech);
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
        driver.set(-1);
    }

    public void stop() {
        driver.set(0);
    }

    @Override
    public void periodic() {
        deploy.setVoltage(controller.update(targetAngle.get().toDegrees(), grayhill.getDistance()));
    }

    @Override
    public void simulationPeriodic() {
        intakeSim.setInput(deploy.get() * RoboRioSim.getVInVoltage());

        intakeSim.update(0.02);
        encoderSim.setDistance(intakeSim.getOutput(0));
        RoboRioSim.setVInVoltage(BatterySim.calculateDefaultBatteryLoadedVoltage(
            intakeSim.getCurrentDrawAmps()));
        intakeArm.setAngle(encoderSim.getDistance());
        targetArm.setAngle(targetAngle.get().toDegrees());
    }
}