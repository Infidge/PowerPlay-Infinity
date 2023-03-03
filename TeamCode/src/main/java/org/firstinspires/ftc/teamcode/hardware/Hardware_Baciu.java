package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hardware_Baciu {

    public static DcMotorEx motorFl;
    public static DcMotorEx motorFr;
    public static DcMotorEx motorBl;
    public static DcMotorEx motorBr;

    public static DcMotorEx sliderLeft;
    public static DcMotorEx sliderRight;

    public static DigitalChannel limitSwitchLeft;
    public static DigitalChannel limitSwitchRight;

    public static Servo outtakeArmLeft;
    public static Servo outtakeArmRight;
    public static Servo outtakeClaw;

    public static DcMotorEx intakeSliders;

    public static Servo intakeArm;
    public static Servo intakeClaw;
    public static Servo intakeClawAngle;

//    public static RevColorSensorV3 intakeSensor;

    public static DigitalChannel intakeLimitSwitch;

    public static DcMotorEx leftEncoder;
    public static DcMotorEx rightEncoder;
    public static DcMotorEx frontEncoder;

    public static LynxModule expansionHub;

    private Hardware_Baciu() {}

    public static void init(final HardwareMap hardwareMap) {
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

        sliderLeft = hardwareMap.get(DcMotorEx.class, "sliderL");
        sliderRight = hardwareMap.get(DcMotorEx.class, "sliderR");

        sliderLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sliderLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sliderRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sliderLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        sliderRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        sliderLeft.setPower(0.0);
        sliderRight.setPower(0.0);

        sliderLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        sliderRight.setDirection(DcMotorSimple.Direction.REVERSE);

        limitSwitchLeft = hardwareMap.get(DigitalChannel.class, "limitSwitch1");
        limitSwitchRight = hardwareMap.get(DigitalChannel.class, "limitSwitch2");

        outtakeClaw = hardwareMap.get(Servo.class, "outtakeClaw");
        outtakeArmLeft = hardwareMap.get(Servo.class, "outtakeArmLeft");
        outtakeArmRight = hardwareMap.get(Servo.class, "outtakeArmRight");

        intakeSliders = hardwareMap.get(DcMotorEx.class, "intake");

        intakeSliders.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeSliders.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeSliders.setPower(0.0);
        intakeSliders.setDirection(DcMotorSimple.Direction.FORWARD);

        intakeArm = hardwareMap.get(Servo.class, "intakeArm");
        intakeClaw = hardwareMap.get(Servo.class, "intakeClaw");
        intakeClawAngle = hardwareMap.get(Servo.class, "moveIntake");

//        intakeSensor = hardwareMap.get(RevColorSensorV3.class, "intakeSensor");

        intakeLimitSwitch = hardwareMap.get(DigitalChannel.class, "limitSwitchIntake");

        leftEncoder = hardwareMap.get(DcMotorEx.class, "backLeft");
        rightEncoder = hardwareMap.get(DcMotorEx.class, "backRight");
        frontEncoder = hardwareMap.get(DcMotorEx.class, "frontLeft");

        expansionHub = hardwareMap.get(LynxModule.class, "Expansion Hub 2");
    }

}
