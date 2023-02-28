package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constant;
import org.firstinspires.ftc.teamcode.util.Encoder;

public class Hardware_Test {


    /** Drivetrain */
    public static DcMotorEx motorFl;
    public static DcMotorEx motorBl;
    public static DcMotorEx motorBr;
    public static DcMotorEx motorFr;


    /** Deadwheels */
    /*public static Encoder leftEncoder;
    public static Encoder rightEncoder;
    public static Encoder frontEncoder;*/
    HardwareMap hwMap =  null;

    public Hardware_Test(){
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




        /** Deadwheels */

        /*leftEncoder = new Encoder(hwMap.get(DcMotorEx.class, "backLeft"));
        rightEncoder = new Encoder(hwMap.get(DcMotorEx.class, "backRight"));
        frontEncoder = new Encoder(hwMap.get(DcMotorEx.class, "frontLeft"));*/



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
