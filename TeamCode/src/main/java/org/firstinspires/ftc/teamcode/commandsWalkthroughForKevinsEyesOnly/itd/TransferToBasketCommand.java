package org.firstinspires.ftc.teamcode.commandsWalkthroughForKevinsEyesOnly.itd;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.subsystems.day4Classes.Arm;
import org.firstinspires.ftc.teamcode.subsystems.day4Classes.Grabber;
import org.firstinspires.ftc.teamcode.subsystems.day4Classes.Lift;

@Config
public class TransferToBasketCommand extends SequentialCommandGroup {
    public static double liftTransferPos = 0, liftBasketPos = 200, liftMinPosFromTransfer = 50;
    public static double armBasketAngle = Math.toRadians(100);
    public TransferToBasketCommand(Lift lift, Arm arm, Grabber grabber) {
        addCommands(
                new LiftMoveCommand(lift, liftTransferPos),
                new GrabberRotateCommand(grabber, Grabber.GrabberState.CLOSED),
                new ParallelCommandGroup(
                        new LiftMoveCommand(lift, liftBasketPos),
                        new SequentialCommandGroup(
                                new WaitUntilCommand(() -> lift.getCurrentPos() > liftMinPosFromTransfer),
                                new ArmRotateCommand(arm, armBasketAngle)
                        )
                )
        );
    }
}
