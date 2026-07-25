package org.firstinspires.ftc.teamcode.commandsWalkthroughForKevinsEyesOnly;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.day4Classes.Collector;

@Config
public class JamDetectionCommandFinished extends CommandBase {
    public static double jamCurrentThreshold = 5; // measured in amps
    public static double extakeTimeAfterCurrentSpike = .2;
    private final Collector collector;

    private final ElapsedTime timer;

    private Collector.IntakeState initialState;

    public JamDetectionCommandFinished(Collector collector) {
        this.collector = collector;
        timer = new ElapsedTime();
    }

    @Override
    public void initialize() {
        initialState = collector.getIntakeState();
    }

    @Override
    public void execute() {
        if(collector.getMotorCurrent() > jamCurrentThreshold) {
            collector.setIntakeState(Collector.IntakeState.OUTTAKE);
            timer.reset();
        }
        else
            collector.setIntakeState(Collector.IntakeState.OUTTAKE);
    }

    @Override
    public void end(boolean interrupted) {
        collector.setIntakeState(initialState);
    }

    @Override
    public boolean isFinished() {
        return timer.seconds() > extakeTimeAfterCurrentSpike;
    }
}
