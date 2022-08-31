package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;

import edu.wpi.first.wpilibj2.command.Command;

public class AutonChooser {
    
    public static Command getAuton(RobotContainer robot, String fms, boolean red) {
        if (red) {
            fms = fms.substring(0, 2);
    
            if (fms.equals("RR"))
                return new TwoRightRedRed(robot);
            else if (fms.equals("RB"))
                return new TwoRightRedBlue(robot);
            else if (fms.equals("BR"))
                return new TwoRightBlueRed(robot);
            else if (fms.equals("BB"))
                return new TwoRightBlueBlue(robot);
            else {
                System.out.println("Error: invalid FMS color string: " + fms);
                System.exit(694);
            }
        } else {
            fms = fms.substring(1, 3);

            if (fms.equals("RR"))
                return new TwoRightRedRed(robot);
            else if (fms.equals("RB"))
                return new TwoRightBlueRed(robot);
            else if (fms.equals("BR"))
                return new TwoRightRedBlue(robot);
            else if (fms.equals("BB"))
                return new TwoRightBlueBlue(robot);
            else {
                System.out.println("Error: invalid FMS color string: " + fms);
                System.exit(694);
            }
        }

        return null;
    }

}
