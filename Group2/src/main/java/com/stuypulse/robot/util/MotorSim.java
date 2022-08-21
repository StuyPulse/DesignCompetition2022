package com.stuypulse.robot.util;

import java.util.function.Function;

import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.LinearSystemSim;

public class MotorSim {

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

    public MotorSim(MotorType motor, int num, int gearing) {
        // TODO: make moment of inertia not constant
        sim = new LinearSystemSim<>(
            LinearSystemId.createDCMotorSystem(
                motor.getMotor(num),
                0.00032,
                gearing));

        encoder = new EncoderSim();
    }

    public void set(double voltage) {
        sim.setInput(voltage);
    }

    public void update(double dtSeconds) {
        sim.update(dtSeconds);
        encoder.update(dtSeconds, getRadPerSecond());
    }

    // returns motor's angular velocity
    public double getRadPerSecond() {
        return sim.getOutput(1);
    }

    public EncoderSim getEncoder() {
        return encoder;
    }

}
