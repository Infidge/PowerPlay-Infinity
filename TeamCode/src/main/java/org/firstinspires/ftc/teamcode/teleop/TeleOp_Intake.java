package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.Drivetrain_Baciu;
import org.firstinspires.ftc.teamcode.hardware.Hardware_Baciu;
import org.firstinspires.ftc.teamcode.hardware.IntakeEnums.IntakeClawState;
import org.firstinspires.ftc.teamcode.hardware.Intake_Baciu;

@TeleOp(name = "TeleOp_Intake", group = "Baciu")
public class TeleOp_Intake extends LinearOpMode {

    private final Drivetrain_Baciu drivetrain = new Drivetrain_Baciu();
    private final Intake_Baciu intake = new Intake_Baciu();

    @Override
    public void runOpMode() throws InterruptedException {
        Hardware_Baciu.init(hardwareMap);
        intake.init();

        Gamepad.LedEffect ledEffect1 = new Gamepad.LedEffect.Builder()
                .addStep(177, 94, 224, 2000)
                .addStep(0, 0, 0, 2000)
                .setRepeating(true)
                .build();

        Gamepad.LedEffect ledEffect2 = new Gamepad.LedEffect.Builder()
                .addStep(129, 235, 108, 2000)
                .addStep(0, 0, 0, 2000)
                .setRepeating(true)
                .build();

        waitForStart();

        gamepad1.runLedEffect(ledEffect1);
        gamepad2.runLedEffect(ledEffect2);

        while (opModeIsActive()) {
            if (gamepad2.right_trigger > 0.0) terminateOpModeNow();

            // Gamepad 1
            drivetrain.mecanumDrive(gamepad1);

            // Gamepad 2
            intake.moveSlidersManually(gamepad2);
            intake.moveArmManually(gamepad2);
            intake.moveClawAngleManually(gamepad2);

            if (gamepad2.left_bumper) {
                intake.moveClaw(IntakeClawState.OPEN);
            } else if (gamepad2.right_bumper) {
                intake.moveClaw(IntakeClawState.CLOSED);
            }

            buildTelemetryData();
            telemetry.update();
        }
    }

    private void buildTelemetryData() {
        telemetry.addData("Left Encoder:", Hardware_Baciu.leftEncoder);
        telemetry.addData("Right Encoder:", Hardware_Baciu.rightEncoder);
        telemetry.addData("Front Encoder:", Hardware_Baciu.frontEncoder);
        telemetry.addData("Intake Sliders:", Hardware_Baciu.intakeSliders.getCurrentPosition());
        telemetry.addData("Intake Switch:", Hardware_Baciu.intakeLimitSwitch.getState());
        telemetry.addData("Intake Arm:", Hardware_Baciu.intakeArm.getPosition());
        telemetry.addData("Intake Claw Angle:", Hardware_Baciu.intakeClawAngle.getPosition());
        telemetry.addData("Intake Claw:", Hardware_Baciu.intakeClaw.getPosition());
    }

}
