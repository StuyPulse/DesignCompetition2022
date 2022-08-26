/************************ PROJECT PHIL ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.network.SmartAngle;
import com.stuypulse.stuylib.network.SmartNumber;

/*-
 * File containing tunable settings for every subsystem on the robot.
 *
 * We use StuyLib's SmartNumber / SmartBoolean in order to have tunable
 * values that we can edit on Shuffleboard.
 */
public interface Settings {
    public interface Drivetrain {
        double TRACK_WIDTH = 0.5;
        
        interface PID {
            SmartNumber kP = new SmartNumber("Drivetrain/kP", 0);
            SmartNumber kI = new SmartNumber("Drivetrain/kI", 0);
            SmartNumber kD = new SmartNumber("Drivetrain/kD", 0);
        }
        interface FF {
            SmartNumber kS = new SmartNumber("Drivetrain/kS", 0);
            SmartNumber kV = new SmartNumber("Drivetrain/kV", 0);
            SmartNumber kA = new SmartNumber("Drivetrain/kA", 0);
        }
    }

    public interface Intake {
        SmartAngle RETRACT_ANGLE = new SmartAngle("Intake/Retract Angle", Angle.fromDegrees(0));
        SmartAngle EXTEND_ANGLE = new SmartAngle("Intake/Extend Angle", Angle.fromDegrees(90));
        
        interface PID {
            SmartNumber kP = new SmartNumber("Intake/kP", 0);
            SmartNumber kI = new SmartNumber("Intake/kI", 0);
            SmartNumber kD = new SmartNumber("Intake/kD", 0);
        }

        interface FF {
            SmartNumber kS = new SmartNumber("Intake/kS", 0);
            SmartNumber kV = new SmartNumber("Intake/kV", 0);
            SmartNumber kA = new SmartNumber("Intake/kA", 0);
        }
    }
}