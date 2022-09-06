package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;

public class TwoRightBlueBlue extends TwoBoxAuto {

    public TwoRightBlueBlue(RobotContainer robot) {
        super(robot, "output/RightStartToSwitchDiff.wpilib.json", "output/LeftSwitchToBox.wpilib.json", "output/LeftSwitchToScaleSame.wpilib.json");
    }
    
}
