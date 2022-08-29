/************************ PROJECT PHIL ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.PIDController;
import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.network.SmartBoolean;
import com.stuypulse.stuylib.network.SmartNumber;
import com.stuypulse.stuylib.streams.filters.IFilter;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;
import com.stuypulse.stuylib.streams.vectors.filters.VDeadZone;
import com.stuypulse.stuylib.streams.vectors.filters.VFilter;
import com.stuypulse.stuylib.streams.vectors.filters.VLowPassFilter;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;

/*-
 * File containing tunable settings for every subsystem on the robot.
 *
 * We use StuyLib's SmartNumber / SmartBoolean in order to have tunable
 * values that we can edit on Shuffleboard.
 */
public interface Settings {

    public interface Swerve {

        public interface Drive {

            double RC = 0.02;
            double DEADBAND = 0.05;

            public interface Encoder {

            }

            public interface Feedback {
                SmartNumber P = new SmartNumber("Swerve/Drive/P", 0.05);
                SmartNumber I = new SmartNumber("Swerve/Drive/I", 0.0);
                SmartNumber D = new SmartNumber("Swerve/Drive/D", 0.0);

                public static Controller getFeedbackController() {
                    return new PIDController(P, I, D);
                }
            }

            public interface Feedforward {
                double V = 0.001;
                double A = 0.001;
                double S = 0.001;

                public static SimpleMotorFeedforward getFeedforwardController() {
                    return new SimpleMotorFeedforward(S, V, A);
                }
            }

            public static VFilter getFilter() {
                return new VDeadZone(DEADBAND)
                        .then(new VLowPassFilter(RC));
            }
        }

        public interface Turn {

            double RC = 0.02;
            double DEADBAND = 0.05;
            public interface Encoder {
                double MIN_INPUT = 0.0;
                double MAX_INPUT = 1.0;

                double MIN_OUTPUT = -Math.PI;
                double MAX_OUTPUT = +Math.PI;

                static double getRadians(double input) {
                    return SLMath.map(input, MIN_INPUT, MAX_INPUT, MIN_OUTPUT, MAX_OUTPUT);
                }
            }

            public interface Feedback {
                SmartNumber P = new SmartNumber("Swerve/Turn/P", 0.05);
                SmartNumber I = new SmartNumber("Swerve/Turn/I", 0.0);
                SmartNumber D = new SmartNumber("Swerve/Turn/D", 0.0);

                public static AngleController getFeedbackController() {
                    return new PIDController(P, I, D).angle().useRadians();
                }
            }

            public static IFilter getFilter() {
                return IFilter.create(x -> SLMath.deadband(x, DEADBAND))
                        .then(new LowPassFilter(RC));
            }
        }
        
        public interface Motion {

            public interface X {
                double kP = 0.0;
                double kI = 0.0;
                double kD = 0.0;
    
                public static PIDController getController() {
                    return new PIDController(kP, kI, kD);
                }
            }
    
            public interface Y {
                double kP = 0.0;
                double kI = 0.0;
                double kD = 0.0;
    
                public static PIDController getController() {
                    return new PIDController(kP, kI, kD);
                }
            }
    
            public interface Theta {
                double kP = 0.0;
                double kI = 0.0;
                double kD = 0.0;
    
                public static ProfiledPIDController getController() {
                    return new ProfiledPIDController(
                            kP, kI, kD,
                            new Constraints(Modules.MAX_ANGULAR_SPEED, Modules.MAX_ANGULAR_ACCEL));
                }
            }
        }
    }
}
