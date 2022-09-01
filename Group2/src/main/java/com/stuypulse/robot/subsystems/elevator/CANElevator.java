package com.stuypulse.robot.subsystems.elevator;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class CANElevator extends Elevator {
    
    private final MotorControllerGroup motors;
    private final Encoder greyhill;

    private final DigitalInput top;
    private final DigitalInput bottom;

    public CANElevator() {
        motors = new MotorControllerGroup(
            new CANSparkMax(Ports.Elevator.FIRST_MOTOR, MotorType.kBrushless), 
            new CANSparkMax(Ports.Elevator.SECOND_MOTOR, MotorType.kBrushless));

        greyhill = new Encoder(Ports.Elevator.GREYHILL_A, Ports.Elevator.GREYHILL_B);
        greyhill.setDistancePerPulse(Math.PI * Settings.Elevator.OUTPUT_DIAMETER / Settings.Elevator.PULSES_PER_ROTATE);
        
        top = new DigitalInput(Ports.Elevator.TOP_LIMIT);
        bottom = new DigitalInput(Ports.Elevator.BOTTOM_LIMIT);
    }

    /*** MOTOR CONTROL ***/
    
    @Override
    public void move(double speed) {
        motors.set(speed);
    }

    @Override
    public void setMotorStop() {
        motors.set(0);
    }

    /*** ENCODER CONTROL ***/

    @Override
    public double getDistance() {
        return greyhill.get();
    }

    @Override
    public void resetEncoder() {
        greyhill.reset();
    }

    /*** LIMIT SWITCH CONTROL ***/
    
    @Override
    public boolean getTopLimitReached() {
        return top.get();
    }

    @Override
    public boolean getBottomLimitReached() {
        return bottom.get();
    }

}
