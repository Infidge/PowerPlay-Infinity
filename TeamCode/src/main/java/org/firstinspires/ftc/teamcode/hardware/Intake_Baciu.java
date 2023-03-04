package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.IntakeEnums.IntakeArmAngle;
import org.firstinspires.ftc.teamcode.hardware.IntakeEnums.IntakeClawAngle;
import org.firstinspires.ftc.teamcode.hardware.IntakeEnums.IntakeClawState;
import org.firstinspires.ftc.teamcode.hardware.IntakeEnums.IntakeSlidersMovement;

public class Intake_Baciu {

    @Deprecated
    private static final double MOTOR_POWER = 0.5;

    private double armAngle = 0.5;
    private double clawAngle = 0.5;
    private int slidersPosition = 0;
    private int slidersTargetPosition = 0;

    public void init() {
        // TODO does order matter?
        moveClaw(IntakeClawState.OPEN);
        Hardware_Baciu.intakeClawAngle.setPosition(clawAngle);
        Hardware_Baciu.intakeArm.setPosition(armAngle);
    }

    public void tickLimitSwitch() {
        if (Hardware_Baciu.intakeSliders.getPower() < 0.0 && Hardware_Baciu.intakeLimitSwitch.getState()) {
            Hardware_Baciu.intakeSliders.setPower(0.0);
            Hardware_Baciu.intakeSliders.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Hardware_Baciu.intakeSliders.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void tickSliders() {
        // TODO stabilize sliders
    }

    public void moveSliders(final IntakeSlidersMovement intakeSlidersMovement) {
        if (intakeSlidersMovement == IntakeSlidersMovement.RETRACT) {
            slidersPosition--;
        } else if (intakeSlidersMovement == IntakeSlidersMovement.EXTEND) {
            slidersPosition++;
        }
        slidersPosition = Range.clip(slidersPosition, 0, 1000);
    }

    public void retractSliders() {
        slidersPosition = 0;
        Hardware_Baciu.intakeSliders.setPower(IntakeSlidersMovement.RETRACT.getPower());
    }

    public void moveSlidersToSavedPosition() {
        slidersPosition = slidersTargetPosition;
    }

    public void saveSlidersPosition() {
        slidersTargetPosition = Hardware_Baciu.intakeSliders.getCurrentPosition();
    }

    public void moveArm(final IntakeArmAngle intakeArmAngle) {
        Hardware_Baciu.intakeArm.setPosition(intakeArmAngle.get());
    }

    public void moveClawAngle(final IntakeClawAngle intakeClawAngle) {
        Hardware_Baciu.intakeClawAngle.setPosition(intakeClawAngle.get());
    }

    public void moveClaw(final IntakeClawState intakeClawState) {
        Hardware_Baciu.intakeClaw.setPosition(intakeClawState.getPosition());
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
