package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;
import com.stuypulse.robot.commands.elevator.ElevatorToBottom;
import com.stuypulse.robot.commands.elevator.ElevatorToHeight;
import com.stuypulse.robot.commands.intake.IntakeTimedAcquire;
import com.stuypulse.robot.commands.intake.IntakeTimedDeacquire;
import com.stuypulse.robot.commands.swerve.SwerveTrajectoryFollower;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TopTopCratesAuton extends SequentialCommandGroup {

    String S3ToP1 = "TopTopCratesAuton/output/S3ToP1.wpilib.json";
    String P1ToP5 = "TopTopCratesAuton/output/P1ToP5.wpilib.json";
    String P5ToP3 = "TopTopCratesAuton/output/P5ToP3.wpilib.json";
    String P3ToP6 = "TopTopCratesAuton/output/P3ToP6.wpilib.json";
    String P6ToP3 = "TopTopCratesAuton/output/P6ToP3.wpilib.json";
    String P3ToP7 = "TopTopCratesAuton/output/P3ToP7.wpilib.json";
    String P7ToP3 = "TopTopCratesAuton/output/P7ToP3.wpilib.json";

    public TopTopCratesAuton(RobotContainer robot) {
        addCommands (
            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, S3ToP1),
                new ElevatorToHeight(robot.elevator, 0.41)
            ),
            new IntakeTimedDeacquire(robot.intake),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P1ToP5),
                new ElevatorToBottom(robot.elevator)
            ),
            new IntakeTimedAcquire(robot.intake),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P5ToP3),
                new ElevatorToHeight(robot.elevator, 2.0)
            ),
            new IntakeTimedDeacquire(robot.intake),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P3ToP6),
                new ElevatorToBottom(robot.elevator)
            ),
            new IntakeTimedAcquire(robot.intake),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P6ToP3),
                new ElevatorToHeight(robot.elevator, 1.9)
            ),
            new IntakeTimedDeacquire(robot.intake),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P3ToP7),
                new ElevatorToBottom(robot.elevator)
            ),
            new IntakeTimedAcquire(robot.intake),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P7ToP3),
                new ElevatorToHeight(robot.elevator, 1.9)
            ),
            new IntakeTimedDeacquire(robot.intake)
        );
    }
}
