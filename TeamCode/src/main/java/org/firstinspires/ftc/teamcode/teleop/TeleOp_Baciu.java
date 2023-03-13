package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Drivetrain_Baciu;
import org.firstinspires.ftc.teamcode.hardware.Hardware_Baciu;
import org.firstinspires.ftc.teamcode.hardware.Intake_Baciu;
import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeArmAngle;
import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeClawPosition;
import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeSlidersPosition;
import org.firstinspires.ftc.teamcode.hardware.Outtake_Baciu;

@TeleOp(name = "TeleOp_Baciu", group = "Baciu")
public class TeleOp_Baciu extends LinearOpMode {

    private final Drivetrain_Baciu drivetrain = new Drivetrain_Baciu();
    private final Intake_Baciu intake = new Intake_Baciu();
    private final Outtake_Baciu outtake = new Outtake_Baciu();

    @Override
    public void runOpMode() throws InterruptedException {
        Hardware_Baciu.init(hardwareMap);

//        intake.init();
//        outtake.init();

        waitForStart();

        gamepad1.setLedColor(177, 94, 224, 30000);
        gamepad2.setLedColor(129, 235, 108, 30000);

        // TODO constants for IntakeArmAngle, IntakeClawAngle (update in Intake class)

        while (opModeIsActive()) {
            // Gamepad 1
            drivetrain.mecanumDrive(gamepad1);

//            if (gamepad1.dpad_left) {
//                intake.moveSliders(IntakeSlidersPower.RETRACT);
//            } else if (gamepad1.dpad_right) {
//                intake.moveSliders(IntakeSlidersPower.EXTEND);
//            }
//
//            if (gamepad1.a) {
//                intake.moveArm(IntakeArmAngle.PICKUP_CONE);
//                intake.moveClawAngle(IntakeClawAngle.PICKUP_CONE);
//            } else if (gamepad1.b) {
//                intake.moveArm(IntakeArmAngle.TRANSFER_CONE);
//                intake.moveClawAngle(IntakeClawAngle.TRANSFER_CONE);
//            }

//            if (gamepad1.a) {
//                intake.moveClaw(IntakeClawState.OPEN);
//            } else if (gamepad1.b) {
//                intake.moveClaw(IntakeClawState.OPEN);
//            }

//            if (gamepad1.x) {
//                intake.retractSliders();
//            }
//
//            if (gamepad1.left_bumper) {
//                intake.saveSlidersPosition();
//            } else if (gamepad1.right_bumper) {
//                intake.moveSlidersToSavedPosition();
//            }
//
//            intake.tickLimitSwitch();
//            intake.tickSliders();

            // Gamepad 2
            if (gamepad2.left_bumper) {
                outtake.lowerSliders();
            } else if (gamepad2.dpad_down) {
                outtake.moveSliders(OuttakeSlidersPosition.LOW_JUNCTION);
            } else if (gamepad2.dpad_left || gamepad2.dpad_right) {
                outtake.moveSliders(OuttakeSlidersPosition.MID_JUNCTION);
            } else if (gamepad2.dpad_up) {
                outtake.moveSliders(OuttakeSlidersPosition.HIGH_JUNCTION);
            }

            if (gamepad2.x) {
                outtake.moveArm(OuttakeArmAngle.COLLECT_CONE);
            } else if (gamepad2.y) {
                outtake.moveArm(OuttakeArmAngle.DROP_MID_OR_HIGH_JUNCTION);
            }

            if (gamepad2.a) {
                outtake.moveClaw(OuttakeClawPosition.CLOSED);
            } else if (gamepad2.b) {
                outtake.moveClaw(OuttakeClawPosition.OPEN);
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
//        telemetry.addData("Intake Sliders:", Hardware_Baciu.intakeSliders.getCurrentPosition());
        telemetry.addData("Intake Switch:", Hardware_Baciu.intakeLimitSwitch.getState());
//        telemetry.addData("Intake Arm:", Hardware_Baciu.intakeArm.getPosition());
//        telemetry.addData("Intake Claw Angle:", Hardware_Baciu.intakeClawAngle.getPosition());
//        telemetry.addData("Intake Claw:", Hardware_Baciu.intakeClaw.getPosition());
        telemetry.addData("Outtake Slider Left:", Hardware_Baciu.sliderLeft.getCurrentPosition());
        telemetry.addData("Outtake Slider Right:", Hardware_Baciu.sliderRight.getCurrentPosition());
        telemetry.addData("Outtake Switch Left:", Hardware_Baciu.limitSwitchLeft.getState());
        telemetry.addData("Outtake Switch Right:", Hardware_Baciu.limitSwitchRight.getState());
        telemetry.addData("Outtake Arm Left:", Hardware_Baciu.outtakeArmLeft.getPosition());
        telemetry.addData("Outtake Arm Right:", Hardware_Baciu.outtakeArmRight.getPosition());
        telemetry.addData("Outtake Claw:", Hardware_Baciu.outtakeClaw.getPosition());
        telemetry.addData("Outtake Claw Open:", outtake.isClawOpen());
        telemetry.addData("Touchpad X: ", gamepad1.touchpad_finger_1_x);
        telemetry.addData("Touchpad Y: ", gamepad1.touchpad_finger_1_y);
    }

}
