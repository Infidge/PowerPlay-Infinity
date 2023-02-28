package org.firstinspires.ftc.teamcode.teleop;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Constant;
import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Outtake;
import org.firstinspires.ftc.teamcode.hardware.Telemetry;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp_Red", group="Demo")

public class TeleOp_Red extends LinearOpMode {

    Hardware robot = new Hardware();
    Intake intake = new Intake();
    Outtake outtake = new Outtake();
    Drivetrain drivetrain = new Drivetrain();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        waitForStart();
        Hardware.intakeSliders.setPower(0.0);
        while (opModeIsActive()) {
            /** GAMEPAD 1 */
            drivetrain.mecanumDrive(gamepad1);

            if (gamepad1.x || gamepad1.y || gamepad1.b) {
                intake.selectArmPreset(gamepad1);
                intake.armGoToPreset();
                intake.angleGoToPreset();
            }
            //intake.lockSLiders(gamepad1);
            intake.toggleClaw(gamepad1);
            intake.checkForCone(outtake.open);
            //intake.slidersResetEncoder();
            /*if (gamepad1.dpad_left || gamepad1.dpad_right)
                intake.moveSlidersManually(gamepad1);*/
            if (gamepad2.b || gamepad2.x)
                outtake.moveArm(gamepad2);
            outtake.toggleClaw(gamepad2);
            if (gamepad2.dpad_up || gamepad2.dpad_left || gamepad2.dpad_right || gamepad2.dpad_down)
                outtake.selectSliderPreset(gamepad2);
            if (gamepad2.left_bumper)
                outtake.lowerSliders(gamepad2);
            outtake.resetEncoder();
            outtake.checkForJunction();
            outtake.moveSlidersPreset();


            /** Telemetry */
            telemetry.addData("Left:", Hardware.leftEncoder.getCurrentPosition());
            telemetry.addData("Right:", Hardware.rightEncoder.getCurrentPosition());
            telemetry.addData("Front:", Hardware.frontEncoder .getCurrentPosition());
            telemetry.addData("Main:", Hardware.outtakeClaw.getPosition());
            telemetry.addData("Arm", Hardware.intakeArm.getPosition());
            telemetry.addData("Lift1:", Hardware.slider1.getCurrentPosition());
            telemetry.addData("LiftP:", Hardware.slider1.getPower());
            telemetry.addData("Switch1:", Hardware.limitSwitch1.getState());
            telemetry.addData("Switch2:", Hardware.limitSwitch2.getState());
            telemetry.addData("Arm Pos:", intake.armPosition);
            telemetry.addData("Intake Claw Angle:", intake.clawAngle);
            telemetry.addData("Outtake Arm:", outtake.armPos);
            telemetry.addData("intake sliders", Hardware.intakeSliders.getCurrentPosition());
            telemetry.update();
        }
    }
}