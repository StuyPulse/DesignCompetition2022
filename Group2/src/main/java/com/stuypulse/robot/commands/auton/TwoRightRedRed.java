package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;

public class TwoRightRedRed extends TwoBoxAuto {

    public TwoRightRedRed(RobotContainer robot) {
        super(robot, "output/RightStartToSwitchSame.wpilib.json", "output/RightSwitchToBox.wpilib.json", "output/RightBoxToScaleSame.wpilib.json");
    }
    
}
