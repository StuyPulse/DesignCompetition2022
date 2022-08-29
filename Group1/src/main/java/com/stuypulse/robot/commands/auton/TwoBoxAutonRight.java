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
 * @author Samuel Chen schen30@stuy.edu
 * @author Kelvin Zhao kzhao31@stuy.edu
 * @author Carmin Vuong carminvuong@gmail.com
 */

public class TwoBoxAutonRight extends SequentialCommandGroup{
    private double time = 15.0;
    private static final String SwitchRight = "deploy/TwoBoxAuton/output/switchRight.wpilib.json";
    private static final String RightSwitchToCenter = "deploy/TwoBoxAuton/output/RightSwitchToCenter.wpilib.json";
    
    public TwoBoxAutonRight(RobotContainer robot) {
        //goes to left switch and drops it
        addCommands(
            new DrivetrainRamsetteCommand(robot.drivetrain, SwitchRight),
            new ElevatorToSwitchCommand(robot.elevator),
            new IntakeDeacquireCommand(robot.intake),
            new IntakeRetractCommand(robot.intake),
            new ElevatorPickupCommand(robot.elevator)
                .withTimeout(time)
        );

        addCommands(
            new DrivetrainRamsetteCommand(robot.drivetrain, RightSwitchToCenter),
            new IntakeExtendCommand(robot.intake),
            new IntakeAcquireForeverCommand(robot.intake),
            new ElevatorToSwitchCommand(robot.elevator),
            new IntakeDeacquireCommand(robot.intake)
                .withTimeout(time)
                                       
        );
    }
}