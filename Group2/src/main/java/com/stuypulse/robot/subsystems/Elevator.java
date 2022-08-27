package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.control.feedforward.Feedforward;
import com.stuypulse.stuylib.streams.filters.MotionProfile;

/**
 * Elevator Subsystem (pick up things)
 * 
 * Methods:
 * - move(double speed)
 * - setMotorStop() 
 * - getDistance()
 * - setDistance(double distance)
 * - setTop()
 * - setBottom()
 * - resetEncoder()
 * - getTopLimitReached
 * - getBottomLimitReached
 * 
 * @author indepedence106(Jason Zhou)
 */
public class Elevator extends SubsystemBase {

    private final MotorControllerGroup motors;
    private final Encoder greyhill;

    private final Controller feedforward;

    private final DigitalInput top;
    private final DigitalInput bottom;

    // make smartnumber later for testing purposes
    private double setDistance;





    public Elevator() {
        // fix constants
        motors = new MotorControllerGroup(new CANSparkMax(Ports.Elevator.FIRST_MOTOR, MotorType.kBrushless), 
                                          new CANSparkMax(Ports.Elevator.SECOND_MOTOR, MotorType.kBrushless),
                                          new CANSparkMax(Ports.Elevator.THIRD_MOTOR, MotorType.kBrushless), 
                                          new CANSparkMax(Ports.Elevator.FOURTH_MOTOR, MotorType.kBrushless));
        greyhill = new Encoder(-1, -1);
        // fix kG kS kV kA values
        feedforward = new Feedforward.Elevator(-1, -1, -1, -1).position().add(new PIDController(-1, -1, -1)).setSetpointFilter(new MotionProfile(-1, -1));
        setDistance = 0;

        top = new DigitalInput(-1);
        bottom = new DigitalInput(-1);
    }

    /*** MOTOR CONTROL ***/

    public void move(double speed) {
        motors.set(speed);
    }

    public void setMotorStop() {
        motors.set(0);
    }

    /*** ENCODER CONTROL ***/

    public double getDistance() {
        return greyhill.get();
    }

    public void setDistance(double distance) {
        this.setDistance = distance;
    }

    public void setTop() {
        this.setDistance = -1;
    }

    public void setBottom() {
        this.setDistance = 0;
    }

    public void resetEncoder() {
        greyhill.reset();
    }

    /*** LIMIT SWITCH CONTROL ***/

    public boolean getTopLimitReached() {
        return top.get();
    }

    public boolean getBottomLimitReached() {
        return bottom.get();
    }

    @Override
    public void periodic() {
        motors.set(feedforward.update(setDistance, greyhill.getDistance()));
        
        if (Settings.DEBUG_MODE.get()) {
            SmartDashboard.putBoolean("Debug/Elevator/Top Limit Reached", getTopLimitReached());
            SmartDashboard.putBoolean("Debug/Elevator/Bottom Limit Reached", getBottomLimitReached());
            SmartDashboard.putNumber("Debug/Elevator/Target Distance", setDistance);
            SmartDashboard.putNumber("Debug/Elevator/Target Error", setDistance - getDistance());

            SmartDashboard.putNumber("Debug/Elevator/Distance", getDistance());
        }
    }



}