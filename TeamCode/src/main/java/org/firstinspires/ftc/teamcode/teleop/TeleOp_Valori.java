package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Outtake;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp_Test", group="Demo")

public class TeleOp_Valori extends LinearOpMode {

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

            intake.moveArmManually(gamepad1);
            intake.moveAngle(gamepad1);
            intake.angleGoToPreset();
            intake.armGoToPreset();
            intake.toggleClaw(gamepad1);
            //intake.manualLock(gamepad1);
          //  intake.lockSLiders(gamepad1);

            /** GAMEPAD 2 */

            outtake.moveArmManually(gamepad2);
            outtake.toggleClaw(gamepad2);
            Hardware.outtakeArmLeft.setPosition(outtake.armPos);
            Hardware.outtakeArmRight.setPosition(1-outtake.armPos);





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
            telemetry.addData("lock", intake.lock);
            telemetry.update();
        }
    }
}