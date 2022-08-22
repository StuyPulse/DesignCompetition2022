/************************ PROJECT PHIL ************************/
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
        int LEADER = 20;
        int FIRST_FOLLOWER = 21;
        int SECOND_FOLLOWER = 22;

        int LOWER_SWITCH = 23;
        int UPPER_SWITCH = 24;
    }
}
