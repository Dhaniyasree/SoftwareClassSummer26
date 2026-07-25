package org.firstinspires.ftc.teamcode.commandsWalkthroughForKevinsEyesOnly.itd;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.day4Classes.Arm;

@Config
public class ArmRotateCommand extends CommandBase {
    public static double maxTime = 2;
    private final Arm arm;
    private final double targetAngle;
    private final ElapsedTime timer;

    public ArmRotateCommand(Arm arm, double targetAngle) {
        this.arm = arm;
        this.targetAngle = targetAngle;
        timer = new ElapsedTime();
        addRequirements(arm);
    }

    @Override
    public void initialize() {
        arm.setTargetAngle(targetAngle);
        timer.reset();
    }

    @Override
    public boolean isFinished() {
        return arm.onTarget() || timer.seconds() > maxTime;
    }
}
