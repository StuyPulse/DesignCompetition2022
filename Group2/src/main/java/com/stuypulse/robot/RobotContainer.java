/********************** PROJECT GOOBER ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot;

import com.stuypulse.robot.commands.auton.AutonChooser;
import com.stuypulse.robot.commands.auton.DoNothingAuton;
import com.stuypulse.robot.commands.auton.TwoRightBlueBlue;
import com.stuypulse.robot.commands.auton.TwoRightBlueRed;
import com.stuypulse.robot.commands.auton.TwoRightRedBlue;
import com.stuypulse.robot.commands.auton.TwoRightRedRed;
import com.stuypulse.robot.commands.swivel.SwivelDrive;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.subsystems.Swivel;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.input.gamepads.AutoGamepad;
import com.stuypulse.stuylib.network.SmartString;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {

  // Subsystem
  public final Swivel swivel = new Swivel();

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

    SmartString fms = new SmartString("FMSinfo/Switch Color", "RRR");
  }

  /****************/
  /*** DEFAULTS ***/
  /****************/

  private void configureDefaultCommands() {
    swivel.setDefaultCommand(new SwivelDrive(swivel, driver));
  }

  /***************/
  /*** BUTTONS ***/
  /***************/

  private void configureButtonBindings() {}

  /**************/
  /*** AUTONS ***/
  /**************/

  public void configureAutons() {
    autonChooser.setDefaultOption("Do Nothing", new DoNothingAuton());
    autonChooser.addOption("TwoRightRedRed", new TwoRightRedRed(this));
    autonChooser.addOption("TwoRightBlueRed", new TwoRightBlueRed(this));
    autonChooser.addOption("TwoRightRedBlue", new TwoRightRedBlue(this));
    autonChooser.addOption("TwoRightBlueBlue", new TwoRightBlueBlue(this));

    SmartDashboard.putData("Autonomous", autonChooser);
  }

  public Command getAutonomousCommand() {
    // return autonChooser.getSelected();
    return AutonChooser.getAuton(this, SmartDashboard.getString("FMSinfo/Switch Color", "RRR"));
  }
}
