package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeArmAngle;
import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeClawPosition;
import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeSlidersPosition;
import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeSlidersPower;

public class Outtake_Baciu {

    private static final int STABILIZATION_THRESHOLD = 15;

    private boolean clawOpen = false;
    private int slidersPosition = 0;
    private boolean stabilizingSliders = false;

    public void init() {
        moveClaw(OuttakeClawPosition.CLOSED);
        moveArm(OuttakeArmAngle.COLLECT_CONE);
    }

    public void tickLimitSwitch() {
        if (Hardware_Baciu.sliderLeft.getPower() < 0.0 && Hardware_Baciu.limitSwitchLeft.getState()) {
            Hardware_Baciu.sliderLeft.setPower(0.0);
            Hardware_Baciu.sliderRight.setPower(0.0);
            Hardware_Baciu.sliderLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Hardware_Baciu.sliderLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void tickSliders() {
        if (slidersPosition == 0) return;
        double slidersPower;
        if (Math.abs(slidersPosition - Hardware_Baciu.sliderLeft.getCurrentPosition()) < STABILIZATION_THRESHOLD) {
            stabilizingSliders = true;
            if (slidersPosition > Hardware_Baciu.sliderLeft.getCurrentPosition()) {
                slidersPower = OuttakeSlidersPower.HOLD.get();
            } else {
                slidersPower = -OuttakeSlidersPower.HOLD.get();
            }
        } else {
            if (slidersPosition > Hardware_Baciu.sliderLeft.getCurrentPosition()) {
                slidersPower = OuttakeSlidersPower.RAISE.get() - Range.clip((double) Hardware_Baciu.sliderLeft.getCurrentPosition() / slidersPosition, 0, 0.5);
            } else {
                slidersPower = OuttakeSlidersPower.LOWER.get() + Range.clip((double) slidersPosition / Hardware_Baciu.sliderLeft.getCurrentPosition(), 0, 0.5);
            }
        }
        Hardware_Baciu.sliderLeft.setPower(slidersPower);
        Hardware_Baciu.sliderRight.setPower(slidersPower);
    }

    public void lowerSliders() {
        slidersPosition = 0;
        Hardware_Baciu.sliderLeft.setPower(OuttakeSlidersPower.LOWER.get());
        Hardware_Baciu.sliderRight.setPower(OuttakeSlidersPower.LOWER.get());
    }

    public void moveSliders(final OuttakeSlidersPosition outtakeSlidersPosition) {
        this.slidersPosition = outtakeSlidersPosition.get();
    }

    public void moveArm(final OuttakeArmAngle outtakeArmAngle) {
        Hardware_Baciu.outtakeArmLeft.setPosition(outtakeArmAngle.get());
        Hardware_Baciu.outtakeArmRight.setPosition(1 - outtakeArmAngle.get());
    }

    public void moveClaw(final OuttakeClawPosition outtakeClawPosition) {
        Hardware_Baciu.outtakeClaw.setPosition(outtakeClawPosition.get());
        clawOpen = outtakeClawPosition == OuttakeClawPosition.OPEN;
    }

    public boolean isClawOpen() {
        return clawOpen;
    }

    public boolean isStabilizingSliders() {
        return stabilizingSliders;
    }

}
