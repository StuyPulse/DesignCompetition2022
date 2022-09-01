package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;

import edu.wpi.first.wpilibj2.command.Command;

public class AutonChooser {

    private enum SwitchState {
        REDRED,
        REDBLUE,
        BLUERED,
        BLUEBLUE
    };

    public enum StartPosition {
        LEFT,
        RIGHT
    };

    private static Command getCommand(RobotContainer robot, SwitchState state, StartPosition pos) {
        switch (state) {
            case REDRED:
                if (pos == StartPosition.RIGHT)
                     return new TwoRightRedRed(robot);
                else return new TwoLeftRedRed(robot);
            
            case REDBLUE:
                if (pos == StartPosition.RIGHT)
                     return new TwoRightRedBlue(robot);
                else return new TwoLeftRedBlue(robot);
            
            case BLUERED:
                if (pos == StartPosition.RIGHT)
                     return new TwoRightBlueRed(robot);
                else return new TwoLeftBlueRed(robot);
            
            case BLUEBLUE:
                if (pos == StartPosition.RIGHT)
                     return new TwoRightBlueBlue(robot);
                else return new TwoLeftBlueBlue(robot);
            
            default:
                return new DoNothingAuton();
        }
    }
    
    public static Command getAuton(RobotContainer robot, String fms, boolean red, StartPosition pos) {
        // if FMS string is not 3 letters or contains letters other than R and B
        if (fms.length() != 3 || !fms.matches("[RB]+")) {
            System.out.println("Error: invalid FMS color string: " + fms);
            System.exit(694);
        }

        SwitchState state = SwitchState.REDRED;

        if (red) {
            fms = fms.substring(0, 2);
    
            if (fms.equals("RR"))      state = SwitchState.REDRED;
            else if (fms.equals("RB")) state = SwitchState.REDBLUE;
            else if (fms.equals("BR")) state = SwitchState.BLUERED;
            else if (fms.equals("BB")) state = SwitchState.BLUEBLUE;
        } else {
            fms = fms.substring(1, 3);
            
            if (fms.equals("RR"))      state = SwitchState.REDRED;
            else if (fms.equals("RB")) state = SwitchState.BLUERED;
            else if (fms.equals("BR")) state = SwitchState.REDBLUE;
            else if (fms.equals("BB")) state = SwitchState.BLUEBLUE;
        }

        return getCommand(robot, state, pos);
    }

}
