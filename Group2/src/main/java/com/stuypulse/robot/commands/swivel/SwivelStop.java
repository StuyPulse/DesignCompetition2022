package com.stuypulse.robot.commands.swivel;

import com.stuypulse.robot.subsystems.Swivel;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class SwivelStop extends InstantCommand {

	private final Swivel swivel;
	
	public SwivelStop(Swivel swivel) {
		this.swivel = swivel;
		
		addRequirements(swivel);
	}

	@Override
	public void initialize() {
		swivel.stop();
	}

}
