package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;
import com.stuypulse.robot.commands.swivel.SwivelTrajectory;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TestAuton extends SequentialCommandGroup {

    public TestAuton(RobotContainer robot) {
        addCommands(
                new SwivelTrajectory(robot.swivel, "test auton/output/test.wpilib.json").robotRelative(),
                new SwivelTrajectory(robot.swivel, "test auton/output/test2.wpilib.json").fieldRelative());
    }
}