package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;
import com.stuypulse.robot.commands.elevator.ElevatorToBottom;
import com.stuypulse.robot.commands.elevator.ElevatorToHeight;
import com.stuypulse.robot.commands.intake.*;
import com.stuypulse.robot.commands.swerve.SwerveTrajectoryFollower;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class S1BottomBottomCratesAuton extends SequentialCommandGroup {

    String S1toP2 = "StoP/output/S1toP2.wpilib.json";
    String P2toP10 = "ScaleToP/output/P2toP10.wpilib.json";
    String P10ToP4 = "BottomScaleAuton/output/P10ToP4.wpilib.json";
    String P4ToP9 = "BottomScaleAuton/output/P4ToP9.wpilib.json";
    String P9ToP4 = "BottomScaleAuton/output/P9ToP4.wpilib.json";
    String P4ToP8 = "BottomScaleAuton/output/P4ToP8.wpilib.json";
    String P8ToP4 = "BottomScaleAuton/output/P8ToP4.wpilib.json";

    public S1BottomBottomCratesAuton(RobotContainer robot){
        addCommands(
            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, S1toP2).robotRelative(),
                new ElevatorToHeight(robot.elevator, 0.41)
            ),
            new IntakeTimedDeacquire(robot.intake, 0.5).withTimeout(0.5),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P2toP10).fieldRelative(),
                new ElevatorToBottom(robot.elevator)
            ),
            new IntakeTimedAcquire(robot.intake, 0.5).withTimeout(0.5),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P10ToP4).fieldRelative(),
                new ElevatorToHeight(robot.elevator, 2.0)
            ),
            new IntakeTimedDeacquire(robot.intake, 0.5).withTimeout(0.5),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P4ToP9).fieldRelative(),
                new ElevatorToBottom(robot.elevator)
            ),
            
            new IntakeTimedAcquire(robot.intake, 0.5).withTimeout(0.5), 
            
            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P9ToP4).fieldRelative(),
                new ElevatorToHeight(robot.elevator, 2.0)
            ),
            new IntakeTimedDeacquire(robot.intake, 0.5).withTimeout(0.5),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P4ToP8).fieldRelative(),
                new ElevatorToBottom(robot.elevator)
            ),
            
            new IntakeTimedAcquire(robot.intake, 0.5).withTimeout(0.5),

            new ParallelCommandGroup(
                new SwerveTrajectoryFollower(robot.swerve, P8ToP4).fieldRelative(),
                new ElevatorToBottom(robot.elevator)
            ),
            
            new IntakeTimedDeacquire(robot.intake, 0.5)
            
            
        );
    }
}
