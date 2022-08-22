package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import static com.stuypulse.robot.constants.Ports.Elevator.*;
import static com.stuypulse.robot.constants.Settings.Elevator.*;

import com.stuypulse.stuylib.control.Controller;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
/** 
 * Fields
 * - leader motor
 * - follower motors
 * - Feedback Controller
 * - encoder
 * - digital inputs
 * - targetState : height from the ground
 * - feedforward
 * - gamepad (IStream)
 * - stalling (BStream)
 * - velocity Feedback Controller
 * - maximum voltage
 * 
 * Methods:
 * - Follower-leader method
 * - calculating voltage for motors 
 * - periodic
 */
public class Elevator extends SubsystemBase {

    // Components
    private final CANSparkMax leader;
    private final CANSparkMax followerOne;
    private final CANSparkMax followerTwo;

    private final RelativeEncoder encoder;

    private final DigitalInput lowerLimit;
    private final DigitalInput upperLimit;

    // Controllers
    private final ElevatorFeedforward elevatorFeedforward;
    private final Controller positionFeedback, velocityFeedback;

    private State targetState;

    public Elevator() {
        leader = new CANSparkMax(LEADER, MotorType.kBrushless);
        followerOne = new CANSparkMax(FIRST_FOLLOWER, MotorType.kBrushless);
        followerTwo = new CANSparkMax(SECOND_FOLLOWER, MotorType.kBrushless);
        
        encoder = leader.getEncoder();
        encoder.setPositionConversionFactor(ENCODER_MULTIPLIER);
        encoder.setVelocityConversionFactor(ENCODER_MULTIPLIER);

        lowerLimit = new DigitalInput(LOWER_SWITCH);
        upperLimit = new DigitalInput(UPPER_SWITCH);

        elevatorFeedforward = Feedforward.getFeedForward();
        positionFeedback = PositionFeedback.getController();
        velocityFeedback = VelocityFeedback.getController();

        targetState = new State(0, 0);
    }

    public double getVelocity() {
        return encoder.getVelocity();
    }

    public double getPosition() {
        return encoder.getPosition();
    }

    public boolean atTop() {
        return !upperLimit.get();
    }
 
    public boolean atBottom() {
        return !lowerLimit.get();
    }

    public void setState(State state) {
        targetState = state;
    }

    public void reset() {
        setState(new State(0, 0));
        encoder.setPosition(0);
    }

    public void set(double voltage) {
        if (atBottom() && voltage < 0) {
            voltage = 0;
            DriverStation.reportWarning("Trying to lower elevator below bottom", false);
        }
        if (atTop() && voltage > 0) {
            voltage = 0;
            DriverStation.reportWarning("Trying to raise elevator above top", false);
        }

        System.out.println("Output: " + voltage);
        
        leader.set(voltage);
        followerOne.set(voltage);
        followerTwo.set(voltage);
    }

    @Override
    public void periodic() {

        double feedforward = elevatorFeedforward.calculate(targetState.velocity);
        double position = positionFeedback.update(targetState.position, getPosition());
        double velocity = velocityFeedback.update(targetState.velocity, getVelocity());

        set(feedforward + position + velocity);

        SmartDashboard.putNumber("Elevator/Position", getPosition());
        SmartDashboard.putNumber("Elevator/Velocity", getVelocity());

        SmartDashboard.putNumber("Elevator/Target Position", targetState.position);
        SmartDashboard.putNumber("Elevator/Target Velocity", targetState.velocity);

        SmartDashboard.putNumber("Elevator/Position Error", targetState.position - getPosition());
        SmartDashboard.putNumber("Elevator/Velocity Error", targetState.velocity - getVelocity());

        SmartDashboard.putBoolean("Elevator/AtBottom", atBottom());
        SmartDashboard.putBoolean("Elevator/AtTop", atTop());
    }
}
