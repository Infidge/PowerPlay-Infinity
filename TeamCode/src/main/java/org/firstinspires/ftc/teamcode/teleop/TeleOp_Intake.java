package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Drivetrain_Baciu;
import org.firstinspires.ftc.teamcode.hardware.Hardware_Baciu;
import org.firstinspires.ftc.teamcode.hardware.IntakeEnums.IntakeClawPosition;
import org.firstinspires.ftc.teamcode.hardware.Intake_Baciu;

@TeleOp(name = "TeleOp_Intake", group = "Baciu")
public class TeleOp_Intake extends LinearOpMode {

    private final Drivetrain_Baciu drivetrain = new Drivetrain_Baciu();
    private final Intake_Baciu intake = new Intake_Baciu();

    @Override
    public void runOpMode() throws InterruptedException {
        Hardware_Baciu.init(hardwareMap);
        intake.init();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad2.right_trigger > 0.0) terminateOpModeNow();

            // Gamepad 1
            drivetrain.mecanumDrive(gamepad1);

            // Gamepad 2
            intake.moveSlidersManually(gamepad2);
            intake.moveArmManually(gamepad2);
            intake.moveClawAngleManually(gamepad2);

            if (gamepad2.left_bumper) {
                intake.moveClaw(IntakeClawPosition.OPEN);
            } else if (gamepad2.right_bumper) {
                intake.moveClaw(IntakeClawPosition.CLOSED);
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
