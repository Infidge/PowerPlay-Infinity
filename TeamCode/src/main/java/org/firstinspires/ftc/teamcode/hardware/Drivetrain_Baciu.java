package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Gamepad;

public class Drivetrain_Baciu {

    public void mecanumDrive(Gamepad gamepad) {
        double drive, strafe, turn, brake;

        if (gamepad.left_trigger > 0.0) {
            brake = 0.3;
        } else if (gamepad.right_trigger > 0.0) {
            brake = 0.5;
        } else {
            brake = 1.0;
        }

        drive = -gamepad.left_stick_y;
        strafe = gamepad.left_stick_x;
        turn = gamepad.right_stick_x;

        double flSpeed, frSpeed, blSpeed, brSpeed;

        flSpeed = drive + strafe + turn;
        frSpeed = drive - strafe - turn;
        blSpeed = drive - strafe + turn;
        brSpeed = drive + strafe - turn;

        double max = Math.max(Math.max(Math.abs(flSpeed), Math.abs(frSpeed)), Math.max(Math.abs(blSpeed), Math.abs(brSpeed)));

        if (max > 1) {
            flSpeed /= max;
            frSpeed /= max;
            blSpeed /= max;
            brSpeed /= max;
        }

        Hardware_Baciu.motorFl.setPower(flSpeed * brake);
        Hardware_Baciu.motorFr.setPower(frSpeed * brake);
        Hardware_Baciu.motorBl.setPower(blSpeed * brake);
        Hardware_Baciu.motorBr.setPower(brSpeed * brake);
    }

}
