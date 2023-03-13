package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.IntakeEnums.IntakeArmAngle;
import org.firstinspires.ftc.teamcode.hardware.IntakeEnums.IntakeClawAngle;
import org.firstinspires.ftc.teamcode.hardware.IntakeEnums.IntakeClawPosition;
import org.firstinspires.ftc.teamcode.hardware.IntakeEnums.IntakeSlidersPosition;
import org.firstinspires.ftc.teamcode.hardware.IntakeEnums.IntakeSlidersPower;

public class Intake_Baciu {

    private static final int STABILIZATION_THRESHOLD = 15;

    @Deprecated
    private static final double MOTOR_POWER = 0.5;

    private boolean holdingSlidersPosition = false;

    private double armAngle = 0.5;
    private double clawAngle = 0.5;

    private int slidersPosition = 0;
    private int slidersTargetPosition = 0;

    public void init() {
        // TODO does order matter?
        moveClaw(IntakeClawPosition.OPEN);
        Hardware_Baciu.intakeClawAngle.setPosition(clawAngle);
        Hardware_Baciu.intakeArm.setPosition(armAngle);
    }

    public void tickLimitSwitch() {
        // TODO check intakeLimitSwitch.getState()
        if (Hardware_Baciu.intakeSliders.getPower() < 0.0 && Hardware_Baciu.intakeLimitSwitch.getState()) {
            Hardware_Baciu.intakeSliders.setPower(0.0);
            Hardware_Baciu.intakeSliders.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Hardware_Baciu.intakeSliders.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void tickSliders() {
        if (slidersPosition == 0) {
            if (Hardware_Baciu.intakeSliders.getCurrentPosition() < STABILIZATION_THRESHOLD) return;
            Hardware_Baciu.intakeSliders.setPower(IntakeSlidersPower.RETRACT.get());
        }
        double slidersPower;
        if (Math.abs(slidersPosition - Hardware_Baciu.intakeSliders.getCurrentPosition()) < STABILIZATION_THRESHOLD) {
            holdingSlidersPosition = true;
            if (slidersPosition > Hardware_Baciu.intakeSliders.getCurrentPosition()) {
                slidersPower = IntakeSlidersPower.HOLD.get();
            } else {
                slidersPower = -IntakeSlidersPower.HOLD.get();
            }
        } else {
            holdingSlidersPosition = false;
            if (slidersPosition > Hardware_Baciu.intakeSliders.getCurrentPosition()) {
                slidersPower = IntakeSlidersPower.EXTEND.get() - Range.clip((double) Hardware_Baciu.intakeSliders.getCurrentPosition() / slidersPosition, 0, 0.2);
            } else {
                slidersPower = IntakeSlidersPower.RETRACT.get() + Range.clip((double) slidersPosition / Hardware_Baciu.intakeSliders.getCurrentPosition(), 0, 0.2);
            }
        }
        Hardware_Baciu.intakeSliders.setPower(slidersPower);
    }

    public void moveSliders(final IntakeSlidersPower intakeSlidersPower) {
        if (intakeSlidersPower == IntakeSlidersPower.RETRACT) {
            slidersPosition--;
        } else if (intakeSlidersPower == IntakeSlidersPower.EXTEND) {
            slidersPosition++;
        }
        slidersPosition = Range.clip(slidersPosition, 0, 1000);
    }

    public void moveSlidersToPosition(final IntakeSlidersPosition intakeSlidersPosition) {

    }

    public void retractSliders() {
        slidersPosition = 0;
        Hardware_Baciu.intakeSliders.setPower(IntakeSlidersPower.RETRACT.get());
    }

    public void moveSlidersToSavedPosition() {
        slidersPosition = slidersTargetPosition;
    }

    public void saveSlidersPosition() {
        slidersTargetPosition = Hardware_Baciu.intakeSliders.getCurrentPosition();
    }

    public boolean isHoldingSlidersPosition() {
        return holdingSlidersPosition;
    }

    public void moveArm(final IntakeArmAngle intakeArmAngle) {
        Hardware_Baciu.intakeArm.setPosition(intakeArmAngle.get());
    }

    public void moveClawAngle(final IntakeClawAngle intakeClawAngle) {
        Hardware_Baciu.intakeClawAngle.setPosition(intakeClawAngle.get());
    }

    public void moveClaw(final IntakeClawPosition intakeClawPosition) {
        Hardware_Baciu.intakeClaw.setPosition(intakeClawPosition.get());
    }

    // Manual movement - sliders & virtual 4 bar
    public void moveSlidersManually(final Gamepad gamepad) {
        if (gamepad.dpad_left) {
            Hardware_Baciu.intakeSliders.setPower(MOTOR_POWER);
        } else if (gamepad.dpad_right) {
            Hardware_Baciu.intakeSliders.setPower(-MOTOR_POWER);
        } else {
            Hardware_Baciu.intakeSliders.setPower(0.0);
        }
    }

    public void moveArmManually(final Gamepad gamepad) {
        if (gamepad.left_stick_x > 0) {
            armAngle += 0.005;
        } else if (gamepad.left_stick_x < 0) {
            armAngle -= 0.005;
        }
        armAngle = Range.clip(armAngle, 0, 1);
        Hardware_Baciu.intakeArm.setPosition(armAngle);
    }

    public void moveClawAngleManually(final Gamepad gamepad) {
        if (gamepad.right_stick_x > 0) {
            clawAngle += 0.005;
        } else if (gamepad.right_stick_x < 0) {
            clawAngle -= 0.005;
        }
        clawAngle = Range.clip(clawAngle, 0, 1);
        Hardware_Baciu.intakeClawAngle.setPosition(clawAngle);
    }

}
