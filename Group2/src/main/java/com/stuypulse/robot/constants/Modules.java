package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.math.Vector2D;

import edu.wpi.first.math.geometry.Rotation2d;

public interface Modules {
    /**
     * Coordinates for position use a rotated coordinate space:
     * +x moves to the front of the robot
     * +y moves to the left of the robot
     */
    public class ModuleConfig {
        public final Vector2D offset;
        public final String id;
        public final Rotation2d zeroAngle;

        public ModuleConfig(Vector2D position, String id, Rotation2d zeroAngle) {
            this.offset = position;
            this.id = id;
            this.zeroAngle = zeroAngle;
        }
    }

    ModuleConfig FRONT_LEFT =  new ModuleConfig(
        new Vector2D(+0.5, +0.5),
        "Front Left",
        Rotation2d.fromDegrees(0));
        
    ModuleConfig FRONT_RIGHT = new ModuleConfig(
        new Vector2D(+0.5, -0.5),
        "Front Right",
        Rotation2d.fromDegrees(0));

    ModuleConfig BACK_LEFT =   new ModuleConfig(
        new Vector2D(-0.5, +0.5),
        "Back Left",
        Rotation2d.fromDegrees(0));

    ModuleConfig BACK_RIGHT =  new ModuleConfig(
        new Vector2D(-0.5, -0.5),
        "Back Right",
        Rotation2d.fromDegrees(0));
}
