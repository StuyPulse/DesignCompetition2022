/********************** PROJECT GOOBER ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.control.angle.feedback.AnglePIDController;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.network.SmartAngle;
import com.stuypulse.stuylib.network.SmartBoolean;
import com.stuypulse.stuylib.network.SmartNumber;

/*-
 * File containing tunable settings for every subsystem on the robot.
 *
 * We use StuyLib's SmartNumber / SmartBoolean in order to have tunable
 * values that we can edit on Shuffleboard.
 */
public interface Settings {
    double DT = 0.02;

    public interface Intake {
        SmartAngle RETRACT_ANGLE = new SmartAngle("Intake/Retract Angle", Angle.fromDegrees(0));
        SmartAngle EXTEND_ANGLE = new SmartAngle("Intake/Extend Angle", Angle.fromDegrees(45));

        SmartNumber ACQUIRE_SPEED = new SmartNumber("Intake/Acquire Speed", 1.0);
        SmartNumber DEACQUIRE_SPEED = new SmartNumber("Intake/Deacquire Speed", -0.5);

        public interface Control {
            double kP = 1;
            double kI = 0.0;
            double kD = 0.1;

            public static AngleController getControl() {
                return new AnglePIDController(kP, kI, kD);
            }
        }
    }
}
