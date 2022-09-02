package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;
import com.stuypulse.robot.commands.elevator.MoveElevatorDistanceCommand;
import com.stuypulse.robot.commands.elevator.MoveElevatorDownCommand;
import com.stuypulse.robot.commands.elevator.MoveElevatorUpCommand;
import com.stuypulse.robot.commands.intake.IntakeCommands;
import com.stuypulse.robot.commands.swivel.SwivelStop;
import com.stuypulse.robot.commands.swivel.SwivelTrajectory;
import com.stuypulse.robot.constants.Settings;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class TwoBoxAuto extends SequentialCommandGroup {

	private static final double SWITCH_DROP_HEIGHT = 4.0;

	private static final double SWITCH_DROP_TIME = 1.0;
	private static final double INTAKE_TIME = 1.5;
	
	public TwoBoxAuto(RobotContainer robot, String pathA, String pathB, String pathC) {
		addCommands(
			// move to switch and drop cube
			new MoveElevatorDistanceCommand(SWITCH_DROP_HEIGHT, robot.elevator),
			new SwivelTrajectory(robot.swivel, pathA).robotRelative(),
			new SwivelStop(robot.swivel),
			IntakeCommands.DeacquireForever(robot.intake),
			new WaitCommand(SWITCH_DROP_TIME),
			
			// intake second cube
			new MoveElevatorDistanceCommand(0.0, robot.elevator),
			new SwivelTrajectory(robot.swivel, pathB).fieldRelative(),
			new SwivelStop(robot.swivel),
			IntakeCommands.AcquireForever(robot.intake),
			new WaitCommand(INTAKE_TIME),

			// move to scale and drop cube
			new MoveElevatorDistanceCommand(Settings.Elevator.TOP_HEIGHT, robot.elevator),
			new SwivelTrajectory(robot.swivel, pathC).fieldRelative(),
			IntakeCommands.DeacquireForever(robot.intake)
		);
	}

}
