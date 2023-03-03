package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.IntakeEnums.IntakeArmAngle;
import org.firstinspires.ftc.teamcode.hardware.IntakeEnums.IntakeClawAngle;
import org.firstinspires.ftc.teamcode.hardware.IntakeEnums.IntakeClawState;

public class Intake_Baciu {

    // TODO properly initialize armAngle and clawAngle
    private double armAngle = 0.5;
    private double clawAngle = 0.5;
    private int slidersPosition = 0;
    private int slidersTargetPosition = 0;

    public void init() {
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

    public void moveSliders() {
        // TODO increment slidersPosition instead of setting motor power
    }

    public void retractSliders() {
        slidersPosition = 0;
        Hardware_Baciu.intakeSliders.setPower(-0.5);
    }

    public void moveSlidersToSavedPosition() {
        Hardware_Baciu.intakeSliders.setTargetPosition(slidersTargetPosition);
    }

    public void saveSlidersPosition() {
        slidersTargetPosition = Hardware_Baciu.intakeSliders.getCurrentPosition();
    }

    public void moveArm(IntakeArmAngle intakeArmAngle) {
        Hardware_Baciu.intakeArm.setPosition(intakeArmAngle.getAngle());
    }

    public void moveClawAngle(IntakeClawAngle intakeClawAngle) {
        Hardware_Baciu.intakeClawAngle.setPosition(intakeClawAngle.getAngle());
    }

    public void moveClaw(IntakeClawState intakeClawState) {
        Hardware_Baciu.intakeClaw.setPosition(intakeClawState.getPosition());
    }

    // Manual movement - sliders & virtual 4 bar
    public void moveSlidersManually(Gamepad gamepad) {
        if (gamepad.dpad_left) {
            Hardware_Baciu.intakeSliders.setPower(0.5);
        } else if (gamepad.dpad_right) {
            Hardware_Baciu.intakeSliders.setPower(-0.5);
        } else {
            Hardware_Baciu.intakeSliders.setPower(0.0);
        }
    }

    public void moveArmManually(Gamepad gamepad) {
        if (gamepad.left_stick_x > 0) {
            armAngle += 0.005;
        } else if (gamepad.left_stick_x < 0) {
            armAngle -= 0.005;
        }
        armAngle = Range.clip(armAngle, 0, 1);
        Hardware_Baciu.intakeArm.setPosition(armAngle);
    }

    public void moveClawAngleManually(Gamepad gamepad) {
        if (gamepad.right_stick_x > 0) {
            clawAngle += 0.005;
        } else if (gamepad.right_stick_x < 0) {
            clawAngle -= 0.005;
        }
        clawAngle = Range.clip(clawAngle, 0, 1);
        Hardware_Baciu.intakeClawAngle.setPosition(clawAngle);
    }

}
