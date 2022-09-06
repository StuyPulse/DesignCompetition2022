package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;

public class TwoLeftBlueRed extends TwoBoxAuto {

    public TwoLeftBlueRed(RobotContainer robot) {
        super(robot, "output/LeftStartToSwitchSame.wpilib.json", "output/LeftSwitchToBox.wpilib.json", "output/RightSwitchToScaleDiff.wpilib.json");
    }
    
}