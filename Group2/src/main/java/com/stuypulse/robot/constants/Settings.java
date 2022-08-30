/********************** PROJECT GOOBER ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.control.feedforward.Feedforward;
import com.stuypulse.stuylib.network.SmartBoolean;
import com.stuypulse.stuylib.network.SmartNumber;
import com.stuypulse.stuylib.streams.filters.MotionProfile;

import edu.wpi.first.math.util.Units;

/*-
 * File containing tunable settings for every subsystem on the robot.
 *
 * We use StuyLib's SmartNumber / SmartBoolean in order to have tunable
 * values that we can edit on Shuffleboard.
 */
public interface Settings {
    double DT = 0.02;

    public interface Elevator {
        double GEARING = 1.0 / 9.0;
        
        double TOP_HEIGHT = Units.feetToMeters(6.2);

        double OUTPUT_DIAMETER = Units.inchesToMeters(1.64);

        public interface Control {
            double kS = 0.01;
            double kV = 0.5;
            double kA = 0.1;
            double kG = 0.5;

            double kP = 1.0;
            double kI = 0.0;
            double kD = 0.1;

            double MAX_ACCEL = 1;
            double MAX_VEL = 2;

            public static Controller getControl() {
                return new Feedforward.Elevator(kG, kS, kV, kA).position()
                    .add(new PIDController(kP, kI, kD))
                    .setSetpointFilter(new MotionProfile(MAX_VEL, MAX_ACCEL));
            }
        }
    }
}
