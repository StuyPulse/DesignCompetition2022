/************************ PROJECT PHIL ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.network.SmartAngle;
import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.math.util.Units;

/*-
 * File containing tunable settings for every subsystem on the robot.
 *
 * We use StuyLib's SmartNumber / SmartBoolean in order to have tunable
 * values that we can edit on Shuffleboard.
 */
public interface Settings {
    public interface Drivetrain {
        double TRACK_WIDTH = Units.inchesToMeters(30);
        double WHEEL_DIAMETER = Units.inchesToMeters(2);

        double GRAYHILL_TO_WHEEL = 0.22666;
        double PULSES_PER_REVOLUTION = 256;
        double DISTANCE_PER_PULSE = (WHEEL_DIAMETER * Math.PI) / 256 * GRAYHILL_TO_WHEEL;

        SmartNumber TURN_DEADBAND = new SmartNumber("Drivetrain/Turn Deadband", 0.0);
        SmartNumber DRIVE_DEADBAND = new SmartNumber("Drivetrain/Drive Deadband", 0.0);

        SmartNumber TURN_POWER = new SmartNumber("Drivertrain/Turn Power", 3);
        SmartNumber DRIVE_POWER = new SmartNumber("Drivetrain/Drive Power", 3);
        SmartNumber TURN_RC = new SmartNumber("Drivetrain/Turn RC", 0);
        SmartNumber DRIVE_RC = new SmartNumber("Drivetrain/Drive RC", 0);

        SmartNumber MAX_TURN_SPEED = new SmartNumber("Drivetrain/Turn Max", 1);
        SmartNumber MAX_SPEED = new SmartNumber("Drivetrain/Max Speed", 15.0);

        interface PID {
            SmartNumber kP = new SmartNumber("Drivetrain/kP", 0.00337);
            SmartNumber kI = new SmartNumber("Drivetrain/kI", 0);
            SmartNumber kD = new SmartNumber("Drivetrain/kD", 0);
        }

        interface FF {
            SmartNumber kS = new SmartNumber("Drivetrain/kS", 0.367);
            SmartNumber kV = new SmartNumber("Drivetrain/kV", 2.07);
            SmartNumber kA = new SmartNumber("Drivetrain/kA", 0.47);

            SmartNumber kVA = new SmartNumber("Drivetrain/kVA", 0.1);
            SmartNumber kAA = new SmartNumber("Drivetrain/kAA", 0.1);
        }
    }

    public interface Intake {
        SmartAngle RETRACT_ANGLE = new SmartAngle("Intake/Retract Angle", Angle.fromDegrees(0));
        SmartAngle EXTEND_ANGLE = new SmartAngle("Intake/Extend Angle", Angle.fromDegrees(120));

        interface PID {
            SmartNumber kP = new SmartNumber("Intake/kP", 0);
            SmartNumber kI = new SmartNumber("Intake/kI", 0);
            SmartNumber kD = new SmartNumber("Intake/kD", 0);
        }

        interface FF {
            SmartNumber kS = new SmartNumber("Intake/kS", 0.1);
            SmartNumber kV = new SmartNumber("Intake/kV", 0.1);
            SmartNumber kA = new SmartNumber("Intake/kA", 0.1);
        }

        interface SIMULATION {
            double WIDTH = 3;
            double HEIGHT = 4;

            double ROOT_WIDTH = 2;
            double ROOT_HEIGHT = 0;

            double ARM_WIDTH = 1;
        }
    }

    public interface Elevator {
        double RUNG = 2.2;
        double SWITCH = 1.98;
        double SCALE = 0.45;

        double BOX = 0;

        double MAX_ELEVATOR_HEIGHT = 10;

        SmartNumber LIFT_DEADBAND = new SmartNumber("Elevator/Lift Deadband", 0.0);

        SmartNumber LIFT_POWER = new SmartNumber("Elevator/Lift Power", 3);
        SmartNumber LIFT_RC = new SmartNumber("Elevator/Lift RC", 0);

        interface PID {
            SmartNumber kP = new SmartNumber("Elevator/kP", 0);
            SmartNumber kI = new SmartNumber("Elevator/kI", 0);
            SmartNumber kD = new SmartNumber("Elevator/kD", 0);
        }

        interface SIMULATION {
            double WIDTH = 3;
            double HEIGHT = 4;

            double ROOT_WIDTH = 2;
            double ROOT_HEIGHT = 0;

            double ARM_WIDTH = 1;
            
            double GEARING = 12;
            double DRUM_RADIUS = 1;
            double MIN_ELEVATOR_HEIGHT = 1;
            double MAX_ELEVATOR_HEIGHT = 3;
        }

        interface FF {
            SmartNumber kG = new SmartNumber("Elevator/kG", 0.1);
            SmartNumber kS = new SmartNumber("Elevator/kS", 0.1);
            SmartNumber kV = new SmartNumber("Elevator/kV", 0.1);
            SmartNumber kA = new SmartNumber("Elevator/kA", 0.1);
        }
    }
}
