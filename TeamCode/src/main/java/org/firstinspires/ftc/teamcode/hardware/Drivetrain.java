package org.firstinspires.ftc.teamcode.hardware;

import static android.os.SystemClock.sleep;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorDigitalTouch;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Constant;
import org.firstinspires.ftc.teamcode.demoBot.Hardware_Demo;

public class Drivetrain {

    Hardware robot = new Hardware();

    public void mecanumDrive(Gamepad gamepad1) {
        double brake;
        double Drive;
        double Strafe;
        double Turn;
        double flspeed;
        double blspeed;
        double brspeed;
        double frspeed;
        double max;

        if (gamepad1.left_trigger > 0.0)
            brake = 0.3;
        else if (gamepad1.right_trigger > 0.0)
            brake = 0.5;
        else
            brake = 1.0;

        Drive = -gamepad1.left_stick_y;
        Strafe = gamepad1.left_stick_x;
        Turn = gamepad1.right_stick_x;

        flspeed = Drive + Strafe + Turn;
        blspeed = Drive - Strafe + Turn;
        brspeed = Drive + Strafe - Turn;
        frspeed = Drive - Strafe - Turn;

        max = Math.max(Math.max(Math.abs(flspeed), Math.abs(frspeed)), Math.max(Math.abs(blspeed), Math.abs(brspeed)));

        if (max > 1) {
            flspeed /= max;
            frspeed /= max;
            blspeed /= max;
            brspeed /= max;
        }

        Hardware.motorFl.setPower(flspeed);
        Hardware.motorBl.setPower(blspeed);
        Hardware.motorBr.setPower(brspeed);
        Hardware.motorFr.setPower(frspeed);
    }

