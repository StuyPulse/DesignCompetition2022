package com.stuypulse.robot.subsystems;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.constants.Settings.Intake.Control;
import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.math.Angle;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Intake/grab cubes
 *
 * Contains:
 *   - 2 drive motors (NEO)
 *   - 1 deploy motor (NEO)
 *   - 1 relative encoder
 * 
 * @author Maximillian Zeng
 * @author Yuchen Pan
 * @author Zixi Feng
 * @author Tdog Lin
 * @author Genjamin Boldfisher
 * @author Maximillian Zeng
*/

public abstract class Intake extends SubsystemBase {
    
    private AngleController controller;
    private Angle targetAngle;

    private double speed;

    public Intake() {
        controller = Control.getControl();

        targetAngle = Settings.Intake.RETRACT_ANGLE.get();
    }


    /*** Drive Motors ***/

    public abstract void set(double speed);

    public void acquire() {
        set(Settings.Intake.ACQUIRE_SPEED.get());
    }

    public void deacquire() {
        set(Settings.Intake.DEACQUIRE_SPEED.get());
    }
    
    public abstract void stop();

    /*** Deploy Motor ***/

    public abstract void setDeploy(double speed);

    public abstract Angle getAngle();

    private void setAngle(Angle angle) {
        targetAngle = angle;
    }

    public void retract() {
        setAngle(Settings.Intake.RETRACT_ANGLE.get());
    }

    public void extend() {
        setAngle(Settings.Intake.EXTEND_ANGLE.get());
    }
    

    @Override
    public void periodic() {
        setDeploy(controller.update(targetAngle, getAngle()));

        SmartDashboard.putNumber("Intake/Angle", getAngle().toDegrees());
        SmartDashboard.putNumber("Intake/Speed", speed);
    }

}
