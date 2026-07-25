package org.firstinspires.ftc.teamcode.commandsWalkthroughForKevinsEyesOnly;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Command Auto")
public class CommandAutoFinished extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        // if you are using Roadrunner or PIDDrive:
        Action pidDriveAction = null;
        Command preloadDrive = new CommandBase() {
            @Override
            public boolean isFinished() {
                return !pidDriveAction.run(new TelemetryPacket());
            }
        };
        Command secondSpike = null;

        CommandScheduler.getInstance().reset();

        CommandScheduler.getInstance().schedule(
                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                preloadDrive,
                                new ParallelCommandGroup(
                                        secondSpike
                                        // insert collector intake command here
                                )
                                // continue auto sequence below
                        )
                        // insert shooting system aim to goal command here
                )
        );

        waitForStart();

        while(opModeIsActive()) {
            // if you are using Pedro:
            // follower.update();

            CommandScheduler.getInstance().run();
            // TODO: update robot below
        }
    }
}
