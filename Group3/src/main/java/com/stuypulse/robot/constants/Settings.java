/************************ PROJECT PHIL ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.control.PIDController;
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

        public interface PositionFeedback {
            SmartNumber P = new SmartNumber("Elevator/Position/P", 0.05);
            SmartNumber I = new SmartNumber("Elevator/Position/I", 0.0);
            SmartNumber D = new SmartNumber("Elevator/Position/D", 0.0);

            public static PIDController getController() {
                return new PIDController(P, I, D);
            }
        }

        // constant output
        public interface Feedforward {
            SmartNumber S = new SmartNumber("Elevator/Feedforward/S", 0.01);
            SmartNumber G = new SmartNumber("Elevator/Feedforward/G", 0.22);
            SmartNumber V = new SmartNumber("Elevator/Feedforward/V", 3.07);
            SmartNumber A = new SmartNumber("Elevator/Feedforward/A", 0.03);

            public static ElevatorFeedforward getFeedForward() {
                return new ElevatorFeedforward(S.get(), G.get(), V.get(), A.get());
            }
        }

        public interface VelocityFeedback {
            SmartNumber P = new SmartNumber("Elevator/Velocity/P", 0.5);
            SmartNumber I = new SmartNumber("Elevator/Velocity/I", 0.01);
            SmartNumber D = new SmartNumber("Elevator/Velocity/D", 0.05);

            public static PIDController getController() {
                return new PIDController(P, I, D);
            }
        }
    }
}
