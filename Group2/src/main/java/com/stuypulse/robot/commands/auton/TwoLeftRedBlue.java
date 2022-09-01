package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;
import com.stuypulse.robot.commands.swivel.SwivelTrajectory;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TwoLeftRedBlue extends SequentialCommandGroup {

    public TwoLeftRedBlue(RobotContainer robot) {
        addCommands(
            new SwivelTrajectory(robot.swivel, "output/LeftStartToScaleSame.wpilib.json").robotRelative(),
            new SwivelTrajectory(robot.swivel, "output/LeftScaleToRightBox.wpilib.json").fieldRelative(),
            new SwivelTrajectory(robot.swivel, "output/RightBoxToSwitch.wpilib.json").fieldRelative());
    }
    
}