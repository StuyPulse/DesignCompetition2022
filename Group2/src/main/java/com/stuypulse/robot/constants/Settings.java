/********************** PROJECT GOOBER ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.control.angle.feedback.AnglePIDController;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.network.SmartBoolean;
import com.stuypulse.stuylib.network.SmartNumber;
import com.stuypulse.stuylib.streams.filters.IFilter;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;
import com.stuypulse.stuylib.streams.vectors.filters.VDeadZone;
import com.stuypulse.stuylib.streams.vectors.filters.VFilter;
import com.stuypulse.stuylib.streams.vectors.filters.VLowPassFilter;

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
                double kP = 8;
                double kI = 0;
                double kD = 0.2;
    
                public static AngleController getFeedback() {
                    return new AnglePIDController(kP, kI, kD);
                }
            }
        }

        public interface Filtering {
            public interface Drive {
                SmartNumber DEADZONE = new SmartNumber("Filtering/Drive/Deadzone", 0.05);
                SmartNumber RC = new SmartNumber("Filtering/Drive/RC", 0.2);
                SmartNumber MAX_SPEED = new SmartNumber("Filtering/Drive/Max Speed", 10);

                public static VFilter getFilter() {
                    return new VDeadZone(DEADZONE)
                        .then(new VLowPassFilter(RC))
                        .then(x -> x.mul(MAX_SPEED.get()));
                }
            }

            public interface Turn {
                SmartNumber DEADZONE = new SmartNumber("Filtering/Turn/Deadzone", 0.05);
                SmartNumber RC = new SmartNumber("Filtering/Turn/RC", 0.2);
                SmartNumber MAX_TURN = new SmartNumber("Filtering/Turn/Max Turn", 10);
                
                public static IFilter getFilter() {
                    return IFilter.create(x -> SLMath.deadband(x, DEADZONE.get()))
                        .then(new LowPassFilter(RC))
                        .then(x -> x * MAX_TURN.get());
                }
            }
        }
    }
}