package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;
import com.stuypulse.robot.util.FakeFMS;
import com.stuypulse.robot.util.FakeFMS.Color;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutonChooser {

    private static RobotContainer robot;
    private static Color[] colors;

    public AutonChooser(RobotContainer container) {
        robot = container;
        colors = FakeFMS.getColors();
    }

    // i dunno how you wanna do this but you got this. 2am autons not it :D

    public static SequentialCommandGroup getAuton(Integer position) {
        switch (position) {
            case 0:
                switch (colors[0]) {
                    case RED:
                        switch (colors[1]) {
                            case RED: return new TopTopCratesAuton(robot);
                            case BLUE: return new TopBottomCratesAuton(robot);
                        }
                    case BLUE:
                        switch (colors[1]) {
                            case RED: return new BottomTopCratesAuton(robot);
                            case BLUE: return new BottomBottomCratesAuton(robot);
                        }
                }
            case 1: 
        }
        
    }
}
