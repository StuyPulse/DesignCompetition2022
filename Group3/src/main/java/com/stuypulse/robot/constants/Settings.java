/************************ PROJECT PHIL ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.PIDController;
import com.stuypulse.stuylib.network.SmartBoolean;
import com.stuypulse.stuylib.network.SmartNumber;

/*-
 * File containing tunable settings for every subsystem on the robot.
 *
 * We use StuyLib's SmartNumber / SmartBoolean in order to have tunable
 * values that we can edit on Shuffleboard.
 */
public interface Settings {

    public interface Intake {

        double GEAR_RATIO = 1.0 / 1.0;
        double POSITION_MULTIPLIER = GEAR_RATIO * 360;

        SmartNumber ACQUIRE_SPEED = new SmartNumber("Intake/Acquire Speed", 1.0);
        SmartNumber DEACQUIRE_SPEED = new SmartNumber("Intake/Deacquire Speed", -1.0);

        SmartNumber EXTEND_ANGLE = new SmartNumber("Intake/Extend Angle", 90);
        SmartNumber RETRACT_ANGLE = new SmartNumber("Intake/Retract Angle", 0);

        public interface Deployment {

            SmartNumber MAX_ERROR = new SmartNumber("Intake/Deployment/Max Error", 2);

            SmartNumber P = new SmartNumber("Intake/Deployment/P", 0.005);
            SmartNumber I = new SmartNumber("Intake/Deployment/I", 0.0);
            SmartNumber D = new SmartNumber("Intake/Deployment/D", 0.0);

            static Controller getController() {
                return new PIDController(P, I, D);
            }
        }

    }

}
