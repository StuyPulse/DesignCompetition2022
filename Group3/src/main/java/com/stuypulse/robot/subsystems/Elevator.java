package com.stuypulse.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.feedforward.VelocityFeedforwardController;
import com.stuypulse.stuylib.streams.booleans.BStream;
import com.stuypulse.stuylib.streams.booleans.filters.BDebounceRC;

import static com.stuypulse.robot.constants.Motors.Elevator.*;
import static com.stuypulse.robot.constants.Ports.Elevator.*;
import static com.stuypulse.robot.constants.Settings.Elevator.*;

import edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Fields: 
 * - 2 Talons (leader follower)
 * - 2 Limit switches : top and bottom limit
 * - TargetState (State)
 * - Position & Velocity feedback
 * - Elevator feedforward
 * - Stalling
 * 
 * Methods:
 * - get limit switches
 * - get position
 * - get velocity
 * - get is stalling
 * - set target state
 * - run motors
 * - stop
 * - reset
 */
public class Elevator extends SubsystemBase {

    private final TalonFX leader, follower; 
    private final DigitalInput upperSwitch, lowerSwitch;

    private final Controller velocityFeedback, positionFeedback;
    private final VelocityFeedforwardController feedforward;
    
    private final BStream stalling; 
    private State targetState;

    public Elevator() {
        leader = new TalonFX(LEFT_MOTOR);
        follower = new TalonFX(RIGHT_MOTOR);

        follower.follow(leader);

        LEADER_CONFIG.configure(leader);
        FOLLOWER_CONFIG.configure(follower);

        upperSwitch = new DigitalInput(UPPER_SWITCH);
        lowerSwitch = new DigitalInput(LOWER_SWITCH);

        velocityFeedback = VelocityFeedback.getController();
        positionFeedback = PositionFeedback.getController();
        feedforward = ElevatorFeedForward.getController();
        
        stalling = BStream.create(() -> isStalling()).filtered(new BDebounceRC.Falling(0.2));
        targetState = new State(0, 0);
    }

    public boolean atTop() {
        return !upperSwitch.get();
    }
    
    public boolean atBottom() {
        return !lowerSwitch.get();
    }

    public double getVelocity() {
        return (leader.getSelectedSensorVelocity() + follower.getSelectedSensorVelocity()) / 2;
    }

    public double getHeight() {
        return (leader.getSelectedSensorPosition() + follower.getSelectedSensorPosition()) / 2;
    }

    public double getAmps() {
        return (leader.getSupplyCurrent() + follower.getSupplyCurrent()) / 2;
    }

    public boolean isStalling() {
        if(getAmps() > 40 && getVelocity() < 0.02){
            return true;
        }
        else{
            return false;
        }
    }

    public void setState(State state) {
        this.targetState = state;
    }

    public void stop() {
        leader.set(TalonFXControlMode.PercentOutput, 0);
    }

    public void reset() {
        leader.setSelectedSensorPosition(0);
        follower.setSelectedSensorPosition(0);
    }

    public void run(double velocity) {
        if (atTop() && velocity > 0 || atBottom() && velocity < 0 || stalling.get()) {
            stop();
        }
        if (atBottom()) {
            reset();
        } else {
            leader.set(TalonFXControlMode.Velocity, velocity);
        }
    }
        
    @Override
    public void periodic() {
        
        double velocity = velocityFeedback.update(targetState.velocity, getVelocity());
        double position = positionFeedback.update(targetState.position, getHeight());
        double ff = feedforward.update(targetState.velocity, getVelocity());

        run(velocity + position + ff);

        // logging
        SmartDashboard.putNumber("Elevator/Velocity", getVelocity());
        SmartDashboard.putNumber("Elevator/Height", getHeight());
        SmartDashboard.putNumber("Elevator/Current Amps", getAmps());

        SmartDashboard.putNumber("Elevator/Target Velocity", targetState.velocity);
        SmartDashboard.putNumber("Elevator/Target Height", targetState.position);

        SmartDashboard.putBoolean("Elevator/At Top", atTop());
        SmartDashboard.putBoolean("Elevator/At Bottom", atBottom());

        SmartDashboard.putBoolean("Elevator/Is Stalling", stalling.get());
    }
}
