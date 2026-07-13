package org.firstinspires.ftc.teamcode.subsystems.collector;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class BasicAnswerKey {
    public static double intakePower = .9;
    public static double extakePower = -.7;
    private final DcMotorEx intakeMotor;

    public enum IntakeState {
        OFF, INTAKE, EXTAKE
    }
    private IntakeState intakeState;

    public BasicAnswerKey(HardwareMap hardwareMap) {
        // note: there's no need to store hardwareMap as instance data

        intakeMotor = hardwareMap.get(DcMotorEx.class, "intake");
        setIntakeState(IntakeState.OFF);
    }

    public void update() {
        switch(intakeState) {
            case OFF:
                intakeMotor.setPower(0);
                break;
            case INTAKE:
                intakeMotor.setPower(intakePower);
                break;
            case EXTAKE:
                intakeMotor.setPower(extakePower);
                break;
        }
    }

    public void printTelemetry(Telemetry telemetry) {
        telemetry.addLine("---INTAKE---");
        telemetry.addData("I state", intakeState);
        telemetry.addData("I power", intakeMotor.getPower());
        // YOU SHOULD NOT HAVE A "telemetry.update();" HERE
    }

    public IntakeState getIntakeState() {
        return intakeState;
    }
    public void setIntakeState(IntakeState intakeState) {
        this.intakeState = intakeState;
    }
}
