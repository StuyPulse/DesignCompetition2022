package com.stuypulse.robot.subsystems.swivel;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.constants.Modules.ModuleConfig;
import com.stuypulse.robot.constants.Settings.Swivel;
import com.stuypulse.robot.util.EncoderSim;
import com.stuypulse.robot.util.MotorSim;

import edu.wpi.first.math.geometry.Rotation2d;

public class SimModule extends SwivelModule {

    private MotorSim driveMotor;
    private MotorSim turnMotor;

    private EncoderSim driveEncoder;
    private EncoderSim turnEncoder;


    public SimModule(ModuleConfig config) {
        super(config);

        driveMotor = new MotorSim(MotorSim.MotorType.NEO550, 1, 1);
        turnMotor = new MotorSim(MotorSim.MotorType.NEO550, 1, 1);

        driveEncoder = driveMotor.getEncoder();
        turnEncoder = turnMotor.getEncoder();
    }


    @Override
    protected void setDriveVolts(double volts) {
        driveMotor.set(volts);
    }

    @Override
    protected void setTurnVolts(double volts) {
        turnMotor.set(volts);
    }

    @Override
    protected Rotation2d getAngle() {
        return new Rotation2d(turnEncoder.getDistance());
    }

    @Override
    protected double getSpeed() {
        return driveMotor.getRadPerSecond() * (Swivel.WHEEL_DIAMETER / 2.0);
    }

    @Override
    public void simulationPeriodic() {
        driveMotor.update(Settings.DT);
        turnMotor.update(Settings.DT);
    }
    
}
