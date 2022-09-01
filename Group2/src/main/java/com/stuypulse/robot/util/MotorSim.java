package com.stuypulse.robot.util;

import java.util.function.Function;

import com.stuypulse.stuylib.math.SLMath;

import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.simulation.LinearSystemSim;

/**
 * A simulated motor intended to be a simple replacement for
 * real motors. Internally uses a LinearSystemSim constructed
 * based on the given motor type. Must be updated every tick.
 * Holds a reference to an EncoderSim which can be accessed
 * through .getEncoder().
 * 
 * @author Ben Goldfisher
 */
public class MotorSim implements MotorController, Sendable {

    public enum MotorType {
        CIM(DCMotor::getCIM),
        FALCON(DCMotor::getFalcon500),
        NEO(DCMotor::getNEO),
        NEO550(DCMotor::getNeo550),
        ROMI(DCMotor::getRomiBuiltIn);

        private Function<Integer, DCMotor> dcmotor;

        private MotorType(Function<Integer, DCMotor> dcmotor) {
            this.dcmotor = dcmotor;
        }

        public DCMotor getMotor(int num) {
            return this.dcmotor.apply(num);
        }
    };

    private final LinearSystemSim<N2, N1, N2> sim;
    private final EncoderSim encoder;

    private double speed;
    private boolean disabled;
    private boolean inverted;

    public MotorSim(MotorType motor, int motorCount, double gearing) {
        // TODO: make moment of inertia not constant
        sim = new LinearSystemSim<>(
            LinearSystemId.createDCMotorSystem(
                motor.getMotor(motorCount),
                0.00032,
                gearing));

        encoder = new EncoderSim();
    }

    public MotorSim(MotorType motor, double gearing) {
        this(motor, 1, gearing);
    }

    @Override
    public void disable() {
        disabled = true;
    }

    /**
     * @return current set speed of motor in range -1.0, 1.0
     */
    @Override
    public double get() {
        return speed;
    }

    @Override
    public boolean getInverted() {
        return inverted;
    }

    /**
     * Set target velocity of motor
     * 
     * @param speed velocity in range -1.0, 1.0
     */
    @Override
    public void set(double speed) {
        if (disabled) {
            speed = 0;
        }

        this.speed = SLMath.clamp(speed, -1, 1);
        sim.setInput(this.speed);
    }

    @Override
    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    @Override
    public void setVoltage(double outputVolts) {
        set(outputVolts / 12.0);
    }

    @Override
    public void stopMotor() {
        set(0);
    }

    public void update(double dtSeconds) {
        sim.update(dtSeconds);
        encoder.update(dtSeconds, getRadPerSecond());
    }

    // returns motor's angular velocity
    public double getRadPerSecond() {
        return sim.getOutput(1) * (inverted ? -1 : 1);
    }

    public EncoderSim getEncoder() {
        return encoder;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("MotorSim");
        builder.addDoubleProperty("angular velocity", this::getRadPerSecond, null);
        builder.addDoubleProperty("set speed", this::get, this::set);
        builder.addBooleanProperty("inverted", this::getInverted, this::setInverted);
    }

}
