package com.stuypulse.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.stuypulse.robot.constants.Settings;

/**
 * Elevator Subsystem (pick up things)
 * 
 * Methods:
 * - move(double speed)
 * - setMotorStop() 
 * - resetEncoder()
 * - getTopLimitReached()
 * - getBottomLimitReached()
 * 
 * @author indepedence106(Jason Zhou)
 */
public abstract class Elevator extends SubsystemBase {

    // private final Intake intake;
    private final Mechanism2d mech;
    private final MechanismLigament2d elevatorMech;
    // private final MechanismLigament2d intakeMech;

    public Elevator() {
        mech = new Mechanism2d(2, 6);
        
        MechanismRoot2d root = mech.getRoot("elevator", 1, 0);
        
        elevatorMech = root.append(
            new MechanismLigament2d("elevator", 1, 90.0));

        // intakeMech = root.append(
        //     new MechanismLigament2d(
        //         "intake", 1, 90));

        SmartDashboard.putData("Mech2d", mech);
    }

    /*** MOTOR CONTROL ***/

    public abstract void move(double speed);

    public abstract void setMotorStop();

    /*** ENCODER CONTROL ***/

    public abstract double getDistance();

    public abstract void resetEncoder();

    /*** LIMIT SWITCH CONTROL ***/

    public abstract boolean getTopLimitReached();

    public abstract boolean getBottomLimitReached();

    @Override
    public void periodic() {
        elevatorMech.setLength(getDistance() * (5 / Settings.Elevator.TOP_HEIGHT) + 0.5);
        // intakeMech.setAngle(intake.getAngle().getDegrees());
        
        SmartDashboard.putBoolean("Elevator/Top Limit Reached", getTopLimitReached());
        SmartDashboard.putBoolean("Elevator/Bottom Limit Reached", getBottomLimitReached());

        SmartDashboard.putNumber("Elevator/Distance", getDistance());
    }

}