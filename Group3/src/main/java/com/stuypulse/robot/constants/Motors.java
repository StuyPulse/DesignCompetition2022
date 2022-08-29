/************************ PROJECT PHIL ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

/*-
 * File containing all of the configurations that different motors require.
 *
 * Such configurations include:
 *  - If it is Inverted
 *  - The Idle Mode of the Motor
 *  - The Current Limit
 *  - The Open Loop Ramp Rate
 */
public interface Motors {

    public interface Swerve {
        public interface FrontRight {
            TFXConfig DRIVE = new TFXConfig(TalonFXInvertType.Clockwise, NeutralMode.Brake, 40, 1 / 10);
            TFXConfig TURN = new TFXConfig(TalonFXInvertType.Clockwise, NeutralMode.Brake, 40, 1 / 10);
        }

        public interface FrontLeft {
            TFXConfig DRIVE = new TFXConfig(TalonFXInvertType.Clockwise, NeutralMode.Brake, 40, 1 / 10);
            TFXConfig TURN = new TFXConfig(TalonFXInvertType.Clockwise, NeutralMode.Brake, 40, 1 / 10);
        }

        public interface BackLeft {
            TFXConfig DRIVE = new TFXConfig(TalonFXInvertType.Clockwise, NeutralMode.Brake, 40, 1 / 10);
            TFXConfig TURN = new TFXConfig(TalonFXInvertType.Clockwise, NeutralMode.Brake, 40, 1 / 10);
        }

        public interface BackRight {
            TFXConfig DRIVE = new TFXConfig(TalonFXInvertType.Clockwise, NeutralMode.Brake, 40, 1 / 10);
            TFXConfig TURN = new TFXConfig(TalonFXInvertType.Clockwise, NeutralMode.Brake, 40, 1 / 10);
        }
    }

    public static class SMConfig {
        public final boolean INVERTED;
        public final IdleMode IDLE_MODE;
        public final int CURRENT_LIMIT_AMPS;
        public final double OPEN_LOOP_RAMP_RATE;

        public SMConfig(
                boolean inverted,
                IdleMode idleMode,
                int currentLimitAmps,
                double openLoopRampRate) {
            this.INVERTED = inverted;
            this.IDLE_MODE = idleMode;
            this.CURRENT_LIMIT_AMPS = currentLimitAmps;
            this.OPEN_LOOP_RAMP_RATE = openLoopRampRate;
        }

        public SMConfig(boolean inverted, IdleMode idleMode, int currentLimitAmps) {
            this(inverted, idleMode, currentLimitAmps, 0.0);
        }

        public SMConfig(boolean inverted, IdleMode idleMode) {
            this(inverted, idleMode, 80);
        }

        public void configure(CANSparkMax motor) {
            motor.setInverted(INVERTED);
            motor.setIdleMode(IDLE_MODE);
            motor.setSmartCurrentLimit(CURRENT_LIMIT_AMPS);
            motor.setOpenLoopRampRate(OPEN_LOOP_RAMP_RATE);
            motor.burnFlash();
        }
    }

    public static class TFXConfig {
        public final TalonFXInvertType INVERTED;
        public final NeutralMode IDLE_MODE;
        public final int CURRENT_LIMIT_AMPS;
        public final double OPEN_LOOP_RAMP_RATE;

        public TFXConfig(
                TalonFXInvertType inverted,
                NeutralMode idleMode,
                int currentLimitAmps,
                double openLoopRampRate) {
            this.INVERTED = inverted;
            this.IDLE_MODE = idleMode;
            this.CURRENT_LIMIT_AMPS = currentLimitAmps;
            this.OPEN_LOOP_RAMP_RATE = openLoopRampRate;
        }

        public TFXConfig(TalonFXInvertType inverted, NeutralMode idleMode, int currentLimitAmps) {
            this(inverted, idleMode, currentLimitAmps, 0.0);
        }

        public TFXConfig(TalonFXInvertType inverted, NeutralMode idleMode) {
            this(inverted, idleMode, 80);
        }

        public void configure(TalonFX motor) {
            motor.setInverted(INVERTED);
            motor.setNeutralMode(IDLE_MODE);
            motor.configSupplyCurrentLimit(
                new SupplyCurrentLimitConfiguration(
                    true, 
                    CURRENT_LIMIT_AMPS - 10, 
                    CURRENT_LIMIT_AMPS, 
                    1
                ));
            motor.configOpenloopRamp(OPEN_LOOP_RAMP_RATE);
        }
    }
}
