package com.stuypulse.robot.subsystems.intake;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.robot.util.EncoderSim;
import com.stuypulse.robot.util.MotorSim;
import com.stuypulse.robot.util.MotorSim.MotorType;
import com.stuypulse.stuylib.math.Angle;

public class SimIntake extends Intake {

    private final MotorSim deployMotor;
    private final MotorSim driveMotor;

    private final EncoderSim deployEncoder;

    public SimIntake() {
        deployMotor = new MotorSim(MotorType.NEO550, Settings.Intake.DEPLOY_GEARING);
        driveMotor = new MotorSim(MotorType.NEO550, 2, 1);

        deployEncoder = deployMotor.getEncoder();
    }

    @Override
    public void set(double speed) {
        driveMotor.set(speed);
    }

    @Override
    public double get() {
        return driveMotor.get();
    }

    @Override
    public void stop() {
        driveMotor.stopMotor();
    }

    @Override
    public void setDeploy(double speed) {
        deployMotor.set(speed);
    }

    @Override
    public Angle getAngle() {
        return Angle.fromRadians(deployEncoder.getDistance());
    }

    @Override
    public void simulationPeriodic() {
        deployMotor.update(Settings.DT);
        driveMotor.update(Settings.DT);
    }
    
}
