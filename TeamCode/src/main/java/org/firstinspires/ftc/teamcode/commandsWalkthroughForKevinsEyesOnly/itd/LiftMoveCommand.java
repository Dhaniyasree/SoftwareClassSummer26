package org.firstinspires.ftc.teamcode.commandsWalkthroughForKevinsEyesOnly.itd;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.day4Classes.Lift;

@Config
public class LiftMoveCommand extends CommandBase {
    public static double maxTime = 3;
    private final Lift lift;
    private final double liftTarget;
    private final ElapsedTime timer;

    public LiftMoveCommand(Lift lift, double liftTarget) {
        this.lift = lift;
        this.liftTarget = liftTarget;
        timer = new ElapsedTime();
        addRequirements(lift);
    }

    @Override
    public void initialize() {
        lift.setTargetPos(liftTarget);
        timer.reset();
    }
    @Override
    public boolean isFinished() {
        return lift.onTarget() || timer.seconds() > maxTime;
    }
}
