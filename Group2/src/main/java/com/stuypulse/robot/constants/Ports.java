/********************** PROJECT GOOBER ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

/** This file contains the different ports of motors, solenoids and sensors */
public interface Ports {
    public interface Gamepad {
        int DRIVER = 0;
        int OPERATOR = 1;
        int DEBUGGER = 2;
    }

    public interface Intake {
        // Motors
        int DRIVER_A = 0;
        int DRIVER_B = 1;
        int DEPLOY = 2;
    }
    
    public interface Elevator {
        // Motors
        int FIRST_MOTOR =  21;
        int SECOND_MOTOR = 22;

        // Sensors
        int GREYHILL_A = 9;
        int GREYHILL_B = 10;
        int TOP_LIMIT = 11;
        int BOTTOM_LIMIT = 12;
    }
    
    public interface Swivel {
        int DRIVE_MOTOR = 10;
        int TURN_MOTOR = 11;

        int ABSOLUTE_ENCODER = 1;
    }
}
