/************************ PROJECT PHIL ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot;

import com.stuypulse.robot.commands.auton.DoNothingAuton;
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
    public final Elevator elevator = new Elevator();
    public final Intake intake = new Intake();

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
      autonChooser.setDefaultOption("Do Nothing", new DoNothingAuton());
      SmartDashboard.putData("Autonomous", autonChooser);
    }

    public Command getAutonomousCommand() {
      return autonChooser.getSelected();
    }
}
