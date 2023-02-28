package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Constant;
import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Outtake;
import org.firstinspires.ftc.teamcode.hardware.Telemetry;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp_Blue", group="Demo")

public class TeleOp_Blue extends LinearOpMode {

    Hardware robot = new Hardware();
    Intake intake = new Intake();
    Outtake outtake = new Outtake();
    Drivetrain drivetrain = new Drivetrain();
    Telemetry telemetry = new Telemetry();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {

            /** Telemetry */
            telemetry.buildTelemetryTeleOp();
        }
    }
}