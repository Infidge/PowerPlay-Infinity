package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.robotcore.internal.network.WifiStartStoppable;
import org.firstinspires.ftc.teamcode.Constant;

public class Hardware_Baciu {

    public static DcMotorEx motorFl;
    public static DcMotorEx motorBl;
    public static DcMotorEx motorBr;
    public static DcMotorEx motorFr;

//    public static DcMotorEx slider1;
//    public static DcMotorEx slider2;

//    public static DigitalChannel limitSwitch1;
//    public static DigitalChannel limitSwitch2;

//    public static Servo outtakeClaw;
//    public static Servo outtakeArmLeft;
//    public static Servo outtakeArmRight;

//    public static DcMotorEx intakeSliders;

//    public static Servo intakeClaw;
//    public static Servo intakeClawAngle;
//    public static Servo intakeArm;
//    public static RevColorSensorV3 intakeSensor;

//    public static DigitalChannel limitSwitchIntake;

    public static DcMotorEx leftEncoder;
    public static DcMotorEx rightEncoder;
    public static DcMotorEx frontEncoder;

    public static LynxModule expansionHub;

    public Hardware_Baciu() {}

    public static void init(HardwareMap hardwareMap) {
        motorFl = hardwareMap.get(DcMotorEx.class, "frontLeft");
        motorFr = hardwareMap.get(DcMotorEx.class, "frontRight");
        motorBl = hardwareMap.get(DcMotorEx.class, "backLeft");
        motorBr = hardwareMap.get(DcMotorEx.class, "backRight");

        motorFl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorFl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorFl.setPower(0.0);
        motorFr.setPower(0.0);
        motorBl.setPower(0.0);
        motorBr.setPower(0.0);

        motorFl.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFr.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBl.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBr.setDirection(DcMotorSimple.Direction.FORWARD);

//        slider1 = hardwareMap.get(DcMotorEx.class, "sliderL");
//        slider2 = hardwareMap.get(DcMotorEx.class, "sliderR");
//
//        slider1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//        slider1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        slider2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        slider1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        slider2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//        slider1.setPower(0.0);
//        slider2.setPower(0.0);
//
//        slider1.setDirection(DcMotorSimple.Direction.REVERSE);
//        slider2.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        outtakeClaw = hardwareMap.get(Servo.class, "outtakeClaw");
//        outtakeArmLeft = hardwareMap.get(Servo.class, "outtakeArmLeft");
//        outtakeArmRight = hardwareMap.get(Servo.class, "outtakeArmRight");
//
//        outtakeClaw .setPosition(Constant.closedClaw);
//        outtakeArmLeft.setPosition(0.0);
//        outtakeArmRight.setPosition(1.0);
//
//        outtakeSensor = hardwareMap.get(Rev2mDistanceSensor.class, "clawSensor");
//
//        limitSwitch1 = hardwareMap.get(DigitalChannel.class, "limitSwitch1");
//        limitSwitch2 = hardwareMap.get(DigitalChannel.class, "limitSwitch2");
//
//        intakeSliders = hardwareMap.get(DcMotorEx.class, "intake");
//
//        intakeSliders.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        intakeSliders.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//        intakeSliders.setPower(-0.2);
//
//        intakeSliders.setDirection(DcMotorSimple.Direction.FORWARD);
//
//        intakeClaw = hardwareMap.get(Servo.class, "intakeClaw");
//        intakeClawAngle = hardwareMap.get(Servo.class, "moveIntake");
//        intakeArm = hardwareMap.get(Servo.class, "intakeArm");
//
//        intakeClaw.setPosition(Constant.openIntake);
//        intakeClawAngle.setPosition(0.5);
//        intakeArm.setPosition(Constant.armDropCone);
//
//        intakeSensor = hardwareMap.get(RevColorSensorV3.class, "intakeSensor");
//
//        limitSwitchIntake = hardwareMap.get(DigitalChannel.class, "limitSwitchIntake");

        leftEncoder = hardwareMap.get(DcMotorEx.class, "backLeft");
        rightEncoder = hardwareMap.get(DcMotorEx.class, "backRight");
        frontEncoder = hardwareMap.get(DcMotorEx.class, "frontLeft");

        expansionHub = hardwareMap.get(LynxModule.class, "Expansion Hub 2");
    }

}
