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
        int FIRSTFOLLOWER = 21;
        int SECONDFOLLOWER = 22;

        int LOWERSWITCH = 23;
        int UPPERSWITCH = 24;
    }
}
