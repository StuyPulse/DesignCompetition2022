package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;

public class TwoRightBlueRed extends TwoBoxAuto {

    public TwoRightBlueRed(RobotContainer robot) {
        super(robot, "output/RightStartToScaleSame.wpilib.json", "output/RightScaleToSwitch.wpilib.json", "output/LeftSwitchToBox.wpilib.json");
    }
    
}
