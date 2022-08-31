package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;
import com.stuypulse.robot.commands.elevator.ElevatorToBottom;
import com.stuypulse.robot.commands.elevator.ElevatorToHeight;
import com.stuypulse.robot.commands.intake.IntakeTimedAcquire;
import com.stuypulse.robot.commands.intake.IntakeTimedDeacquire;
import com.stuypulse.robot.commands.swerve.SwerveTrajectoryFollower;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TopBottomCratesAuton extends SequentialCommandGroup {

    String S3ToP1 = "TopBottomCratesAuton/output/S3ToP1.wpilib.json";
    String P1ToP10 = "TopBottomCratesAuton/output/P1ToP10.wpilib.json";
    String P10ToP4 = "TopBottomCratesAuton/output/P10ToP4.wpilib.json";
    String P4ToP9 = "TopBottomCratesAuton/output/P4ToP9.wpilib.json";
    String P9ToP4 = "TopBottomCratesAuton/output/P9ToP4.wpilib.json";
    String P4ToP8 = "TopBottomCratesAuton/output/P4ToP8.wpilib.json";
    String P8ToP4 = "TopBottomCratesAuton/output/P8ToP4.wpilib.json";

    public TopBottomCratesAuton(RobotContainer robot) {
        addCommands (
            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, S3ToP1),
                new ElevatorToHeight(robot.elevator, 0.41)
            ),
            new IntakeTimedDeacquire(robot.intake),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P1ToP10),
                new ElevatorToBottom(robot.elevator)
            ),
            new IntakeTimedAcquire(robot.intake),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P10ToP4),
                new ElevatorToHeight(robot.elevator, 2.0)
            ),
            new IntakeTimedDeacquire(robot.intake),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P4ToP9),
                new ElevatorToBottom(robot.elevator)
            ),
            new IntakeTimedAcquire(robot.intake),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P9ToP4),
                new ElevatorToHeight(robot.elevator, 1.9)
            ),
            new IntakeTimedDeacquire(robot.intake),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P4ToP8),
                new ElevatorToBottom(robot.elevator)
            ),
            new IntakeTimedAcquire(robot.intake),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P8ToP4),
                new ElevatorToHeight(robot.elevator, 1.9)
            ),
            new IntakeTimedDeacquire(robot.intake)
        );
    }
}
