package com.stuypulse.robot.subsystems;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.control.feedforward.Feedforward;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;

public class SimDrivetrain extends IDrivetrain {

    static final double TRACK_WIDTH = Units.inchesToMeters(30);

    private double leftVelocity = 0.0;
    private double rightVelocity = 0.0;

    private double angle = 0.0;

    private double targetLeftVelocity = 0.0;
    private double targetRightVelocity = 0.0;

    private DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(TRACK_WIDTH);

    private DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(getAngle());

    private final Controller leftController = new PIDController(0.00337, 0, 0).add(new Feedforward.Drivetrain(0.367, 2.07, 0.47).velocity());
    private final Controller rightController = new PIDController(0.00337, 0, 0).add(new Feedforward.Drivetrain(0.367, 2.07, 0.47).velocity());

    private final DifferentialDrivetrainSim drivetrainSim = new DifferentialDrivetrainSim(LinearSystemId.identifyDrivetrainSystem(2.07, 0.47, 2.07, 0.47, TRACK_WIDTH),
            DCMotor.getFalcon500(3), 8, TRACK_WIDTH, Units.inchesToMeters(3), 
            null);

    private final Field2d field = new Field2d();

    public SimDrivetrain() {
        setSubsystem("Drivetrain");
        addChild("Field2D", field);
    }

    @Override
    public Rotation2d getAngle() {
        return new Rotation2d(angle);
    }

    @Override
    public void reset(Pose2d location) {
        odometry.resetPosition(location, getAngle());
    }

    @Override
    public DifferentialDriveWheelSpeeds getSpeeds() {
        return new DifferentialDriveWheelSpeeds(leftVelocity, rightVelocity);
    }

    @Override
    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    @Override
    public void tankDrive(double leftMetersPerSecond, double rightMetersPerSecond) {
        targetLeftVelocity = leftMetersPerSecond;
        targetRightVelocity = rightMetersPerSecond;
    }

    @Override
    public void arcadeDrive(double vxMetersPerSecond, double omegaRadiansPerSecond) {
        var wheelSpeeds = kinematics.toWheelSpeeds(new ChassisSpeeds(vxMetersPerSecond, 0, omegaRadiansPerSecond));
        tankDrive(wheelSpeeds.leftMetersPerSecond, wheelSpeeds.rightMetersPerSecond);
    }

    @Override
    public void periodic() {
        drivetrainSim.setInputs(leftController.update(targetLeftVelocity, leftVelocity), rightController.update(targetRightVelocity, rightVelocity));

        System.out.println("Left: " + leftVelocity + " Right: " + rightVelocity);

        System.out.println("Left Target: " + targetLeftVelocity + " Right Target: " + targetRightVelocity);
        
        drivetrainSim.update(0.02);

        leftVelocity = drivetrainSim.getLeftVelocityMetersPerSecond();
        rightVelocity = drivetrainSim.getRightVelocityMetersPerSecond();

        var wheelSpeeds = kinematics.toChassisSpeeds(getSpeeds());
        angle += wheelSpeeds.omegaRadiansPerSecond * 0.02;

        odometry.update(getAngle(), drivetrainSim.getLeftPositionMeters(), drivetrainSim.getRightPositionMeters());

        field.setRobotPose(getPose());

        System.out.println("Left Position: " + drivetrainSim.getLeftPositionMeters() + " Right Position: " + drivetrainSim.getRightPositionMeters());
    }
}
