package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;
import com.stuypulse.robot.commands.swivel.SwivelTrajectory;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TwoLeftBlueRed extends SequentialCommandGroup {

    public TwoLeftBlueRed(RobotContainer robot) {
        addCommands(
            new SwivelTrajectory(robot.swivel, "output/LeftStartToSwitchSame.wpilib.json").robotRelative(),
            new SwivelTrajectory(robot.swivel, "output/LeftSwitchToBox.wpilib.json").fieldRelative(),
            new SwivelTrajectory(robot.swivel, "output/LeftSwitchToScaleDiff.wpilib.json").fieldRelative());
    }
    
}