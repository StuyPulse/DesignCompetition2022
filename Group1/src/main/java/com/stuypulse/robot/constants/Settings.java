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

        SmartNumber TURN_DEADBAND = new SmartNumber("Drivetrain/Turn Deadband", 0.0);
        SmartNumber DRIVE_DEADBAND = new SmartNumber("Drivetrain/Drive Deadband", 0.0);

        SmartNumber TURN_POWER = new SmartNumber("Drivertrain/Turn Power", 3);
        SmartNumber DRIVE_POWER = new SmartNumber("Drivetrain/Drive Power", 3);
        SmartNumber TURN_RC = new SmartNumber("Drivetrain/Turn RC", 0);
        SmartNumber DRIVE_RC = new SmartNumber("Drivetrain/Drive RC", 0);
        
        SmartNumber MAX_TURN_SPEED = new SmartNumber("Drivetrain/Turn Max", 1);
        SmartNumber MAX_SPEED = new SmartNumber("Drivetrain/Max Speed", 15.0);
        

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
}
