/********************** PROJECT GOOBER ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

import java.nio.file.Path;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.control.feedforward.Feedforward;
import com.stuypulse.stuylib.control.angle.feedback.AnglePIDController;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.network.SmartAngle;
import com.stuypulse.stuylib.network.SmartBoolean;
import com.stuypulse.stuylib.network.SmartNumber;
import com.stuypulse.stuylib.network.SmartString;
import com.stuypulse.stuylib.streams.filters.IFilter;
import com.stuypulse.stuylib.streams.filters.MotionProfile;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;
import com.stuypulse.stuylib.streams.vectors.filters.VDeadZone;
import com.stuypulse.stuylib.streams.vectors.filters.VFilter;
import com.stuypulse.stuylib.streams.vectors.filters.VLowPassFilter;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Filesystem;

/*-
 * File containing tunable settings for every subsystem on the robot.
 *
 * We use StuyLib's SmartNumber / SmartBoolean in order to have tunable
 * values that we can edit on Shuffleboard.
 */
public interface Settings {
    Path DEPLOY_DIRECTORY = Filesystem.getDeployDirectory().toPath();

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

    public interface Elevator {
        double GEARING = 1.0 / 9.0;
        
        double TOP_HEIGHT = Units.feetToMeters(6.2);

        double OUTPUT_DIAMETER = Units.inchesToMeters(1.64);

        double PULSES_PER_ROTATE = -1;

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
    
    public interface Swivel {
        double WHEEL_DIAMETER = Units.feetToMeters(1);
        double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;

        public interface Drive {

            public interface Feedforward {
                double kS = 0.1;
                double kA = 0.1;
                double kV = 0.1;
            }
    
            public interface Feedback {
                double kP = 1;
                double kI = 0;
                double kD = 0.2;
            }
        }

        public interface Turn {
            public interface Feedback {
                double kP = 6;
                double kI = 0;
                double kD = 1;
            }
        }

        public interface Filtering {
            public interface Drive {
                SmartNumber DEADZONE = new SmartNumber("Filtering/Drive/Deadzone", 0.05);
                SmartNumber RC = new SmartNumber("Filtering/Drive/RC", 0.2);
                SmartNumber MAX_SPEED = new SmartNumber("Filtering/Drive/Max Speed", 10);
            }

            public interface Turn {
                SmartNumber DEADZONE = new SmartNumber("Filtering/Turn/Deadzone", 0.05);
                SmartNumber RC = new SmartNumber("Filtering/Turn/RC", 0.2);
                SmartNumber MAX_TURN = new SmartNumber("Filtering/Turn/Max Turn", 10);
            }
        }

        public interface Motion {
            public interface X {
                double kP = 4;
                double kI = 0;
                double kD = 1;

                public static PIDController getController() {
                    return new PIDController(kP, kI, kD);
                }
            }

            public interface Y {
                double kP = 4;
                double kI = 0;
                double kD = 1;

                public static PIDController getController() {
                    return new PIDController(kP, kI, kD);
                }
            }
            
            public interface Theta {
                double kP = 1.5;
                double kI = 0;
                double kD = 0.1;
                double MAX_ACCEL = 10;
                double MAX_VEL = 10;

                public static ProfiledPIDController getController() {
                    ProfiledPIDController a = new ProfiledPIDController(
                        kP, kI, kD,
                        new Constraints(MAX_VEL, MAX_ACCEL));
                    a.enableContinuousInput(-Math.PI, Math.PI);
                    return a;
                }
            }
        }
    }
}
