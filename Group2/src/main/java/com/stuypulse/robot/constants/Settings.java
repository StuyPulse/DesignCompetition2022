/********************** PROJECT GOOBER ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.control.PIDController;
import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.network.SmartBoolean;
import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.util.Units;

/*-
 * File containing tunable settings for every subsystem on the robot.
 *
 * We use StuyLib's SmartNumber / SmartBoolean in order to have tunable
 * values that we can edit on Shuffleboard.
 */
public interface Settings {
    double DT = 0.02;

    public interface Swivel {
        double WHEEL_DIAMETER = Units.feetToMeters(1);
        double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;

        public interface Drive {
            double MAX_SPEED = 10;

            public interface Feedforward {
                double kS = 0.1;
                double kA = 0.1;
                double kV = 0.1;
    
                public static SimpleMotorFeedforward getFeedforward() {
                    return new SimpleMotorFeedforward(kS, kV, kA);
                }
            }
    
            public interface Feedback {
                double kP = 1;
                double kI = 0;
                double kD = 0.2;
    
                public static PIDController getFeedback() {
                    return new PIDController(kP, kI, kD);
                }
            }
        }

        public interface Turn {
            public interface Feedback {
                double kP = 1;
                double kI = 0;
                double kD = 0.2;
    
                public static AngleController getFeedback() {
                    return new AngleController(new PIDController(kP, kI, kD));
                }
            }
        }
    }
}
