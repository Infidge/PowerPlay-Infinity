package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class Telemetry {

    Hardware robot = new Hardware();
    Outtake outtake = new Outtake();
    Intake intake = new Intake();
    Drivetrain drivetrain = new Drivetrain();

    public void buildTelemetryTeleOp(){

        telemetry.addData("Left:", Hardware.leftEncoder.getCurrentPosition());
        telemetry.addData("Right:", Hardware.rightEncoder.getCurrentPosition());
        telemetry.addData("Front:", Hardware.frontEncoder .getCurrentPosition());
        telemetry.addData("Main:", Hardware.outtakeClaw.getPosition());
        telemetry.addData("Lift1:", Hardware.slider1.getCurrentPosition());
        telemetry.addData("Lift2:", Hardware.slider2.getCurrentPosition());
        telemetry.addData("Switch1:", Hardware.limitSwitch1.getState());
        telemetry.addData("Switch2:", Hardware.limitSwitch2.getState());
        telemetry.addData("Intake Claw Angle:", intake.clawAngle);
//        telemetry.addData("Outtake Claw Angle:", outtake.clawAngle);
        telemetry.update();
    }

    public void buildTelemetryAutonomous(){

    }
}
