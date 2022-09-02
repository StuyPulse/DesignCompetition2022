/********************** PROJECT GOOBER ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

import java.nio.file.Path;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.network.SmartAngle;
import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
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
        double DEPLOY_GEARING = 3.0 / 1.0;

        SmartAngle RETRACT_ANGLE = new SmartAngle("Intake/Retract Angle", Angle.fromDegrees(0));
        SmartAngle EXTEND_ANGLE = new SmartAngle("Intake/Extend Angle", Angle.fromDegrees(45));

        SmartNumber ACQUIRE_SPEED = new SmartNumber("Intake/Acquire Speed", 1.0);
        SmartNumber DEACQUIRE_SPEED = new SmartNumber("Intake/Deacquire Speed", -0.5);

        public interface Control {
            double kP = 1;
            double kI = 0.0;
            double kD = 0.1;
        }
    }

    public interface Elevator {
        double GEARING = 1.0 / 9.0;
        
        double MIN_INTAKE_HEIGHT = Units.inchesToMeters(10.219);
        double MAX_INTAKE_HEIGHT = Units.inchesToMeters(85.023);

        double MAX_DIST = MAX_INTAKE_HEIGHT - MIN_INTAKE_HEIGHT;

        double OUTPUT_DIAMETER = Units.inchesToMeters(1.64);

        double PULSES_PER_ROTATE = -1;

        double WEIGHT = Units.lbsToKilograms(32.149);
        
        double DRIVE_SPEED = Units.feetToMeters(1);

        public interface Control {
            double kS = 0;//0.01;
            double kV = 0;//0.1;
            double kA = 0;//0.05;
            double kG = 0;//0;

            double kP = 4.0;
            double kI = 0.0;
            double kD = 0.5;

            double MAX_ACCEL = 0.75;
            double MAX_VEL = 1;
        }
    }
    
    public interface Swivel {
        double WHEEL_DIAMETER = Units.feetToMeters(1);
        double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;

        public interface Drive {

            public interface Feedforward {
                double kS = 0.01;
                double kA = 0.05;
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
                double kP = 2;
                double kI = 0;
                double kD = 0.1;
                double MAX_ACCEL = 10;
                double MAX_VEL = 15;

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

    public interface BuddyClimb {
        double GEARING = 1.0 / 3.0;

        double OUTPUT_DIAMETER = Units.inchesToMeters(1.64);

        double DEBOUNCE_TIME = 1.0;

        double SET_SPEED_THRESHOLD = 0.1;

        double CURRENT_THRESHOLD = -1;

        SmartNumber DEPLOY_SPEED = new SmartNumber("BuddyClimb/Deploy Speed", 0.3);
    }
}
