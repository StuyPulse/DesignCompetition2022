package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;

public class TwoLeftBlueBlue extends TwoBoxAuto {

    public TwoLeftBlueBlue(RobotContainer robot) {
        super(robot, "output/LeftStartToSwitchSame.wpilib.json", "output/LeftSwitchToBox.wpilib.json", "output/LeftSwitchToScaleSame.wpilib.json");
    }
    
}