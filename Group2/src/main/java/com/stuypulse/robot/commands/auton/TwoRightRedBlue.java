package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;

public class TwoRightRedBlue extends TwoBoxAuto {

    public TwoRightRedBlue(RobotContainer robot) {
        super(robot, "output/RightStartToSwitchSame.wpilib.json", "output/RightSwitchToBox.wpilib.json", "output/RightBoxToScaleDiff.wpilib.json");
    }
    
}
