package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Methods: 
 *  - Move Up,
 *  - Move Down,
 *  - Move set distance ()
 *  - 
 */

public class Elevator extends SubsystemBase {

    private final MotorControllerGroup motors;

    private final 


    public Elevator() {
        motors = new MotorControllerGroup(new CANSparkMax(-1, MotorType.kBrushless), 
                                          new CANSparkMax(-1, MotorType.kBrushless), 
                                          new CANSparkMax(-1, MotorType.kBrushless), 
                                          new CANSparkMax(-1, MotorType.kBrushless));
        
    }

    private void move(double sped) {
        motors.set(sped);
    }

    public void moveUp(double sped) {
        move(+sped);
    }

    public void moveDown(double sped) {
        move(-sped);
    }

    public double getDistance() {

    }



}