package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;
import com.stuypulse.robot.commands.elevator.ElevatorToBottom;
import com.stuypulse.robot.commands.elevator.ElevatorToHeight;
import com.stuypulse.robot.commands.intake.IntakeExtend;
import com.stuypulse.robot.commands.intake.IntakeTimedAcquire;
import com.stuypulse.robot.commands.intake.IntakeTimedDeacquire;
import com.stuypulse.robot.commands.swerve.SwerveTrajectoryFollower;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class S3TopTopCratesAuton extends SequentialCommandGroup {

    String S3ToP1 = "StoP/output/S3toP1.wpilib.json";
    String P1ToP5 = "ScaleToP/output/P1toP5.wpilib.json";
    String P5ToP3 = "TopScaleAuton/output/P5toP3.wpilib.json";
    String P3ToP6 = "TopScaleAuton/output/P3toP6.wpilib.json";
    String P6ToP3 = "TopScaleAuton/output/P6toP3.wpilib.json";
    String P3ToP7 = "TopScaleAuton/output/P3toP7.wpilib.json";
    String P7ToP3 = "TopScaleAuton/output/P7toP3.wpilib.json";

    public S3TopTopCratesAuton(RobotContainer robot) {
        addCommands (
            new IntakeExtend(robot.intake),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, S3ToP1).robotRelative(),
                new ElevatorToHeight(robot.elevator, 0.41)
            ),
            new IntakeTimedDeacquire(robot.intake).withTimeout(0.5),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P1ToP5).fieldRelative(),
                new ElevatorToBottom(robot.elevator)
            ),
            new IntakeTimedAcquire(robot.intake).withTimeout(0.5),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P5ToP3).fieldRelative(),
                new ElevatorToHeight(robot.elevator, 2.0)
            ),
            new IntakeTimedDeacquire(robot.intake).withTimeout(0.5),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P3ToP6).fieldRelative(),
                new ElevatorToBottom(robot.elevator)
            ),
            new IntakeTimedAcquire(robot.intake).withTimeout(0.5),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P6ToP3).fieldRelative(),
                new ElevatorToHeight(robot.elevator, 1.9)
            ),
            new IntakeTimedDeacquire(robot.intake).withTimeout(0.5),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P3ToP7).fieldRelative(),
                new ElevatorToBottom(robot.elevator)
            ),
            new IntakeTimedAcquire(robot.intake).withTimeout(0.5),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P7ToP3).fieldRelative(),
                new ElevatorToHeight(robot.elevator, 1.9)
            ),
            new IntakeTimedDeacquire(robot.intake).withTimeout(0.5)
        );
    }
}
