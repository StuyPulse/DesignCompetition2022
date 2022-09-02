package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;

public class TwoLeftRedBlue extends TwoBoxAuto {

    public TwoLeftRedBlue(RobotContainer robot) {
        super(robot, "output/LeftStartToScaleSame.wpilib.json", "output/LeftScaleToRightBox.wpilib.json", "output/RightBoxToSwitch.wpilib.json");
    }
    
}