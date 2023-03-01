package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Gamepad;

public class Drivetrain_Baciu {

    public Drivetrain_Baciu() {}

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

        double flspeed, frspeed, blspeed, brspeed;

        flspeed = drive + strafe + turn;
        frspeed = drive - strafe - turn;
        blspeed = drive - strafe + turn;
        brspeed = drive + strafe - turn;

        double max = Math.max(Math.max(Math.abs(flspeed), Math.abs(frspeed)), Math.max(Math.abs(blspeed), Math.abs(brspeed)));

        if (max > 1) {
            flspeed /= max;
            frspeed /= max;
            blspeed /= max;
            brspeed /= max;
        }

        Hardware.motorFl.setPower(flspeed * brake);
        Hardware.motorFr.setPower(frspeed * brake);
        Hardware.motorBl.setPower(blspeed * brake);
        Hardware.motorBr.setPower(brspeed * brake);
    }

}
