package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Drivetrain_Baciu;
import org.firstinspires.ftc.teamcode.hardware.Hardware_Baciu;
import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeArm;
import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeClaw;
import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeSlidersPosition;
import org.firstinspires.ftc.teamcode.hardware.Outtake_Baciu;

@TeleOp(name = "Teleop_Baciu", group = "Baciu")
public class TeleOp_Baciu extends LinearOpMode {

    private final Drivetrain_Baciu drivetrain = new Drivetrain_Baciu();
    private final Outtake_Baciu outtake = new Outtake_Baciu();

    @Override
    public void runOpMode() {
        Hardware_Baciu.init(hardwareMap);
        outtake.init();

        waitForStart();

        while (opModeIsActive()) {
            // Gamepad 1
            drivetrain.mecanumDrive(gamepad1);

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

            if (gamepad2.b) {
                outtake.moveArm(OuttakeArm.COLLECT);
            } else if (gamepad2.x) {
                outtake.moveArm(OuttakeArm.DROP);
            }

            if (gamepad2.a) {
                outtake.moveClaw(OuttakeClaw.CLOSED);
            } else if (gamepad2.y) {
                outtake.moveClaw(OuttakeClaw.OPEN);
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
