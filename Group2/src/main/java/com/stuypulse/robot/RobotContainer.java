/********************** PROJECT GOOBER ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot;

import com.stuypulse.robot.commands.auton.AutonChooser;
import com.stuypulse.robot.commands.auton.AutonChooser.StartPosition;
import com.stuypulse.robot.commands.elevator.ElevatorDrive;
import com.stuypulse.robot.commands.swivel.SwivelDrive;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.subsystems.Elevator;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.robot.subsystems.intake.SimIntake;
import com.stuypulse.robot.subsystems.Swivel;
import com.stuypulse.robot.subsystems.elevator.SimElevator;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.input.gamepads.AutoGamepad;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {

  // Subsystem
  public final Intake intake = new SimIntake();
  public final Swivel swivel = new Swivel();
  public final Elevator elevator = new SimElevator();

  // Gamepads
  public final Gamepad driver = new AutoGamepad(Ports.Gamepad.DRIVER);
  public final Gamepad operator = new AutoGamepad(Ports.Gamepad.OPERATOR);

  // Autons
  private static SendableChooser<StartPosition> startPosChooser = new SendableChooser<>();

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
    swivel.setDefaultCommand(new SwivelDrive(swivel, driver));

    elevator.setDefaultCommand(new ElevatorDrive(elevator, operator));
  }

  /***************/
  /*** BUTTONS ***/
  /***************/

  private void configureButtonBindings() {}

  /**************/
  /*** AUTONS ***/
  /**************/

  public void configureAutons() {
    startPosChooser.setDefaultOption("Right", StartPosition.RIGHT);
    startPosChooser.addOption("Left", StartPosition.LEFT);

    SmartDashboard.putData("Autonomous", startPosChooser);
  }

  public Command getAutonomousCommand() {
    return AutonChooser.getAuton(this,
      DriverStation.getGameSpecificMessage(),
      DriverStation.getAlliance(),
      startPosChooser.getSelected());
  }
}
