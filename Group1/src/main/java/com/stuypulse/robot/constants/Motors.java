/************************ PROJECT PHIL ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
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
    public interface Drivetrain {
        CANSparkMaxConfig left = new CANSparkMaxConfig(true, IdleMode.kBrake, 60, 0);
        CANSparkMaxConfig right = new CANSparkMaxConfig(false, IdleMode.kBrake, 60, 0);
    }
    
    public interface Elevator {
        TalonSRXConfig elevator = new TalonSRXConfig(false, NeutralMode.Brake, 30, 0);
    }

    public static class CANSparkMaxConfig {
        boolean inverted;
        IdleMode idleMode;
        int currentLimit;
        double rampRate;

        public CANSparkMaxConfig(boolean inverted, IdleMode idleMode, int currentLimit, double rampRate) {
            this.inverted = inverted;
            this.idleMode = idleMode;
            this.currentLimit = currentLimit;
            this.rampRate = rampRate;
        }

        public void configure(CANSparkMax... motors) {
            for (CANSparkMax motor : motors) {
                motor.setInverted(inverted);
                motor.setIdleMode(idleMode);
                motor.setSmartCurrentLimit(currentLimit);
                motor.setClosedLoopRampRate(rampRate);
            }
        }
    }

    public static class TalonSRXConfig {
        boolean inverted;
        NeutralMode neutralMode;
        int currentLimit;
        double rampRate;

        public TalonSRXConfig(boolean inverted, NeutralMode neutralMode, int currentLimit, double rampRate) {
            this.inverted = inverted;
            this.neutralMode = neutralMode;
            this.currentLimit = currentLimit;
            this.rampRate = rampRate;
        }

        public void configure(WPI_TalonSRX... motors) {
            for (WPI_TalonSRX motor : motors) {
                motor.setInverted(inverted);
                motor.setNeutralMode(neutralMode);
                motor.configContinuousCurrentLimit(currentLimit - 10, 0);
                motor.configPeakCurrentLimit(currentLimit, 0);
                motor.configPeakCurrentDuration(100, 0);
                motor.configOpenloopRamp(rampRate);
            }
        }
    }
}
