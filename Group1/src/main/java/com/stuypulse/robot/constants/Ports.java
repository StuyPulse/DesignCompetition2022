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

    public interface Drivetrain {
        int LEFT_FRONT = 0;
        int LEFT_MIDDLE = 1;
        int LEFT_BACK = 2;

        int RIGHT_FRONT = 3;
        int RIGHT_MIDDLE = 4;
        int RIGHT_BACK = 5;

        public interface Grayhill {
            int LEFT_A = 1;
            int LEFT_B = 2;
            int RIGHT_A = 3;
            int RIGHT_B = 4;
        }
    }

    public interface Intake {
        int LEFT_DRIVER = 6;
        int RIGHT_DRIVER = 7;

        int DEPLOYER_A = 5;
        int DEPLOYER_B = 6;

        int DEPLOY = 8;
    }

    public interface Elevator {
        int FIRST = 9;
        int SECOND = 10;
        int THIRD = 11;

        int LOWER = 7;
        int UPPER = 8;

        int A = 9;
        int B = 10;
    }
}
