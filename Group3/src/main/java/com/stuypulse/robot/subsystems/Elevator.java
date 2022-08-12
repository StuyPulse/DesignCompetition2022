package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.constants.Ports.Gamepad;
import static com.stuypulse.robot.constants.Ports.Elevator.*;
import static com.stuypulse.robot.constants.Settings.Elevator.*;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.PIDController;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.booleans.BStream;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import edu.wpi.first.wpilibj.DigitalInput;
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

    private CANSparkMax leader;
    private CANSparkMax firstFollower;
    private CANSparkMax secondFollower;

    private RelativeEncoder encoder;
    
    private DigitalInput upperSwitch;
    private DigitalInput lowerSwitch;
    
    private Controller feedback, velocityFeedback;
    private ElevatorFeedforward feedforward;
    private State targetState;
    
    private IStream controls;
    private BStream stalling;

    public Elevator(Gamepad operator) {
        leader = new CANSparkMax(LEADER, MotorType.kBrushless);
        firstFollower = new CANSparkMax(FIRSTFOLLOWER, MotorType.kBrushless);
        secondFollower = new CANSparkMax(SECONDFOLLOWER, MotorType.kBrushless);
        
        encoder = leader.getEncoder();
        encoder.setPositionConversionFactor(POSITION_MULTIPLIER);

        upperSwitch = new DigitalInput(UPPERSWITCH);
        lowerSwitch = new DigitalInput(LOWERSWITCH);

        feedback = new PIDController(0.005, 0.0, 0.0);
        velocityFeedback = new PIDController(0.005, 0.0, 0.0);
        feedforward = new ElevatorFeedforward(0.0, 0.0, 0.0, 0.0);
        targetState = new State(0.0, 0.0);
    }

    public void internship() {
        leader.setVoltage(calculate());
        firstFollower.setVoltage(calculate());
        secondFollower.setVoltage(calculate());
    }
    
    public double calculate() { 
        return MAX_VOLTAGE.get() * controls.get();
    }

    public boolean atTop() {
        return !upperSwitch.get();
    }

    public boolean atBottom() {
        return !lowerSwitch.get();
    }
    
    @Override
    public void periodic() {
        internship();

        // logging
    }
}
