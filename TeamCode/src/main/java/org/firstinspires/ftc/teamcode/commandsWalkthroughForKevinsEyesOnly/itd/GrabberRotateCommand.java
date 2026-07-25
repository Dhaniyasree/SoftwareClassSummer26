package org.firstinspires.ftc.teamcode.commandsWalkthroughForKevinsEyesOnly.itd;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.day4Classes.Grabber;

public class GrabberRotateCommand extends CommandBase {
    public static double rotateTime = .5;
    private final Grabber grabber;
    private final Grabber.GrabberState grabberState;
    private final ElapsedTime timer;


    public GrabberRotateCommand(Grabber grabber, Grabber.GrabberState grabberState) {
        this.grabber = grabber;
        this.grabberState = grabberState;
        timer = new ElapsedTime();
        addRequirements(grabber);
    }

    @Override
    public void initialize() {
        grabber.setState(grabberState);
        timer.reset();
    }

    @Override
    public boolean isFinished() {
        return timer.seconds() > rotateTime;
    }
}
