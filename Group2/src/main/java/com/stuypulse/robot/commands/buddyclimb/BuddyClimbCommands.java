package com.stuypulse.robot.commands.buddyclimb;

import com.stuypulse.robot.subsystems.buddyclimb.BuddyClimb;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class BuddyClimbCommands {
    public static InstantCommand deploy(BuddyClimb buddyClimb) {
        return new InstantCommand(buddyClimb::deploy);
    }

    public static InstantCommand retract(BuddyClimb buddyClimb) {
        return new InstantCommand(buddyClimb::retract);
    }
}
