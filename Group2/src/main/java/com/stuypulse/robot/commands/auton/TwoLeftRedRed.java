package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;

public class TwoLeftRedRed extends TwoBoxAuto {

    public TwoLeftRedRed(RobotContainer robot) {
        super(robot, "output/LeftStartToSwitchDiff.wpilib.json", "output/RightSwitchToBox.wpilib.json", "output/RightBoxToScaleSame.wpilib.json");
    }
    
}