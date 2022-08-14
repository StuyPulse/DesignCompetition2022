/************************ PROJECT PHIL ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.network.SmartNumber;

/*-
 * File containing tunable settings for every subsystem on the robot.
 *
 * We use StuyLib's SmartNumber / SmartBoolean in order to have tunable
 * values that we can edit on Shuffleboard.
 */
public interface Settings {
    public interface Drivetrain {
        double TRACK_WIDTH = 0.5;
        
        interface PID {
            SmartNumber kP = new SmartNumber("Drivetrain/kP", 0);
            SmartNumber kI = new SmartNumber("Drivetrain/kI", 0);
            SmartNumber kD = new SmartNumber("Drivetrain/kD", 0);
        }
        interface FF {
            SmartNumber kS = new SmartNumber("Drivetrain/kS", 0);
            SmartNumber kV = new SmartNumber("Drivetrain/kV", 0);
            SmartNumber kA = new SmartNumber("Drivetrain/kA", 0);
        }
    }

    public interface Elevator {
        double TRACK_WIDTH = 0.5;

        double RUNG = 2.2;
        double SWITCH = 1.98;
        double SCALE = 0.45;

        double BOX = 0;
        
        interface PID {
            SmartNumber kP = new SmartNumber("Elevator/kP", 0);
            SmartNumber kI = new SmartNumber("Elevator/kI", 0);
            SmartNumber kD = new SmartNumber("Elevator/kD", 0);
        }

        interface SIMULATION {
            double WIDTH = 3;
            double HEIGHT = 4;

            double ROOT_WIDTH = 2;
            double ROOT_HEIGHT = 0;
        }
        interface FF {
            SmartNumber kG = new SmartNumber("Elevator/kG", 0);
            SmartNumber kS = new SmartNumber("Elevator/kS", 0);
            SmartNumber kV = new SmartNumber("Elevator/kV", 0);
            SmartNumber kA = new SmartNumber("Elevator/kA", 0);
        }
    }
}
