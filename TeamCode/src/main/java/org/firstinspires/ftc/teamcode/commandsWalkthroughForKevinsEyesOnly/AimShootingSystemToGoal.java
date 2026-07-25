package org.firstinspires.ftc.teamcode.commandsWalkthroughForKevinsEyesOnly;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.InterpLUT;

import org.firstinspires.ftc.teamcode.subsystems.day4Classes.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.day4Classes.Turret;
import org.firstinspires.ftc.teamcode.utils.drivetrain.PinpointLocalizer;

@Config
public class AimShootingSystemToGoal extends CommandBase {
    public static double redGoalX = 0, redGoalY = 0;
    public static double blueGoalX = 0, blueGoalY = 0;

    private final InterpLUT shooterLookup;

    private final boolean isRed;
    private final Turret turret;
    private final Shooter shooter;
    private final PinpointLocalizer pinpoint;

    public AimShootingSystemToGoal(boolean isRed, Turret turret, Shooter shooter, PinpointLocalizer pinpoint) {
        this.isRed = isRed;
        this.turret = turret;
        this.shooter = shooter;
        this.pinpoint = pinpoint;
        addRequirements(turret, shooter);

        shooterLookup = new InterpLUT();
        shooterLookup.add(0, 0);
        shooterLookup.add(1, 1);
        shooterLookup.add(2, 2);
        shooterLookup.add(3, 3);
        shooterLookup.createLUT();
    }

    @Override
    public void initialize() {
        turret.setTurretState(Turret.TurretState.POINT_AT_ANGLE);
        shooter.setShooterState(Shooter.ShooterState.VELOCITY_CONTROL);
    }

    @Override
    public void execute() {
        Vector2d goal;
        if(isRed)
            goal = new Vector2d(redGoalX, redGoalY);
        else
            goal = new Vector2d(blueGoalX, blueGoalY);

        Pose2d robotPose = pinpoint.getPose();
        Vector2d robotToGoal = goal.minus(robotPose.position);

        // calculating target turret angle
        double turretTarget = robotToGoal.angleCast().toDouble() - robotPose.heading.toDouble();
        turret.setTargetAngle(turretTarget);

        // calculating target shooter speed
        double distFromGoal = robotToGoal.norm();
        double shooterTarget = shooterLookup.get(distFromGoal);
        shooter.setTargetVelocity(shooterTarget);
    }
}