    public void driveForward(double distance, int leftDif, int rightDif) {
            int flTarget, blTarget, brTarget, frTarget;
            flTarget = robot.motorFl.getCurrentPosition() + (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));
            blTarget = robot.motorBl.getCurrentPosition() + (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));
            brTarget = robot.motorBr.getCurrentPosition() + (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));
            frTarget = robot.motorFr.getCurrentPosition() + (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));

            robot.motorFl.setTargetPosition(flTarget);
            robot.motorBl.setTargetPosition(blTarget);
            robot.motorBr.setTargetPosition(brTarget);
            robot.motorFr.setTargetPosition(frTarget);

            robot.motorFl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorFr.setMode(DcMotor.RunMode.RUN_TO_POSITION);


            for (int i = 10; i <= Constant.maxVelocityDrivetrain; i+=10) {
                robot.motorFl.setVelocity(i+leftDif, AngleUnit.DEGREES);
                robot.motorBl.setVelocity(i+leftDif, AngleUnit.DEGREES);
                robot.motorBr.setVelocity(i+rightDif, AngleUnit.DEGREES);
                robot.motorFr.setVelocity(i+rightDif, AngleUnit.DEGREES);
                sleep(50);
            }

            while (robot.motorFl.isBusy() || robot.motorBl.isBusy() || robot.motorBr.isBusy() || robot.motorFr.isBusy()) {
                telemetry.addData("Current position: ", "%2d : %2d : %2d : %2d",
                        robot.motorFl.getCurrentPosition(),
                        robot.motorBl.getCurrentPosition(),
                        robot.motorBr.getCurrentPosition(),
                        robot.motorFr.getCurrentPosition());
                telemetry.update();
                if (((Hardware_Demo.motorFl.getCurrentPosition()/ Constant.ticksPerRev* Constant.wheelDiameter * 3.1415)-Hardware_Demo.motorFl.getTargetPosition()/ Constant.ticksPerRev* Constant.wheelDiameter*3.1415)>5)
                    break;
            }

            robot.motorFl.setVelocity(0, AngleUnit.DEGREES);
            robot.motorBl.setVelocity(0, AngleUnit.DEGREES);
            robot.motorBr.setVelocity(0, AngleUnit.DEGREES);
            robot.motorFr.setVelocity(0, AngleUnit.DEGREES);

            resetEncoders();
    }

    public void driveBackward(double distance, int leftDif, int rightDif) {
            int flTarget, blTarget, brTarget, frTarget;
            flTarget = robot.motorFl.getCurrentPosition() - (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));
            blTarget = robot.motorBl.getCurrentPosition() - (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));
            brTarget = robot.motorBr.getCurrentPosition() - (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));
            frTarget = robot.motorFr.getCurrentPosition() - (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));

            robot.motorFl.setTargetPosition(flTarget);
            robot.motorBl.setTargetPosition(blTarget);
            robot.motorBr.setTargetPosition(brTarget);
            robot.motorFr.setTargetPosition(frTarget);

            robot.motorFl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorFr.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            for (int i = 10; i <= Constant.maxVelocityDrivetrain; i+=10) {
                robot.motorFl.setVelocity(-i-leftDif, AngleUnit.DEGREES);
                robot.motorBl.setVelocity(-i-leftDif, AngleUnit.DEGREES);
                robot.motorBr.setVelocity(-i-rightDif, AngleUnit.DEGREES);
                robot.motorFr.setVelocity(-i-rightDif, AngleUnit.DEGREES);
                sleep(50);
            }

            while (robot.motorFl.isBusy() || robot.motorBl.isBusy() || robot.motorBr.isBusy() || robot.motorFr.isBusy()) {
                telemetry.addData("Current position: ", "%2d : %2d : %2d : %2d",
                        robot.motorFl.getCurrentPosition(),
                        robot.motorBl.getCurrentPosition(),
                        robot.motorBr.getCurrentPosition(),
                        robot.motorFr.getCurrentPosition());
                telemetry.update();
                if (((Hardware_Demo.motorFl.getCurrentPosition()/ Constant.ticksPerRev* Constant.wheelDiameter * 3.1415)-Hardware_Demo.motorFl.getTargetPosition()/ Constant.ticksPerRev* Constant.wheelDiameter*3.1415)>5)
                    break;
            }
            robot.motorFl.setVelocity(0, AngleUnit.DEGREES);
            robot.motorBl.setVelocity(0, AngleUnit.DEGREES);
            robot.motorBr.setVelocity(0, AngleUnit.DEGREES);
            robot.motorFr.setVelocity(0, AngleUnit.DEGREES);

            resetEncoders();


    }

    public void slideRight(double distance) {
            int flTarget, blTarget, brTarget, frTarget;
            flTarget = robot.motorFl.getCurrentPosition() + (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));
            blTarget = robot.motorBl.getCurrentPosition() - (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));
            brTarget = robot.motorBr.getCurrentPosition() + (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));
            frTarget = robot.motorFr.getCurrentPosition() - (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));

            robot.motorFl.setTargetPosition(flTarget);
            robot.motorBl.setTargetPosition(blTarget);
            robot.motorBr.setTargetPosition(brTarget);
            robot.motorFr.setTargetPosition(frTarget);

            robot.motorFl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorFr.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            for (int i = 10; i <= Constant.maxVelocityDrivetrain; i+=10) {
                robot.motorFl.setVelocity(i, AngleUnit.DEGREES);
                robot.motorBl.setVelocity(-i, AngleUnit.DEGREES);
                robot.motorBr.setVelocity(i, AngleUnit.DEGREES);
                robot.motorFr.setVelocity(-i, AngleUnit.DEGREES);
                sleep(50);

            }

            while (robot.motorFl.isBusy()  && robot.motorBl.isBusy()  && robot.motorBr.isBusy()  && robot.motorFr.isBusy()) {
                telemetry.addData("Current position: ", "%2d : %2d : %2d : %2d",
                        robot.motorFl.getCurrentPosition(),
                        robot.motorBl.getCurrentPosition(),
                        robot.motorBr.getCurrentPosition(),
                        robot.motorFr.getCurrentPosition());
                telemetry.update();
                if (((Hardware_Demo.motorFl.getCurrentPosition()/ Constant.ticksPerRev* Constant.wheelDiameter * 3.1415)-Hardware_Demo.motorFl.getTargetPosition()/ Constant.ticksPerRev* Constant.wheelDiameter*3.1415)>5)
                    break;
            }

            robot.motorFl.setVelocity(0, AngleUnit.DEGREES);
            robot.motorBl.setVelocity(0, AngleUnit.DEGREES);
            robot.motorBr.setVelocity(0, AngleUnit.DEGREES);
            robot.motorFr.setVelocity(0, AngleUnit.DEGREES);

            resetEncoders();
    }


    public void slideLeft(double distance) {
            int flTarget, blTarget, brTarget, frTarget;
            flTarget = robot.motorFl.getCurrentPosition() - (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));
            blTarget = robot.motorBl.getCurrentPosition() + (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));
            brTarget = robot.motorBr.getCurrentPosition() - (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));
            frTarget = robot.motorFr.getCurrentPosition() + (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));

            robot.motorFl.setTargetPosition(flTarget);
            robot.motorBl.setTargetPosition(blTarget);
            robot.motorBr.setTargetPosition(brTarget);
            robot.motorFr.setTargetPosition(frTarget);

            robot.motorFl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorFr.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            for (int i = 10; i <= Constant.maxVelocityDrivetrain; i+=10) {
                robot.motorFl.setVelocity(-i, AngleUnit.DEGREES);
                robot.motorBl.setVelocity(i, AngleUnit.DEGREES);
                robot.motorBr.setVelocity(-i, AngleUnit.DEGREES);
                robot.motorFr.setVelocity(i, AngleUnit.DEGREES);
                sleep(50);
            }

            while (robot.motorFl.isBusy() && robot.motorBl.isBusy() && robot.motorBr.isBusy() && robot.motorFr.isBusy()) {
                telemetry.addData("Current position: ", "%2d : %2d : %2d : %2d",
                        robot.motorFl.getCurrentPosition(),
                        robot.motorBl.getCurrentPosition(),
                        robot.motorBr.getCurrentPosition(),
                        robot.motorFr.getCurrentPosition());
                telemetry.update();
                if (((Hardware_Demo.motorFl.getCurrentPosition()/ Constant.ticksPerRev* Constant.wheelDiameter * 3.1415)-Hardware_Demo.motorFl.getTargetPosition()/ Constant.ticksPerRev* Constant.wheelDiameter*3.1415)>5)
                    break;
            }

            robot.motorFl.setVelocity(0, AngleUnit.DEGREES);
            robot.motorBl.setVelocity(0, AngleUnit.DEGREES);
            robot.motorBr.setVelocity(0, AngleUnit.DEGREES);
            robot.motorFr.setVelocity(0, AngleUnit.DEGREES);

            resetEncoders();
    }

    public void rotateRight(int degrees){
            int flTarget, blTarget, brTarget, frTarget;
            int distance = (int)((degrees * 2 * Constant.chassis_width * 3.1415)/360);
            flTarget = robot.motorFl.getCurrentPosition() + (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));
            blTarget = robot.motorBl.getCurrentPosition() + (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));
            brTarget = robot.motorBr.getCurrentPosition() - (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));
            frTarget = robot.motorFr.getCurrentPosition() - (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));

            robot.motorFl.setTargetPosition(flTarget);
            robot.motorBl.setTargetPosition(blTarget);
            robot.motorBr.setTargetPosition(brTarget);
            robot.motorFr.setTargetPosition(frTarget);

            robot.motorFl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorFr.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            robot.motorFl.setVelocity(Constant.maxVelocityDrivetrain);
            robot.motorBl.setVelocity(Constant.maxVelocityDrivetrain);
            robot.motorBr.setVelocity(-Constant.maxVelocityDrivetrain);
            robot.motorFr.setVelocity(-Constant.maxVelocityDrivetrain);

            while (robot.motorFl.isBusy()  || robot.motorBl.isBusy()  || robot.motorBr.isBusy()  || robot.motorFr.isBusy()) {
                telemetry.addData("Current position: ", "%2d : %2d : %2d : %2d",
                        robot.motorFl.getCurrentPosition(),
                        robot.motorBl.getCurrentPosition(),
                        robot.motorBr.getCurrentPosition(),
                        robot.motorFr.getCurrentPosition());
                telemetry.update();
            }
            robot.motorFl.setVelocity(0);
            robot.motorBl.setVelocity(0);
            robot.motorBr.setVelocity(0);
            robot.motorFr.setVelocity(0);

            resetEncoders();
    }

    public void rotateLeft(int degrees){
            int flTarget, blTarget, brTarget, frTarget;
            int distance = (int)((degrees * 2 * Constant.chassis_width * 3.1415)/360);
            flTarget = robot.motorFl.getCurrentPosition() - (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));
            blTarget = robot.motorBl.getCurrentPosition() - (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));
            brTarget = robot.motorBr.getCurrentPosition() + (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));
            frTarget = robot.motorFr.getCurrentPosition() + (int) ((Constant.ticksPerRev * distance) / (Constant.wheelDiameter * 3.1415));

            robot.motorFl.setTargetPosition(flTarget);
            robot.motorBl.setTargetPosition(blTarget);
            robot.motorBr.setTargetPosition(brTarget);
            robot.motorFr.setTargetPosition(frTarget);

            robot.motorFl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorFr.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            robot.motorFl.setVelocity(-Constant.maxVelocityDrivetrain);
            robot.motorBl.setVelocity(-Constant.maxVelocityDrivetrain);
            robot.motorBr.setVelocity(Constant.maxVelocityDrivetrain);
            robot.motorFr.setVelocity(Constant.maxVelocityDrivetrain);

            while (robot.motorFl.isBusy()  || robot.motorBl.isBusy()  || robot.motorBr.isBusy()  || robot.motorFr.isBusy()) {
                telemetry.addData("Current position: ", "%2d : %2d : %2d : %2d",
                        robot.motorFl.getCurrentPosition(),
                        robot.motorBl.getCurrentPosition(),
                        robot.motorBr.getCurrentPosition(),
                        robot.motorFr.getCurrentPosition());
                telemetry.update();
            }
            robot.motorFl.setVelocity(0);
            robot.motorBl.setVelocity(0);
            robot.motorBr.setVelocity(0);
            robot.motorFr.setVelocity(0);

            resetEncoders();
    }

    public void resetEncoders(){
        robot.motorFl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorBl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorBr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorFr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.motorFl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorBl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorBr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorFr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
