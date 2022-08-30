package com.stuypulse.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Fields:
 * Module ID (final)
 * Module Offset (final)
 * Control (final)
 * Module Target State
 * 
 * Methods:
 * Get Module ID
 * Get Module Offset
 * Get Current Speed
 * Get Current Angle
 * Get Current Module State
 * Set Module Target State
 */
public class SwerveModule extends SubsystemBase {

    private final String id;
    private final Translation2d moduleOffset;
    private final ModuleControl moduleControl;

    private SwerveModuleState targetState;
    
    public SwerveModule(String id, Translation2d moduleOffset, ModuleControl moduleControl) {
        this.id = id;
        this.moduleOffset = moduleOffset;
        this.moduleControl = moduleControl;

        this.targetState = new SwerveModuleState();
    }

    /** CONFIG */

    public String getID() {
        return this.id;
    }

    public Translation2d getOffset() {
        return this.moduleOffset;
    }

    /** SENSORS */

    public double getVelocity() {
        return moduleControl.getVelocity();
    }

    public Rotation2d getAngle() {
        return moduleControl.getAngle();
    }

    /** STATES */

    public SwerveModuleState getState() {
        return new SwerveModuleState(getVelocity(), getAngle());
    }

    public void setTargetState(double velocity, Rotation2d angle) {
        targetState = SwerveModuleState.optimize(
            new SwerveModuleState(velocity, angle),
            getAngle()
        );
    }

    public void setTargetState(SwerveModuleState state) {
        targetState = SwerveModuleState.optimize(state, getAngle());
    }

    /** RESET */

    public void stop() {
        setTargetState(0.0, getAngle());
    }

    public void reset() {
        setTargetState(0.0, new Rotation2d(0.0));
        moduleControl.stop();
    }

    @Override
    public void periodic() {
        // control
        moduleControl.setTargetVelocity(targetState.speedMetersPerSecond);
        moduleControl.setTargetAngle(targetState.angle);

        // logging
        SmartDashboard.putNumber("Module " + id + "/Velocity", getVelocity());
        SmartDashboard.putNumber("Module " + id + "/Angle", getAngle().getDegrees());
        
        SmartDashboard.putNumber("Module " + id + "/Target Velocity", targetState.speedMetersPerSecond);
        SmartDashboard.putNumber("Module " + id + "/Target Angle", targetState.angle.getDegrees());
    }
}
