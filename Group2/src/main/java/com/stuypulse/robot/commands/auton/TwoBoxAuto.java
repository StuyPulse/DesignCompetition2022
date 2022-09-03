package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.RobotContainer;
import com.stuypulse.robot.commands.elevator.ElevatorToBottom;
import com.stuypulse.robot.commands.elevator.ElevatorToHeight;
import com.stuypulse.robot.commands.elevator.ElevatorToTop;
import com.stuypulse.robot.commands.intake.IntakeCommands;
import com.stuypulse.robot.commands.swivel.SwivelTrajectory;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class TwoBoxAuto extends SequentialCommandGroup {

	private static final double SWITCH_DROP_HEIGHT = Units.feetToMeters(4.0);

	private static final double SWITCH_DROP_TIME = 1.0;
	private static final double INTAKE_TIME = 1.5;
	
	public TwoBoxAuto(RobotContainer robot, String pathA, String pathB, String pathC) {
		addCommands(
			// move to switch and drop cube
			new ElevatorToHeight(SWITCH_DROP_HEIGHT, robot.elevator),
			new SwivelTrajectory(robot.swivel, pathA).robotRelative().withStop(),
			IntakeCommands.DeacquireForever(robot.intake),
			new WaitCommand(SWITCH_DROP_TIME),
			
			// intake second cube
			new ElevatorToBottom(robot.elevator),
			new SwivelTrajectory(robot.swivel, pathB).fieldRelative().withStop(),
			IntakeCommands.AcquireForever(robot.intake),
			new WaitCommand(INTAKE_TIME),

			// move to scale and drop cube
			new ElevatorToTop(robot.elevator),
			new SwivelTrajectory(robot.swivel, pathC).fieldRelative().withStop(),
			IntakeCommands.DeacquireForever(robot.intake)
		);
	}

}
