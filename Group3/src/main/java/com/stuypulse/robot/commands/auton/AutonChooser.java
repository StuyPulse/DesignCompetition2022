package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;
import com.stuypulse.robot.util.FakeFMS;
import com.stuypulse.robot.util.FakeFMS.Color;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * Uses the data from FakeFMS to determine which path to run.
 * 
 * @param position the starting position of the robot
 */
public class AutonChooser {

    private static RobotContainer robot;
    private static Color[] colors;

    public AutonChooser(RobotContainer container) {
        robot = container;
        colors = FakeFMS.getColors();
    }

    public static SequentialCommandGroup getAuton(Integer position) {
        switch (position) {
            case 1:
                switch (colors[0]) {
                    case RED:
                        switch (colors[2]) {
                            case RED: return new S1TopTopCratesAuton(robot);
                            case BLUE: return new S1TopBottomCratesAuton(robot);
                        }
                    case BLUE:
                        switch (colors[2]) {
                            case RED: return new S1BottomTopCratesAuton(robot);
                            case BLUE: return new S1BottomBottomCratesAuton(robot);
                        }
                }
            case 2:
                switch (colors[0]) {
                    case RED:
                        switch (colors[2]) {
                            case RED: return new S2TopTopCratesAuton(robot);
                            case BLUE: return new S2TopBottomCratesAuton(robot);
                        }
                    case BLUE:
                        switch (colors[2]) {
                            case RED: return new S2BottomTopCratesAuton(robot);
                            case BLUE: return new S2BottomBottomCratesAuton(robot);
                        }
                }
            case 3:
                switch (colors[0]) {
                    case RED:
                        switch (colors[2]) {
                            case RED: return new S3TopTopCratesAuton(robot);
                            case BLUE: return new S3TopBottomCratesAuton(robot);
                        }
                    case BLUE:
                        switch (colors[2]) {
                            case RED: return new S3BottomTopCratesAuton(robot);
                            case BLUE: return new S3BottomBottomCratesAuton(robot);
                        }
                }
            default:
                return new DoNothingAuton();
        }
        
    }
}
