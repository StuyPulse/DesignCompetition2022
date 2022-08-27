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
        int FIRST_MOTOR = -1;
        int SECOND_MOTOR = -1;
        int THIRD_MOTOR = -1;
        int FOURTH_MOTOR = -1;
    }
}
