package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.text.style.IconMarginSpan;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.configuration.BuiltInConfigurationTypeJsonAdapter;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.sun.source.tree.NewClassTree;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Constant;

public class Intake {
    Hardware robot = new Hardware();

    public double armPosition = Constant.armDropCone;
    public double clawAngle = 0.5;
    public double sliderPos = Constant.retracted;
    private double sliderTarget = Constant.retracted;
    public double lock = 0.0;
    public boolean open = true;
    public boolean gotCone = false;
    private boolean breakProof = false;
    private boolean manual = false;
    private boolean locked = true;
    private ElapsedTime autoDrop = new ElapsedTime();
    private ElapsedTime coneTime = new ElapsedTime();
    private boolean in = false;
    private boolean out = false;


    private ElapsedTime clawToggleTime = new ElapsedTime();

    public void moveArmManually(Gamepad gamepad){
        if (gamepad.right_stick_x > 0)
            armPosition+=0.01;
        else if (gamepad.right_stick_x < 0)
            armPosition-=0.01;
        armPosition = Range.clip(armPosition, 0, 1);
    }

    public void moveAngle(Gamepad gamepad){
        if (gamepad.left_stick_x > 0)
            clawAngle+=0.005;
        else if (gamepad.left_stick_x < 0)
            clawAngle-=0.005;
        clawAngle = Range.clip(clawAngle, 0, 1);
    }

    public void angleGoToPreset(){
        Hardware.intakeClawAngle.setPosition(clawAngle);
    }

    public void checkForCone(boolean isOuttakeOpen) {
        if (Hardware.intakeSensor.getDistance(DistanceUnit.CM) < 1.5 && (robot.red(Hardware.intakeSensor) || robot.blue(Hardware.intakeSensor)) && isOuttakeOpen && out) {
            if (open) coneTime.reset();
            closeClaw();
            if (!open && coneTime.seconds() > 0.7) {
                Hardware.intakeArm.setPosition(Constant.armDropCone);
                Hardware.intakeClawAngle.setPosition(Constant.intakeDrop);
                in = true;
                out = false;
            }

        }
    }

    public void closeClaw(){
        Hardware.intakeClaw.setPosition(Constant.closedIntake);
        open = false;
    }

    public void openClaw(){
        Hardware.intakeClaw.setPosition(Constant.openIntake);
        open = true;
    }

    public void selectArmPreset(Gamepad gamepad){
        if (gamepad.b) {
            armPosition = Constant.armCone5;
            clawAngle = Constant.clawAngleCone5;
            breakProof = true;
            out = true;
            in = false;
        }
        if (gamepad.x) {
            armPosition = Constant.armDropCone;
            clawAngle = Constant.intakeDrop;
            breakProof = false;
            in = true;
            out = false;
        }
        if (gamepad.y && !breakProof) {
            armPosition = Constant.armDroppedCone;
            clawAngle = Constant.intakeDown;
            in = false;
            out = true;
        }
    }

    public void armGoToPreset(){
        Hardware.intakeArm.setPosition(armPosition);
    }

    public void moveSlidersManually(Gamepad gamepad) {
        if (gamepad.dpad_left) {
            Hardware.intakeSliders.setPower(1.0);
            manual = true;
        }
        else if (gamepad.dpad_right) {
            Hardware.intakeSliders.setPower(-1.0);
            manual = true;
        }
        else if (Hardware.intakeSliders.getCurrentPosition() < 1 && !locked)
            Hardware.intakeSliders.setPower(-0.1);
        else Hardware.intakeSliders.setPower(0.0);

    }

    public void sliderGoToPos(){
        if (Math.abs(sliderPos - Hardware.intakeSliders.getCurrentPosition()) < 10){
            if (Hardware.intakeSliders.getCurrentPosition() < sliderPos){
                Hardware.intakeSliders.setPower(Constant.stabilizeSliders);
            }
            else Hardware.intakeSliders.setPower(-Constant.stabilizeSliders);
        }
        else {
            if (Hardware.intakeSliders.getCurrentPosition() < sliderPos){
                Hardware.intakeSliders.setPower(Constant.slidersPower);
            }
            else Hardware.intakeSliders.setPower(-Constant.slidersPower);
        }
    }

    public void slidersResetEncoder(){
        if (!Hardware.limitSwitchIntake.getState()){
            Hardware.intakeSliders.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Hardware.intakeSliders.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Hardware.intakeSliders.setPower(0.0);
            sliderPos = 0;
        }
    }

    public void toggleClaw(Gamepad gamepad){
        if (gamepad.left_bumper){
            closeClaw();
            clawToggleTime.reset();
        }
        else if (gamepad.right_bumper){
            openClaw();
            clawToggleTime.reset();
        }
        open = !open;
    }

    public void autoDropIn() {
        if (armPosition >= Constant.armDropCone - 2 && Hardware.limitSwitchIntake.getState()) {
            if (!open) {
                clawAngle = Constant.intakeDrop;
                autoDrop.reset();
            }
            if (autoDrop.seconds() > 0.5)
                openClaw();
        }
    }

    public void slidersGoToCone(boolean isOuttakeOpen){
        if (Hardware.intakeSensor.getDistance(DistanceUnit.CM) < 3){
            Hardware.intakeSliders.setPower(0.0);
            if (isOuttakeOpen) closeClaw();
        }
        else Hardware.intakeSliders.setPower(1.0);
    }

   /* public void lockSLiders(Gamepad gamepad){
        if (gamepad.dpad_left || gamepad.dpad_right || Math.abs(Hardware.intakeSliders.getCurrentPosition()) > 2){
            Hardware.stopSliders.setPosition(Constant.unlock);
        }
        else Hardware.stopSliders.setPosition(Constant.lock);
    }

    public void manualLock(Gamepad gamepad){
        if (gamepad.dpad_up)
            lock+=0.03;
        else if (gamepad.dpad_down)
            lock-=0.03;
        lock = Range.clip(lock,0,1);
        Hardware.stopSliders.setPosition(lock);
    }*/

    //aliniere pe con.
}
