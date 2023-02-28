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

public class Hardware {


    /** Drivetrain */
    public static DcMotorEx motorFl;
    public static DcMotorEx motorBl;
    public static DcMotorEx motorBr;
    public static DcMotorEx motorFr;

    /** Outtake */
    public static DcMotorEx slider1;
    public static DcMotorEx slider2;

    public static DigitalChannel limitSwitch1;
    public static DigitalChannel limitSwitch2;

    public static Servo outtakeClaw;
    public static Servo outtakeArmLeft;
    public static Servo outtakeArmRight;
    public static Servo junctionGuider;

    public static Rev2mDistanceSensor outtakeSensor;

    /** Intake */
    public static DcMotorEx intakeSliders;

    public static Servo intakeClaw;
    public static Servo intakeClawAngle;
    public static Servo intakeArm;
    //public static Servo stopSliders;


    public static RevColorSensorV3 intakeSensor;

    public static DigitalChannel limitSwitchIntake;

    /** Deadwheels */
    public static DcMotorEx leftEncoder;
    public static DcMotorEx rightEncoder;
    public static DcMotorEx frontEncoder;

    /** Expansion Hub */
    public static LynxModule expansionHub;

    HardwareMap hwMap =  null;

    public Hardware(){
    }

    public void init(HardwareMap ahwMap){

        hwMap = ahwMap;

        /** Drivetrain */

        motorFl = hwMap.get(DcMotorEx.class, "frontLeft");
        motorBl = hwMap.get(DcMotorEx.class, "backLeft");
        motorBr = hwMap.get(DcMotorEx.class, "backRight");
        motorFr = hwMap.get(DcMotorEx.class, "frontRight");

        motorFl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorFl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorFl.setPower(0.0);
        motorBl.setPower(0.0);
        motorBr.setPower(0.0);
        motorFr.setPower(0.0);

        motorFl.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBl.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBr.setDirection(DcMotorSimple.Direction.FORWARD);
        motorFr.setDirection(DcMotorSimple.Direction.FORWARD);

        /** Outtake */

        slider1 = hwMap.get(DcMotorEx.class, "sliderL");
        slider2 = hwMap.get(DcMotorEx.class, "sliderR");

        slider1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slider1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slider2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        slider1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slider2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slider1.setPower(0.0);
        slider2.setPower(0.0);

        slider1.setDirection(DcMotorSimple.Direction.REVERSE);
        slider2.setDirection(DcMotorSimple.Direction.REVERSE);

        outtakeClaw   = hwMap.get(Servo.class, "outtakeClaw");
        outtakeArmLeft = hwMap.get(Servo.class, "outtakeArmLeft");
        outtakeArmRight = hwMap.get(Servo.class, "outtakeArmRight");
        junctionGuider = hwMap.get(Servo.class, "junctionGuider");

        outtakeClaw  .setPosition(Constant.closedClaw);
        outtakeArmLeft .setPosition(0.0);
        outtakeArmRight .setPosition(1.0);
        junctionGuider.setPosition(Constant.retractGuider);

        outtakeSensor = hwMap.get(Rev2mDistanceSensor.class, "clawSensor");

        limitSwitch1 = hwMap.get(DigitalChannel.class, "limitSwitch1");
        limitSwitch2 = hwMap.get(DigitalChannel.class, "limitSwitch2");

        /** Intake */

        intakeSliders     = hwMap.get(DcMotorEx.class, "intake");

        intakeSliders.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        intakeSliders.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeSliders.setPower(-0.2);

        intakeSliders.setDirection(DcMotorSimple.Direction.FORWARD);

        intakeClaw = hwMap.get(Servo.class, "intakeClaw");
        intakeClawAngle = hwMap.get(Servo.class, "moveIntake");
        intakeArm = hwMap.get(Servo.class, "intakeArm");
        //stopSliders = hwMap.get(Servo.class, "stopSliders");

        intakeClaw.setPosition(Constant.openIntake);
        intakeClawAngle.setPosition(0.5);
        intakeArm.setPosition(Constant.armDropCone);
        //stopSliders.setPosition(Constant.lock);

        intakeSensor = hwMap.get(RevColorSensorV3.class, "intakeSensor");

        limitSwitchIntake = hwMap.get(DigitalChannel.class, "limitSwitchIntake");

        /** Deadwheels */

        leftEncoder = hwMap.get(DcMotorEx.class, "backLeft");
        rightEncoder = hwMap.get(DcMotorEx.class, "backRight");
        frontEncoder = hwMap.get(DcMotorEx.class, "frontLeft");

        /** Expansion Hub */

        expansionHub = hwMap.get(LynxModule.class, "Expansion Hub 2");
    }

    public double voltageToDegrees(double voltage){
        return (voltage * Constant.potentiometerMaxDegrees)/Constant.potentiometerMaxVoltage;
    }

    public double degreesToVoltage(double degrees){
        return (degrees * Constant.potentiometerMaxVoltage)/Constant.potentiometerMaxDegrees;
    }

    public double voltageToServo(double voltage){
        return voltageToDegrees(voltage)/300.0;
    }

    public double degreesToServo(double degrees){
        return degrees/300;
    }

    public double ticksToServo(double ticks){
        return (ticks/Constant.encoderTicksIntake)*5/6;
    }

    public boolean red(RevColorSensorV3 colorSensor){
        if (Math.max(Math.max(colorSensor.blue(), colorSensor.red()), colorSensor.green()) == colorSensor.red())
            return true;
        else return false;
    }

    public boolean blue(RevColorSensorV3 colorSensor){
        if (Math.max(Math.max(colorSensor.blue(), colorSensor.red()), colorSensor.green()) == colorSensor.blue())
            return true;
        else return false;
    }
}
