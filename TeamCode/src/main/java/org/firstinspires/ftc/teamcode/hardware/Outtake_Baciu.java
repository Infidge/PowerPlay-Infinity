package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeArmAngle;
import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeClawState;
import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeSlidersPosition;
import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeSlidersState;

public class Outtake_Baciu {

    private static final int STABILIZATION_THRESHOLD = 15;

    private boolean clawOpen = false;
    private int slidersPosition = 0;

    public void init() {
        moveClaw(OuttakeClawState.CLOSED);
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
            if (slidersPosition > Hardware_Baciu.sliderLeft.getCurrentPosition()) {
                slidersPower = OuttakeSlidersState.HOLD.getPower();
            } else {
                slidersPower = -OuttakeSlidersState.HOLD.getPower();
            }
        } else {
            if (slidersPosition > Hardware_Baciu.sliderLeft.getCurrentPosition()) {
                slidersPower = OuttakeSlidersState.MOVE.getPower() - Range.clip((double) Hardware_Baciu.sliderLeft.getCurrentPosition() / slidersPosition, 0, 0.8);
            } else {
                slidersPower = -OuttakeSlidersState.MOVE.getPower() + Range.clip((double) slidersPosition / Hardware_Baciu.sliderLeft.getCurrentPosition(), 0, 0.8);
            }
        }
        Hardware_Baciu.sliderLeft.setPower(slidersPower);
        Hardware_Baciu.sliderRight.setPower(slidersPower);
    }

    public void lowerSliders() {
        slidersPosition = 0;
        Hardware_Baciu.sliderLeft.setPower(-OuttakeSlidersState.MOVE.getPower());
        Hardware_Baciu.sliderRight.setPower(-OuttakeSlidersState.MOVE.getPower());
    }

    public void moveSliders(final OuttakeSlidersPosition outtakeSlidersPosition) {
        this.slidersPosition = outtakeSlidersPosition.get();
    }

    public void moveArm(final OuttakeArmAngle outtakeArmAngle) {
        Hardware_Baciu.outtakeArmLeft.setPosition(outtakeArmAngle.get());
        Hardware_Baciu.outtakeArmRight.setPosition(1 - outtakeArmAngle.get());
    }

    public void moveClaw(final OuttakeClawState outtakeClawState) {
        Hardware_Baciu.outtakeClaw.setPosition(outtakeClawState.getPosition());
        clawOpen = outtakeClawState == OuttakeClawState.OPEN;
    }

    public boolean isClawOpen() {
        return clawOpen;
    }

}
