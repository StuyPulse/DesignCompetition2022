package com.stuypulse.robot.subsystems.elevator;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.Elevator;
import com.stuypulse.stuylib.math.SLMath;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SimElevator extends Elevator {

    // private final LinearSystemSim<N2, N1, N1> sim;
    private final ElevatorSim sim;
    private double distance;

    public SimElevator() {
        sim = new ElevatorSim(
            DCMotor.getNEO(2),
            Settings.Elevator.GEARING,
            Settings.Elevator.WEIGHT,
            Settings.Elevator.OUTPUT_DIAMETER / 2.0,
            Settings.Elevator.MIN_INTAKE_HEIGHT,
            Settings.Elevator.MAX_INTAKE_HEIGHT);
        /*sim = new LinearSystemSim<>(
            LinearSystemId.createElevatorSystem(
                DCMotor.getNEO(2),
                Settings.Elevator.WEIGHT,
                Settings.Elevator.OUTPUT_DIAMETER / 2.0,
                Settings.Elevator.GEARING));*/
    }

    @Override
    public void move(double speed) {
        speed = SLMath.clamp(speed, -1, +1);
        SmartDashboard.putNumber("Elevator/in", speed);
        sim.setInput(speed);
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
        SmartDashboard.putNumber("Elevator/speed", sim.getOutput(0));
        distance += sim.getOutput(0) * Settings.DT;
    }

}
