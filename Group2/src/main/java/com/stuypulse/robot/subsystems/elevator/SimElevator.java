package com.stuypulse.robot.subsystems.elevator;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.Elevator;
import com.stuypulse.stuylib.math.SLMath;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;

public class SimElevator extends Elevator {

    private final ElevatorSim sim;
    private double distance;

    public SimElevator() {
        sim = new ElevatorSim(
            DCMotor.getNEO(2),
            1.0 / Settings.Elevator.GEARING,
            Settings.Elevator.WEIGHT,
            Settings.Elevator.OUTPUT_DIAMETER / 2.0,
            Settings.Elevator.MIN_INTAKE_HEIGHT,
            Settings.Elevator.MAX_INTAKE_HEIGHT);
    }

    @Override
    public void move(double speed) {
        sim.setInput(SLMath.clamp(speed, -1, +1) * 12);
    }

    @Override
    public void setMotorStop() {
        sim.setInput(0);
    }

    @Override
    public double getDistance() {
        return distance;
    }

    @Override
    public void resetEncoder() {
        distance = 0;
        
    }

    @Override
    public boolean getTopLimitReached() {
        return getDistance() > Settings.Elevator.MAX_DIST;
    }

    @Override
    public boolean getBottomLimitReached() {
        return getDistance() < 0;
    }

    @Override
    public void simulationPeriodic() {
        sim.update(Settings.DT);

        if (getTopLimitReached() && sim.getVelocityMetersPerSecond() > 0) return;
        if (getBottomLimitReached() && sim.getVelocityMetersPerSecond() < 0) return;

        distance += sim.getVelocityMetersPerSecond() * Settings.DT;
    }

}
