/************************ PROJECT PHIL ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot;

import com.stuypulse.robot.commands.auton.AutonChooser;
import com.stuypulse.robot.commands.auton.DoNothingAuton;
import com.stuypulse.robot.commands.swerve.SwerveDrive;
import com.stuypulse.robot.subsystems.Swerve;
import com.stuypulse.robot.commands.elevator.ElevatorMove;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.subsystems.Elevator;
import com.stuypulse.robot.commands.intake.IntakeAcquire;
import com.stuypulse.robot.commands.intake.IntakeDeacquire;
import com.stuypulse.robot.commands.intake.IntakeExtend;
import com.stuypulse.robot.commands.intake.IntakeRetract;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.input.gamepads.AutoGamepad;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {

  // Gamepads
  public final Gamepad driver = new AutoGamepad(Ports.Gamepad.DRIVER);
  public final Gamepad operator = new AutoGamepad(Ports.Gamepad.OPERATOR);

  // Subsystem
  public final Swerve swerve = new Swerve();
  public final Intake intake = new Intake();
  public final Elevator elevator = new Elevator();

  // Autons
  private static SendableChooser<Integer> positionChooser = new SendableChooser<>();

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
      swerve.setDefaultCommand(new SwerveDrive(swerve, driver));
      elevator.setDefaultCommand(new ElevatorMove(elevator, operator));
  }

  /***************/
  /*** BUTTONS ***/
  /***************/

  private void configureButtonBindings() {
      /** INTAKE */
      operator.getLeftTriggerButton().whileHeld(new IntakeDeacquire(intake));
      operator.getRightTriggerButton().whileHeld(new IntakeAcquire(intake));
      operator.getLeftBumper().whenPressed(new IntakeRetract(intake));
      operator.getRightBumper().whenPressed(new IntakeExtend(intake));
    }

  /**************/
  /*** AUTONS ***/
  /**************/

  public void configureAutons() {
    positionChooser.setDefaultOption("Position 0", 0); // S3
    positionChooser.addOption("Position 1", 1); // S1

    SmartDashboard.putData("Autonomous", positionChooser);
  }

  public Command getAutonomousCommand() {
    new AutonChooser(this);
    return AutonChooser.getAuton(positionChooser.getSelected());
  }
}
