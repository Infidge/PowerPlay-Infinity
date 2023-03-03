package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeArm;
import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeClaw;
import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeSlidersPosition;
import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeSlidersState;

public class Outtake_Baciu {

    private static final int STABILIZATION_THRESHOLD = 15;

    private boolean clawOpen = false;
    private int slidersPosition = 0;

    public void init() {
        moveClaw(OuttakeClaw.CLOSED);
        moveArm(OuttakeArm.COLLECT);
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
        if (slidersPosition != 0) {
            double slidersPower;
            if (Math.abs(slidersPosition - Hardware_Baciu.sliderLeft.getCurrentPosition()) < STABILIZATION_THRESHOLD) {
                if (slidersPosition > Hardware_Baciu.sliderLeft.getCurrentPosition()) {
                    slidersPower = OuttakeSlidersState.STOP.getPower();
                } else {
                    slidersPower = -OuttakeSlidersState.STOP.getPower();
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
    }

    public void lowerSliders() {
        slidersPosition = 0;
        Hardware_Baciu.sliderLeft.setPower(-OuttakeSlidersState.MOVE.getPower());
        Hardware_Baciu.sliderRight.setPower(-OuttakeSlidersState.MOVE.getPower());
    }

    public void moveSliders(final OuttakeSlidersPosition outtakeSlidersPosition) {
        this.slidersPosition = outtakeSlidersPosition.getPosition();
    }

    public void moveArm(final OuttakeArm outtakeArm) {
        Hardware_Baciu.outtakeArmLeft.setPosition(outtakeArm.getPosition());
        Hardware_Baciu.outtakeArmRight.setPosition(1 - outtakeArm.getPosition());
    }

    public void moveClaw(final OuttakeClaw outtakeClaw) {
        Hardware_Baciu.outtakeClaw.setPosition(outtakeClaw.getPosition());
        clawOpen = outtakeClaw == OuttakeClaw.OPEN;
    }

    public boolean isClawOpen() {
        return clawOpen;
    }

}
