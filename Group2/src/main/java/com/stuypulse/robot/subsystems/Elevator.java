package com.stuypulse.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

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
public abstract class Elevator extends SubsystemBase {

    private final Controller feedforward;

    private double setDistance;

    public Elevator() {
        // fix kG kS kV kA values
        feedforward = new Feedforward.Elevator(-1, -1, -1, -1).position()
            .add(new PIDController(-1, -1, -1)).setSetpointFilter(new MotionProfile(-1, -1));
        
        setDistance = 0;
    }

    /*** MOTOR CONTROL ***/

    public abstract void move(double speed);

    public abstract void setMotorStop();

    /*** ENCODER CONTROL ***/

    public abstract double getDistance();

    public void setDistance(double distance) {
        this.setDistance = distance;
    }

    public void setTop() {
        this.setDistance = Settings.Elevator.TOP_HEIGHT;
    }

    public void setBottom() {
        this.setDistance = 0;
    }

    public abstract void resetEncoder();

    /*** LIMIT SWITCH CONTROL ***/

    public abstract boolean getTopLimitReached();

    public abstract boolean getBottomLimitReached();

    @Override
    public void periodic() {
        move(feedforward.update(setDistance, getDistance()));
        
        SmartDashboard.putBoolean("Elevator/Top Limit Reached", getTopLimitReached());
        SmartDashboard.putBoolean("Elevator/Bottom Limit Reached", getBottomLimitReached());
        SmartDashboard.putNumber("Elevator/Target Distance", setDistance);
        SmartDashboard.putNumber("Elevator/Target Error", setDistance - getDistance());

        SmartDashboard.putNumber("Elevator/Distance", getDistance());
    }



}