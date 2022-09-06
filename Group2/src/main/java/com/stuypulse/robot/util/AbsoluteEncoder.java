package com.stuypulse.robot.util;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DutyCycleEncoder;

/**
 * Wrapper class for a DutyCycleEncoder that takes in an
 * angle offset, or the forward angle of the absolute
 * encoder. The class also provides helper functions to read
 * angle from the encoder.
 * 
 * @author Ben Goldfisher
 */
public class AbsoluteEncoder implements Sendable {
	
	private final DutyCycleEncoder encoder;
	
	private Rotation2d offset;

	public AbsoluteEncoder(int channel, Rotation2d offset) {
		encoder = new DutyCycleEncoder(channel);
		this.offset = offset;
	}

	private double rotationsToRadians(double rotations) {
		return MathUtil.interpolate(-Math.PI, +Math.PI, rotations);
	}

	public void setOffset(Rotation2d offset) {
		this.offset = offset;
	}

	public Rotation2d getOffset() {
		return offset;
	}

	public Rotation2d getAngle() {
		return new Rotation2d(rotationsToRadians(encoder.getAbsolutePosition()))
			.minus(offset);
	}

	// make the current angle face 'forward'
	public void reset() {
		setOffset(new Rotation2d(rotationsToRadians(encoder.getAbsolutePosition())));
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		encoder.initSendable(builder);
	}
	
}
