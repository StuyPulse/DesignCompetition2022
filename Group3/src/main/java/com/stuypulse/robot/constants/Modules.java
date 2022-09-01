package com.stuypulse.robot.constants;

import com.stuypulse.robot.subsystems.SimModule;
import com.stuypulse.robot.subsystems.SwerveModule;
import com.stuypulse.stuylib.math.Angle;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;

public interface Modules {

    double LENGTH = Units.inchesToMeters(32.0);
    double WIDTH = Units.inchesToMeters(32.0);

    double MAX_ACCELERATION = 4.2;
    double MAX_SPEED = Units.feetToMeters(16.3);

    double MAX_ANGULAR_SPEED = MAX_SPEED / Math.hypot(LENGTH / 2.0, WIDTH / 2.0);
    double MAX_ANGULAR_ACCEL = 4.2;

    public interface TopRight {
        String ID = "Top Right";
        Translation2d MODULE_OFFSET = new Translation2d(LENGTH * +0.5, WIDTH * -0.5);
        Angle ENCODER_OFFSET = Angle.fromDegrees(45);

        int DRIVE_MOTOR = 0;
        int TURN_MOTOR = 1;

        int ABS_ENCODER = 0;

        SimModule MODULE = new SimModule(
            ID,
            MODULE_OFFSET,
            DRIVE_MOTOR,
            TURN_MOTOR,
            ABS_ENCODER,
            ENCODER_OFFSET
        );
    }

    public interface TopLeft {
        String ID = "Top Left";
        Translation2d MODULE_OFFSET = new Translation2d(LENGTH * +0.5, WIDTH * +0.5);
        Angle ENCODER_OFFSET = Angle.fromDegrees(-45);

        int DRIVE_MOTOR = 2;
        int TURN_MOTOR = 3;

        int ABS_ENCODER = 1;

        SimModule MODULE = new SimModule(
            ID,
            MODULE_OFFSET,
            DRIVE_MOTOR,
            TURN_MOTOR,
            ABS_ENCODER,
            ENCODER_OFFSET
        );
    }

    public interface BottomLeft {
        String ID = "Bottom Left";
        Translation2d MODULE_OFFSET = new Translation2d(LENGTH * -0.5, WIDTH * -0.5);
        Angle ENCODER_OFFSET = Angle.fromDegrees(-135);

        int DRIVE_MOTOR = 4;
        int TURN_MOTOR = 5;

        int ABS_ENCODER = 2;

        SimModule MODULE = new SimModule(
            ID,
            MODULE_OFFSET,
            DRIVE_MOTOR,
            TURN_MOTOR,
            ABS_ENCODER,
            ENCODER_OFFSET
        );
    }

    public interface BottomRight {
        String ID = "Bottom Right";
        Translation2d MODULE_OFFSET = new Translation2d(LENGTH * -0.5, WIDTH * +0.5);
        Angle ENCODER_OFFSET = Angle.fromDegrees(135);

        int DRIVE_MOTOR = 6;
        int TURN_MOTOR = 7;

        int ABS_ENCODER = 3;

        SimModule MODULE = new SimModule(
            ID,
            MODULE_OFFSET,
            DRIVE_MOTOR,
            TURN_MOTOR,
            ABS_ENCODER,
            ENCODER_OFFSET
        );
    }

    SimModule[] MODULES = {
        TopRight.MODULE,
        TopLeft.MODULE,
        BottomLeft.MODULE,
        BottomRight.MODULE
    };
}
