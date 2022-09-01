package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;
import com.stuypulse.robot.commands.swivel.SwivelTrajectory;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TwoRightBlueRed extends SequentialCommandGroup {

    public TwoRightBlueRed(RobotContainer robot) {
        addCommands(
            new SwivelTrajectory(robot.swivel, "output/RightStartToScaleSame.wpilib.json").robotRelative(),
            new SwivelTrajectory(robot.swivel, "output/RightScaleToSwitch.wpilib.json").fieldRelative(),
            new SwivelTrajectory(robot.swivel, "output/LeftSwitchToBox.wpilib.json").fieldRelative());
    }
    
}
