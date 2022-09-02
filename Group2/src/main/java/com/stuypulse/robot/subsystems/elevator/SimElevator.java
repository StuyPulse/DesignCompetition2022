package com.stuypulse.robot.subsystems.elevator;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.Elevator;
import com.stuypulse.robot.util.EncoderSim;
import com.stuypulse.robot.util.MotorSim;
import com.stuypulse.robot.util.MotorSim.MotorType;

public class SimElevator extends Elevator {

    private final MotorSim motors;
    private final EncoderSim encoder;

    public SimElevator() {
        motors = new MotorSim(MotorType.NEO550, 2, Settings.Elevator.GEARING);
        
        encoder = motors.getEncoder();
        encoder.setPositionConversion(Math.PI * Settings.Elevator.OUTPUT_DIAMETER);
    }

    @Override
    public void move(double speed) {
        // speed -= Control.kG;

        // if elevator should not physically be able to move
        if (getBottomLimitReached() && speed < 0) {
            motors.stopMotor();
        } else if (getTopLimitReached() && speed > 0) {
            motors.stopMotor();
        } else {
            motors.set(speed);
        }
    }

    @Override
    public void setMotorStop() {
        motors.stopMotor();
    }

    @Override
    public double getDistance() {
        return encoder.getDistance();
    }

    @Override
    public void resetEncoder() {
        encoder.reset();
        
    }

    @Override
    public boolean getTopLimitReached() {
        return getDistance() >= Settings.Elevator.TOP_HEIGHT;
    }

    @Override
    public boolean getBottomLimitReached() {
        return getDistance() <= 0;
    }

    @Override
    public void simulationPeriodic() {
        motors.update(Settings.DT);
    }

}