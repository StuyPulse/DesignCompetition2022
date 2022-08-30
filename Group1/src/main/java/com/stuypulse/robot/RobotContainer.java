/************************ PROJECT PHIL ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot;

import com.stuypulse.robot.commands.Intake.IntakeAcquireCommand;
import com.stuypulse.robot.commands.Intake.IntakeDeacquireCommand;
import com.stuypulse.robot.commands.Intake.IntakeExtendCommand;
import com.stuypulse.robot.commands.Intake.IntakeRetractCommand;
import com.stuypulse.robot.commands.auton.DoNothingAuton;
import com.stuypulse.robot.commands.drivetrain.DrivetrainDriveCommand;
import com.stuypulse.robot.commands.elevator.ElevatorClimbCommand;
import com.stuypulse.robot.commands.elevator.ElevatorPickupCommand;
import com.stuypulse.robot.commands.elevator.ElevatorToSwitchCommand;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Elevator;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.input.gamepads.AutoGamepad;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

/*
 * @author Samuel Chen
 * @author Vincent Wang
 */

public class RobotContainer {

  // Subsystem
  public final Drivetrain drivetrain = new Drivetrain();
  public final Intake intake = new Intake();
  public final Elevator elevator = new Elevator();

  // Gamepads
  public final Gamepad driver = new AutoGamepad(Ports.Gamepad.DRIVER);
  public final Gamepad operator = new AutoGamepad(Ports.Gamepad.OPERATOR);

  // Autons
  private static SendableChooser<Command> autonChooser = new SendableChooser<>();

  // Robot container

  public RobotContainer() {
    configureDefaultCommands();
    configureButtonBindings();
    configureAutons();
  }

  /****************/
  /*** DEFAULTS ***/
  /****************/

  private void configureDefaultCommands() {
    drivetrain.setDefaultCommand(new DrivetrainDriveCommand(drivetrain, driver));
  }

  /***************/
  /*** BUTTONS ***/
  /***************/

  private void configureButtonBindings() {

    //all intake commands
    operator.getLeftButton().whenPressed(new IntakeExtendCommand(intake));
    operator.getRightButton().whenPressed(new IntakeRetractCommand(intake));
    operator.getTopButton().whenPressed(new IntakeAcquireCommand(intake));
    operator.getBottomButton().whenPressed(new IntakeDeacquireCommand(intake));

    //all elevator commands
    operator.getDPadLeft().whenPressed(new ElevatorPickupCommand(elevator));
    operator.getDPadUp().whenPressed(new ElevatorToSwitchCommand(elevator));
    operator.getDPadRight().whenPressed(new ElevatorToSwitchCommand(elevator));
    operator.getDPadDown().whenPressed(new ElevatorClimbCommand(elevator));


  }

  /**************/
  /*** AUTONS ***/
  /**************/

  public void configureAutons() {
    autonChooser.setDefaultOption("Do Nothing", new DoNothingAuton());

    SmartDashboard.putData("Autonomous", autonChooser);
  }

  public Command getAutonomousCommand() {
    return autonChooser.getSelected();
  }
}
