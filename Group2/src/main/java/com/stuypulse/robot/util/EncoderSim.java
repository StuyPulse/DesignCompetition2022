package com.stuypulse.robot.util;

public class EncoderSim {
    
    private double position;

    private double positionConversion;

    private boolean inverted;

    public EncoderSim() {
        this.position = 0;
    }

    protected void update(double dt, double vel) {
        position += dt * vel;
    }

    public double getDistance() {
        if (inverted)
            return -position * positionConversion;
        else
            return position * positionConversion;
    }

    public double getPositionConversion() {
        return positionConversion;
    }

    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    // distance is by default in radians
    public void setPositionConversion(double conversion) {
        this.positionConversion = conversion;
    }

}
