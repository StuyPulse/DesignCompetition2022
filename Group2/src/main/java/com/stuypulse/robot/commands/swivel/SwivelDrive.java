package com.stuypulse.robot.commands.swivel;

import com.stuypulse.robot.constants.Settings.Swivel.Filtering.Drive;
import com.stuypulse.robot.constants.Settings.Swivel.Filtering.Turn;
import com.stuypulse.robot.subsystems.Swivel;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.vectors.VStream;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SwivelDrive extends CommandBase {
    
	private final Swivel swivel;
    
    private VStream speed;
    private IStream turn;

    public SwivelDrive(Swivel swivel, Gamepad gamepad) {
		this.swivel = swivel;

        speed = VStream.create(gamepad::getLeftStick).filtered(Drive.getFilter());
        turn = IStream.create(gamepad::getRightX).filtered(Turn.getFilter());

        addRequirements(swivel);
	}

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        swivel.setState(speed.get(), turn.get(), true);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean wasInterrupted) {}
}
