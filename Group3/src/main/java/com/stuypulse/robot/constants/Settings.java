/************************ PROJECT PHIL ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.control.feedforward.Feedforward;
import com.stuypulse.stuylib.control.feedforward.PositionFeedforwardController;
import com.stuypulse.stuylib.control.feedforward.VelocityFeedforwardController;

import com.stuypulse.stuylib.network.SmartBoolean;
import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.util.Units;

/*-
 * File containing tunable settings for every subsystem on the robot.
 *
 * We use StuyLib's SmartNumber / SmartBoolean in order to have tunable
 * values that we can edit on Shuffleboard.
 */
public interface Settings {

    public interface Elevator {
        double MAX_VELOCITY = 0.0;
        double MAX_ACCELERATION = 0.0;

        double MIN_HEIGHT = Units.inchesToMeters(5);
        double MAX_HEIGHT = Units.inchesToMeters(121);

        double GEAR_RATIO = 0.25;
        double ENCODER_MULTIPLIER = GEAR_RATIO * 360;

        SmartNumber RC = new SmartNumber("Elevator/Filter RC", 0.02);
        SmartNumber DEADBAND = new SmartNumber("Elevator/Deadband", 0.01);

        
        public interface VelocityFeedback {
            SmartNumber P = new SmartNumber("Elevator/VelocityFeedback/P", 0.02);
            SmartNumber I = new SmartNumber("Elevator/VelocityFeedback/I", 0.0);
            SmartNumber D = new SmartNumber("Elevator/VelocityFeedback/D", 0.0);
            
            public static PIDController getController() {
                return new PIDController(P, I, D);
            }
        }

        public interface PositionFeedback {
            SmartNumber P = new SmartNumber("Elevator/VelocityFeedback/P", 0.02);
            SmartNumber I = new SmartNumber("Elevator/VelocityFeedback/I", 0.0);
            SmartNumber D = new SmartNumber("Elevator/VelocityFeedback/D", 0.0);
            
            public static PIDController getController() {
                return new PIDController(P, I, D);
            }
        }

        public interface ElevatorFeedForward{
            SmartNumber G = new SmartNumber("Elevator/ElevatorFeedForward/G", 0.01);
            SmartNumber S = new SmartNumber("Elevator/ElevatorFeedForward/S", 0.02);
            SmartNumber V = new SmartNumber("Elevator/ElevatorFeedForward/V", 0.02);
            SmartNumber A = new SmartNumber("Elevator/ElevatorFeedForward/A", 0.03);
            
            public static VelocityFeedforwardController getController() {
                return new Feedforward.Elevator(G, S, V, A).velocity();
            }
        }
    }
    
    public interface Intake {

        double GEAR_RATIO = 1.0 / 1.0;
        double POSITION_MULTIPLIER = GEAR_RATIO * 360;

        SmartNumber ACQUIRE_SPEED = new SmartNumber("Intake/Acquire Speed", 1.0);
        SmartNumber DEACQUIRE_SPEED = new SmartNumber("Intake/Deacquire Speed", -1.0);

        SmartNumber EXTEND_ANGLE = new SmartNumber("Intake/Extend Angle", 90);
        SmartNumber RETRACT_ANGLE = new SmartNumber("Intake/Retract Angle", 0);

        public interface Deployment {

            public interface FB {
                SmartNumber P = new SmartNumber("Intake/Deployment/P", 0.005);
                SmartNumber I = new SmartNumber("Intake/Deployment/I", 0.0);
                SmartNumber D = new SmartNumber("Intake/Deployment/D", 0.0);

                static PIDController getPIDController() {
                    return new PIDController(P, I, D);
                }
            }

            public interface FF {
                SmartNumber S = new SmartNumber("Intake/Deployment/S", 0.005);
                SmartNumber V = new SmartNumber("Intake/Deployment/V", 0.0);
                SmartNumber A = new SmartNumber("Intake/Deployment/A", 0.0);

                static PositionFeedforwardController getFeedforward() {
                    return new Feedforward.Motor(S, V, A).position();
                }
            }
        }
    }
}
