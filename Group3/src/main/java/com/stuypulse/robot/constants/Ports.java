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

    public interface Intake {
        int LEFT_DRIVER = 10;
        int RIGHT_DRIVER = 11;
        int DEPLOYMENT = 12; 
    }
    
    public interface Elevator {
        int LEFT_MOTOR = 20;
        int RIGHT_MOTOR = 21;

        int LOWER_SWITCH = 23;
        int UPPER_SWITCH = 24;
    }
}
