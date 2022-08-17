package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.math.Vector2D;

public interface Modules {
    /**
     * Coordinates for position use a rotated coordinate space:
     * +x moves to the front of the robot
     * +y moves to the left of the robot
     */
    public class ModuleConfig {
        public final Vector2D position;
        public final String id;

        public ModuleConfig(Vector2D position, String id) {
            this.position = position;
            this.id = id;
        }
    }

    ModuleConfig FRONT_LEFT =  new ModuleConfig(new Vector2D(+0.5, +0.5), "Front Left");
    ModuleConfig FRONT_RIGHT = new ModuleConfig(new Vector2D(+0.5, -0.5), "Front Left");
    ModuleConfig BACK_LEFT =   new ModuleConfig(new Vector2D(-0.5, +0.5), "Back Left");
    ModuleConfig BACK_RIGHT =  new ModuleConfig(new Vector2D(-0.5, -0.5), "Back Left");
}
