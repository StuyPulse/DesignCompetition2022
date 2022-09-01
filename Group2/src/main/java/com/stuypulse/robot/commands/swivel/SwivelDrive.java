package com.stuypulse.robot.commands.swivel;

import com.stuypulse.robot.constants.Settings.Swivel.Filtering.Drive;
import com.stuypulse.robot.constants.Settings.Swivel.Filtering.Turn;
import com.stuypulse.robot.subsystems.Swivel;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.filters.IFilter;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;
import com.stuypulse.stuylib.streams.vectors.VStream;
import com.stuypulse.stuylib.streams.vectors.filters.VDeadZone;
import com.stuypulse.stuylib.streams.vectors.filters.VLowPassFilter;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SwivelDrive extends CommandBase {
    
    private final Swivel swivel;
    
    private VStream speed;
    private IStream turn;

    public SwivelDrive(Swivel swivel, Gamepad gamepad) {
        this.swivel = swivel;

        speed = VStream.create(gamepad::getLeftStick).filtered(
            new VDeadZone(Drive.DEADZONE)
                .then(new VLowPassFilter(Drive.RC))
                .then(x -> x.mul(Drive.MAX_SPEED.get())));

        turn = IStream.create(gamepad::getRightX).filtered(
            IFilter.create(x -> SLMath.deadband(x, Turn.DEADZONE.get()))
                .then(new LowPassFilter(Turn.RC))
                .then(x -> x * Turn.MAX_TURN.get()));

        addRequirements(swivel);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        swivel.setStates(speed.get(), turn.get(), true);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean wasInterrupted) {}
}
