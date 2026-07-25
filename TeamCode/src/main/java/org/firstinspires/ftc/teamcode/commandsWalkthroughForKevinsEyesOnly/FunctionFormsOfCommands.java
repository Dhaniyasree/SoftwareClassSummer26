package org.firstinspires.ftc.teamcode.commandsWalkthroughForKevinsEyesOnly;

import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.util.InterpLUT;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.day4Classes.Collector;
import org.firstinspires.ftc.teamcode.subsystems.day4Classes.Grabber;
import org.firstinspires.ftc.teamcode.subsystems.day4Classes.Lift;
import org.firstinspires.ftc.teamcode.subsystems.day4Classes.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.day4Classes.Turret;
import org.firstinspires.ftc.teamcode.utils.command.CommandBuilder;
import org.firstinspires.ftc.teamcode.utils.drivetrain.PinpointLocalizer;

import java.util.Set;

public class FunctionFormsOfCommands {
    // normal implementation
    public static Command setLiftToTargetHeightBlockingCommandMethod2(Lift lift, double target) {
        SequentialCommandGroup command = new SequentialCommandGroup(
                new InstantCommand(() -> lift.setTargetPos(target)),
                new WaitUntilCommand(() -> lift.onTarget())
        );
        command.addRequirements(lift);
        return command;
    }
    public static Command rotateGrabber(Grabber grabber, Grabber.GrabberState grabberState) {
        SequentialCommandGroup command = new SequentialCommandGroup(
                new InstantCommand(() -> grabber.setState(grabberState)),
                new WaitCommand(500)
        );
        command.addRequirements(grabber);
        return command;
    }

    // instead of writing a full class for each command you can write an "inline declaration" like below
    // side note: this needs to be written this way and not with CommandBuilder because it has instance data
    public static Command detectCollectorJamCommand(Collector collector) {
        return new Command() {
            private final ElapsedTime timer = new ElapsedTime();
            @Override
            public Set<Subsystem> getRequirements() {
                return Set.of();
            }

            @Override
            public void initialize() {
                timer.reset();
            }
            @Override
            public void execute() {
                // logic goes here
            }
        };
    }

    public static Command aimShooterTurretToGoal(PinpointLocalizer pinpoint, Shooter shooter, Turret turret, Vector2d goal) {
        // there is no constructor, so you initialize everything in function body
        InterpLUT shooterSpeedTable = new InterpLUT();
        shooterSpeedTable.add(12, 1000);
        shooterSpeedTable.add(36, 1500);
        shooterSpeedTable.add(72, 2000);
        shooterSpeedTable.createLUT();

        return new Command() {
            @Override
            public Set<Subsystem> getRequirements() {
                return Set.of(shooter, turret);
            }

            @Override
            public void initialize() {
                shooter.setShooterState(Shooter.ShooterState.VELOCITY_CONTROL);
                turret.setTurretState(Turret.TurretState.POINT_AT_ANGLE);
            }

            @Override
            public void execute() {
                Vector2d robotPos = pinpoint.getPose().position;
                Vector2d robotToGoal = goal.minus(robotPos);

                double distToGoal = robotToGoal.norm();
                double turretTarget = robotToGoal.angleCast().toDouble() - pinpoint.getPose().heading.toDouble();

                shooter.setTargetVelocity(shooterSpeedTable.get(distToGoal));
                turret.setTargetAngle(turretTarget);
            }
        };
    }



    // for the advanced minds: justin made this custom syntax to make commands more similar to roadrunner actions
    public static Command setLiftToTargetHeightBlockingCommandMethod1(Lift lift) {
        return new CommandBuilder()
                .initialize(() -> lift.setTargetPos(500))
                .setIsFinished(() -> lift.onTarget())
                .requires(lift)
                .build();
    }
}
