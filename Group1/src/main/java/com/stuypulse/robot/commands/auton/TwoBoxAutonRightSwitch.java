package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.commands.Intake.IntakeAcquireForeverCommand;
import com.stuypulse.robot.commands.Intake.IntakeDeacquireCommand;
import com.stuypulse.robot.commands.Intake.IntakeExtendCommand;
import com.stuypulse.robot.commands.Intake.IntakeRetractCommand;
import com.stuypulse.robot.commands.drivetrain.DrivetrainRamsetteCommand;
import com.stuypulse.robot.commands.elevator.ElevatorPickupCommand;
import com.stuypulse.robot.commands.elevator.ElevatorToSwitchCommand;
import com.stuypulse.robot.RobotContainer;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/*
 * @author Samuel Chen
 * @author Kelvin Zhao
 * @author Carmin Vuong
 */
public class TwoBoxAutonRightSwitch extends SequentialCommandGroup {
    private final RobotContainer robot;

    private double time = 1.0;
    private static final String SwitchRight = "TwoBoxAuton/output/switchRight.wpilib.json";
    private static final String RightSwitchToCenter = "TwoBoxAuton/output/RightSwitchToCenter.wpilib.json";

    public TwoBoxAutonRightSwitch(RobotContainer robot) {
        this.robot = robot;
       addCommands(
                new DrivetrainRamsetteCommand(robot.drivetrain, SwitchRight),
                new ElevatorToSwitchCommand(robot.elevator),
                new IntakeDeacquireCommand(robot.intake),
                new IntakeRetractCommand(robot.intake),
                new ElevatorPickupCommand(robot.elevator));

        addCommands(
                new DrivetrainRamsetteCommand(robot.drivetrain, RightSwitchToCenter),
                new IntakeExtendCommand(robot.intake),
                new IntakeAcquireForeverCommand(robot.intake),
                new ElevatorToSwitchCommand(robot.elevator),
                new IntakeDeacquireCommand(robot.intake));
    }
}