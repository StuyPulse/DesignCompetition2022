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
        int FIRST_MOTOR =  11;
        int SECOND_MOTOR = 12;

        // Sensors
        int GREYHILL_A = 0;
        int GREYHILL_B = 1;
        int TOP_LIMIT = 2;
        int BOTTOM_LIMIT = 3;
    }


    public interface BuddyClimb {
        //Motor
        int MOTOR = 21;
        
        //Sensor
        int GREYHILL_A = 4;
        int GREYHILL_B = 5;
    }
    
    public interface Swivel {
        int DRIVE_MOTOR = 30;
        int TURN_MOTOR = 31;

        int ABSOLUTE_ENCODER = 6;

    }
}
