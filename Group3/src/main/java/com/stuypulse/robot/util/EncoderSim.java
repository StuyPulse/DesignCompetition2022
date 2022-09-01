package com.stuypulse.robot.util;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * Wrapper for a simulated motor that provides an encoder API.
 * Constructed through a MotorSim.
 * 
 * Default distance units are in radians.
 * 
 * @author Ben Goldfisher
 */
public class EncoderSim implements Sendable {
    private double position;
    private double velocity;

    private double positionConversion;
    private double velocityConversion;

    private boolean inverted;

    protected EncoderSim() {
        position = 0;
        velocity = 0;
        positionConversion = 1;
        velocityConversion = 1;
    }

    protected void update(double dtSeconds, double vel) {
        velocity = vel;
        position += dtSeconds * vel;
    }

    public double getDistance() {
        if (inverted)
            return -position * positionConversion;
        else
            return position * positionConversion;
    }

    public double getVelocity() {
        if (inverted) {
            return -velocity * velocityConversion;
        } else {
            return velocity * velocityConversion;
        }
    }

    public double getPositionConversion() {
        return positionConversion;
    }

    public double getVelocityConversion() {
        return velocityConversion;
    }

    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    // distance is by default in radians
    public void setPositionConversion(double conversion) {
        this.positionConversion = conversion;
    }

    public void setVelocityConversion(double conversion) {
        this.velocityConversion = conversion;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Encoder");

        builder.addDoubleProperty("Distance", this::getDistance, null);
        builder.addDoubleProperty("Position Conversion", this::getPositionConversion, this::setPositionConversion);
        builder.addDoubleProperty("Velocity Conversion", this::getVelocityConversion, this::setVelocityConversion);
    }

}
