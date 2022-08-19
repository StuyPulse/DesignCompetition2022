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
        int LEFT_FRONT = -1;
        int LEFT_MIDDLE = -1;
        int LEFT_BOTTOM = -1;

        int RIGHT_FRONT = -1;
        int RIGHT_MIDDLE = -1;
        int RIGHT_BOTTOM = -1;

        public interface Grayhill {
            int LEFT_A = -1;
            int LEFT_B = -1;
            int RIGHT_A = -1;
            int RIGHT_B = -1;
        }
    }

    public interface Intake {
        int LEFT_DRIVER = -1;
        int RIGHT_DRIVER = -1;

        int DEPLOYER_A = -1;
        int DEPLOYER_B = -1;

        int DEPLOY = -1;
    }
}
