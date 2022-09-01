package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;
import com.stuypulse.robot.commands.swivel.SwivelTrajectory;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TwoLeftRedRed extends SequentialCommandGroup {

    public TwoLeftRedRed(RobotContainer robot) {
        addCommands(
            new SwivelTrajectory(robot.swivel, "output/LeftStartToSwitchDiff.wpilib.json").robotRelative(),
            new SwivelTrajectory(robot.swivel, "output/RightSwitchToBox.wpilib.json").fieldRelative(),
            new SwivelTrajectory(robot.swivel, "output/RightBoxToScaleSame.wpilib.json").fieldRelative());
    }
    
}