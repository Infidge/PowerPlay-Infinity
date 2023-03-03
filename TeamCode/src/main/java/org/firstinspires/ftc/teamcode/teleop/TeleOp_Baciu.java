package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.Drivetrain_Baciu;
import org.firstinspires.ftc.teamcode.hardware.Hardware_Baciu;
import org.firstinspires.ftc.teamcode.hardware.IntakeEnums.IntakeArmAngle;
import org.firstinspires.ftc.teamcode.hardware.IntakeEnums.IntakeClawAngle;
import org.firstinspires.ftc.teamcode.hardware.Intake_Baciu;
import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeArmAngle;
import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeClawState;
import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeSlidersPosition;
import org.firstinspires.ftc.teamcode.hardware.Outtake_Baciu;

@TeleOp(name = "TeleOp_Baciu", group = "Baciu")
public class TeleOp_Baciu extends LinearOpMode {

    private final Drivetrain_Baciu drivetrain = new Drivetrain_Baciu();
    private final Intake_Baciu intake = new Intake_Baciu();
    private final Outtake_Baciu outtake = new Outtake_Baciu();

    @Override
    public void runOpMode() {
        Hardware_Baciu.init(hardwareMap);

        if (Hardware_Baciu.intakeSliders.getMode() != DcMotor.RunMode.RUN_TO_POSITION) terminateOpModeNow();

        intake.init();
        outtake.init();

        Gamepad.LedEffect ledEffect1 = new Gamepad.LedEffect.Builder()
                .addStep(177, 94, 224, 3000)
                .addStep(0, 0, 0, 3000)
                .setRepeating(true)
                .build();

        Gamepad.LedEffect ledEffect2 = new Gamepad.LedEffect.Builder()
                .addStep(129, 235, 108, 3000)
                .addStep(0, 0, 0, 3000)
                .setRepeating(true)
                .build();

        waitForStart();

        // TODO Package-private modifier for hardware
        // TODO Proper constants for IntakeArmAngle, IntakeClawAngle

        while (opModeIsActive()) {
            if (gamepad1.left_bumper || gamepad2.left_bumper) terminateOpModeNow();

            // Gamepad 1
            gamepad1.runLedEffect(ledEffect1);

            drivetrain.mecanumDrive(gamepad1);

            // TODO Opening and closing intake claw

            if (gamepad1.a) {
                intake.moveArm(IntakeArmAngle.PICKUP_CONE);
                intake.moveClawAngle(IntakeClawAngle.PICKUP_CONE);
            } else if (gamepad1.b) {
                intake.moveArm(IntakeArmAngle.TRANSFER_CONE);
                intake.moveClawAngle(IntakeClawAngle.TRANSFER_CONE);
            }

            if (gamepad1.x) {
                intake.retractSliders();
            }

            if (gamepad1.y) {
                intake.saveSlidersPosition();
            }

            if (gamepad1.right_bumper) {
                intake.moveSlidersToSavedPosition();
            }

            intake.tickLimitSwitch();
            intake.moveSlidersManually(gamepad1);

            // Gamepad 2
            gamepad2.runLedEffect(ledEffect2);

            if (gamepad2.left_bumper) {
                outtake.lowerSliders();
            } else if (gamepad2.dpad_down) {
                outtake.moveSliders(OuttakeSlidersPosition.LOW_JUNCTION);
            } else if (gamepad2.dpad_left || gamepad2.dpad_right) {
                outtake.moveSliders(OuttakeSlidersPosition.MID_JUNCTION);
            } else if (gamepad2.dpad_up) {
                outtake.moveSliders(OuttakeSlidersPosition.HIGH_JUNCTION);
            }

            // TODO Implement `OuttakeArmAngle.DROP_LOW_JUNCTION`
            if (gamepad2.b) {
                outtake.moveArm(OuttakeArmAngle.COLLECT);
            } else if (gamepad2.x) {
                outtake.moveArm(OuttakeArmAngle.DROP_MID_OR_HIGH_JUNCTION);
            }

            if (gamepad2.a) {
                outtake.moveClaw(OuttakeClawState.CLOSED);
            } else if (gamepad2.y) {
                outtake.moveClaw(OuttakeClawState.OPEN);
            }

            outtake.tickLimitSwitch();
            outtake.tickSliders();

            buildTelemetryData();
            telemetry.update();
        }
    }

    private void buildTelemetryData() {
        telemetry.addData("Left Encoder:", Hardware_Baciu.leftEncoder);
        telemetry.addData("Right Encoder:", Hardware_Baciu.rightEncoder);
        telemetry.addData("Front Encoder:", Hardware_Baciu.frontEncoder);
        telemetry.addData("Outtake Slider Left:", Hardware_Baciu.sliderLeft.getCurrentPosition());
        telemetry.addData("Outtake Slider Right:", Hardware_Baciu.sliderRight.getCurrentPosition());
        telemetry.addData("Outtake Switch Left:", Hardware_Baciu.limitSwitchLeft.getState());
        telemetry.addData("Outtake Switch Right:", Hardware_Baciu.limitSwitchRight.getState());
        telemetry.addData("Outtake Arm Left:", Hardware_Baciu.outtakeArmLeft.getPosition());
        telemetry.addData("Outtake Arm Right:", Hardware_Baciu.outtakeArmRight.getPosition());
        telemetry.addData("Outtake Claw:", Hardware_Baciu.outtakeClaw.getPosition());
        telemetry.addData("Outtake Claw Open?", outtake.isClawOpen());
    }

}
