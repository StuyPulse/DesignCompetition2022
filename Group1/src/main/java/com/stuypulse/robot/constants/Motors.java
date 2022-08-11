/************************ PROJECT PHIL ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

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
}
