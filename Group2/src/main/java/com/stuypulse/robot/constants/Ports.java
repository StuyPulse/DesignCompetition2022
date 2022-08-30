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

    public interface Elevator {
        // Motors
        int FIRST_MOTOR =  21;
        int SECOND_MOTOR = 22;
        int THIRD_MOTOR =  23;
        int FOURTH_MOTOR = 24;

        // Sensors
        int GREYHILL_A = 9;
        int GREYHILL_B = 10;
        int TOP_LIMIT = 11;
        int BOTTOM_LIMIT = 12;
    }
}
