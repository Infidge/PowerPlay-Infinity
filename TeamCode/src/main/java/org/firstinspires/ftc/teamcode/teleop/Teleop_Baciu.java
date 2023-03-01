package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Drivetrain_Baciu;
import org.firstinspires.ftc.teamcode.hardware.Hardware_Baciu;

@TeleOp(name = "Teleop_Baciu", group = "Baciu")
public class Teleop_Baciu extends LinearOpMode {

    private final Drivetrain_Baciu drivetrain = new Drivetrain_Baciu();

    @Override
    public void runOpMode() {
        Hardware_Baciu.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            drivetrain.mecanumDrive(gamepad1);
        }
    }

}
