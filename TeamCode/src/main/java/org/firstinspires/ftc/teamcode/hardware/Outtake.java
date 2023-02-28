package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Constant;

public class Outtake {

    private double lastDist = -1.0;
    public int sliderPos = Constant.mid;

    public double armPos = Constant.outtakeArmIn;

    public double leftPos = armPos, rightPos = 1-armPos;

    public boolean collect = true;
    public boolean open = false;
    private boolean dropping = false;

    private ElapsedTime autoDropTime = new ElapsedTime();

    private ElapsedTime clawToggleTime = new ElapsedTime();

    public enum V4BarState {
        Collect,
        Drop
    }

    public void moveV4Bar(V4BarState state){
        switch (state) {
            case Collect:
                Hardware.outtakeArmLeft.setPosition(Constant.outtakeArmIn);
                Hardware.outtakeArmRight.setPosition(1-Constant.outtakeArmIn);
                Hardware.junctionGuider.setPosition(Constant.retractGuider);
                dropping = false;
                break;
            case Drop:
                Hardware.outtakeArmLeft.setPosition(Constant.outtakeArmDropAuto);
                Hardware.outtakeArmRight.setPosition(1-Constant.outtakeArmDropAuto);
                Hardware.junctionGuider.setPosition(Constant.guiderOn);
                autoDropTime.reset();
                dropping = true;
                break;
            default:
                break;
        }
    }

    public void moveArm(Gamepad gamepad){
        if (gamepad.b)
            moveV4Bar(V4BarState.Collect);
        else if (gamepad.x)
            moveV4Bar(V4BarState.Drop);
    }

    public void openClaw(){
        Hardware.outtakeClaw.setPosition(Constant.openClaw);
        open = true;
    }

    public void closeClaw() {
        Hardware.outtakeClaw.setPosition(Constant.closedClaw);
        open = false;
    }

    public void moveSlidersManual(Gamepad gamepad){
        if (gamepad.dpad_up) {
            Hardware.slider1.setPower(Constant.raiseSlider);
            Hardware.slider2.setPower(Constant.raiseSlider);
        }
        else if (gamepad.dpad_down) {
            Hardware.slider1.setPower(Constant.lowerSlider);
            Hardware.slider2.setPower(Constant.lowerSlider);
        }
        else{
            Hardware.slider1.setPower(0.0);
            Hardware.slider2.setPower(0.0);
        }
    }


    public void moveArmManually (Gamepad gamepad){
        if (gamepad.left_stick_y < 0.0){
            armPos+=0.01;
        }
        else if (gamepad.left_stick_y > 0.0){
            armPos-=0.01;
        }
        armPos = Range.clip(armPos,0,1);
        Hardware.outtakeArmLeft.setPosition(armPos);
        Hardware.outtakeArmRight.setPosition(1-armPos);
    }

    public void selectSliderPreset(Gamepad gamepad){
        if (gamepad.dpad_up){
            sliderPos = Constant.high;
            moveV4Bar(V4BarState.Drop);
            collect = false;
        }
        else if (gamepad.dpad_left || gamepad.dpad_right){
            sliderPos = Constant.mid;
            moveV4Bar(V4BarState.Drop);
            collect = false;
        }
        else if (gamepad.dpad_down){
            sliderPos = Constant.low;
            moveV4Bar(V4BarState.Drop);
            collect = false;
        }
    }
    public void moveSlidersPreset(){
        if (!collect) {
            if (Math.abs(sliderPos - Hardware.slider1.getCurrentPosition()) < 15) {
                if (sliderPos > Hardware.slider1.getCurrentPosition()) {
                    Hardware.slider1.setPower(Constant.stopSlider);
                    Hardware.slider2.setPower(Constant.stopSlider);
                } else {
                    Hardware.slider1.setPower(-Constant.stopSlider);
                    Hardware.slider2.setPower(-Constant.stopSlider);
                }
            }
            else {
                if (sliderPos > Hardware.slider1.getCurrentPosition()) {
                        Hardware.slider1.setPower(Constant.raiseSlider - Range.clip((double) Hardware.slider1.getCurrentPosition() / sliderPos, 0, 0.8));
                        Hardware.slider2.setPower(Constant.raiseSlider - Range.clip((double) Hardware.slider1.getCurrentPosition() / sliderPos, 0, 0.8));
                }
                else {
                        Hardware.slider1.setPower(Constant.lowerSlider + Range.clip((double) sliderPos / Hardware.slider1.getCurrentPosition(), 0, 0.8));
                        Hardware.slider2.setPower(Constant.lowerSlider + Range.clip((double) sliderPos / Hardware.slider1.getCurrentPosition(), 0, 0.8));
                }
            }
        }
    }

    public void lowerSliders(Gamepad gamepad){
        if (gamepad.left_bumper){
            collect = true;
            Hardware.slider1.setPower(Constant.lowerSlider);
            Hardware.slider2.setPower(Constant.lowerSlider);
            moveV4Bar(V4BarState.Collect);
        }
    }

    public void lowerSlidersAuto(){
            collect = true;
            Hardware.slider1.setPower(Constant.lowerSlider);
            Hardware.slider2.setPower(Constant.lowerSlider);
            moveV4Bar(V4BarState.Collect);
    }

    public void resetEncoder(){
        if (Hardware.slider1.getPower() < 0.0 && Hardware.limitSwitch1.getState()){
            Hardware.slider1.setPower(0.0);
            Hardware.slider2.setPower(0.0);
            Hardware.slider1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Hardware.slider1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void checkForJunction(){
        if (lastDist != -1.0) {
            if (Math.abs(lastDist) - Hardware.outtakeSensor.getDistance(DistanceUnit.CM) > 15 && Hardware.slider1.getCurrentPosition() > Constant.low - 50 && dropping && autoDropTime.seconds() > 1.0 ) {
                openClaw();
            }
        }

        lastDist = Hardware.outtakeSensor.getDistance(DistanceUnit.CM);
    }

    public void toggleClaw(Gamepad gamepad){
        if (gamepad.a){
            closeClaw();
            clawToggleTime.reset();
        }
        else if (gamepad.y){
            openClaw();
            clawToggleTime.reset();
        }
    }

}
