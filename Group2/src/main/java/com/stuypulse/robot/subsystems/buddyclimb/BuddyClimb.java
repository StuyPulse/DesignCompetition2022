package com.stuypulse.robot.subsystems.buddyclimb;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C.Port;

public class BuddyClimb {
    private final Encoder greyhill;

    private final CANSparkMax motor;

    private final Debouncer stalling;

    public BuddyClimb() {
        greyhill = new Encoder(Ports.BuddyClimb.GREYHILL_A, Ports.BuddyClimb.GREYHILL_B);
        greyhill.setDistancePerPulse(Math.PI * Settings.BuddyClimb.OUTPUT_DIAMETER / Settings.BuddyClimb.GEARING);

        stalling = new Debouncer(Settings.BuddyClimb.DEBOUNCE_TIME, DebounceType.kBoth);

        motor = new CANSparkMax(Ports.BuddyClimb.MOTOR, MotorType.kBrushless);
    }

    /*** MOTOR CONTROL ***/
    
    public void deploy() {
        // 1.5 rotations per full deployment
        if (isStalling()) {
            DriverStation.reportError(
                    "[CRITICAL] Climber is stalling when attempting to move!", false);
            stalling.calculate(true);
            motor.set(0);
        } else if (greyhill.getDistance() > +1.5 * Settings.BuddyClimb.OUTPUT_DIAMETER * Math.PI) motor.set(Settings.BuddyClimb.DEPLOY_SPEED.get());
    
    }

    public void retract() {
        if (isStalling()) {
            DriverStation.reportError(
                    "[CRITICAL] Climber is stalling when attempting to move!", false);
            stalling.calculate(true);
            motor.set(0);
        } if (greyhill.getDistance() <= 0) motor.set(-Settings.BuddyClimb.DEPLOY_SPEED.get());
    }

    /*** STALLING PROTECTION ***/

    private double getSetSpeed() {
        return motor.get();
    }

    private double getCurrentAmps() {
        return Math.abs(motor.getOutputCurrent());
    }

    public boolean isStalling() {
        boolean current = getCurrentAmps() > Settings.BuddyClimb.CURRENT_THRESHOLD;
        boolean output = Math.abs(getSetSpeed()) > Settings.BuddyClimb.SET_SPEED_THRESHOLD;
        return stalling.calculate(output && current);
    }
}
