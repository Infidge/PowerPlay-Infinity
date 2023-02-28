package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constant;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Outtake;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.ElementDetection;

@Autonomous(name="Auto_Right", group="Roadrunner")
public class Auto_Right extends LinearOpMode {

    private enum robotStage{
        positionRobot,
        /*positionRobot2,
        cone1,
        dropCone1,
        cone2,
        dropCone2,
        cone3,
        dropCone3,
        cone4,
        dropCone4,*/
        choosePark,
        gotoPark,
        stopRobot}

    private final static robotStage[] robotStageValues = robotStage.values();
    private int programstage=0;

    int parkZone = 2;

    private int  nextStage(int ordinal){
        return ordinal;
    }

    private int  nextStage(){
        return nextStage(programstage+1);
    }

    private int  thisStage(){
        return nextStage(programstage);
    }

    private int previousStage(){return nextStage(programstage-1);}

    Hardware robot = new Hardware();
    Intake intake = new Intake();
    Outtake outtake = new Outtake();
    Drivetrain drivetrain = new Drivetrain();
    ElapsedTime runtime = new ElapsedTime();
    boolean moveSliders = true;
    boolean moveIntake = true;
    boolean moved = false;
    boolean dropping = true;

    int id1 = 13;
    int id2 = 19;
    int id3 = 12;

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        ElementDetection aprilTag = new ElementDetection(hardwareMap, telemetry);


        Pose2d startPose = new Pose2d(90, -159, Math.toRadians(270));
        drive.setPoseEstimate(startPose);

        Trajectory positionRobot, positionRobot2;
        TrajectorySequence park = null;

        positionRobot = drive.trajectoryBuilder(startPose, true)
                .lineToLinearHeading(new Pose2d(90,-40, Math.toRadians(173) + 0.244978663))
                .build();
        positionRobot2 = drive.trajectoryBuilder(positionRobot.end(), true)
                .lineToLinearHeading(new Pose2d(110,-48, Math.toRadians(173) + 0.244978663))
                .build();

        waitForStart();
        drive.followTrajectoryAsync(positionRobot);
        outtake.collect = true;
        Hardware.intakeSliders.setPower(0.0);
        while (opModeIsActive()){
            telemetry.addData("Left:", Hardware.leftEncoder.getCurrentPosition());
            telemetry.addData("Right:", Hardware.rightEncoder.getCurrentPosition());
            telemetry.addData("Front:", Hardware.frontEncoder .getCurrentPosition());
            telemetry.addData("Main:", Hardware.outtakeClaw.getPosition());
            telemetry.addData("Arm", Hardware.intakeArm.getPosition());
            telemetry.addData("Lift1:", Hardware.slider1.getCurrentPosition());
            telemetry.addData("LiftP:", Hardware.slider1.getPower());
            telemetry.addData("Switch1:", Hardware.limitSwitch1.getState());
            telemetry.addData("Switch2:", Hardware.limitSwitch2.getState());
            telemetry.addData("Arm Pos:", intake.armPosition);
            telemetry.addData("Intake Claw Angle:", intake.clawAngle);
            telemetry.addData("Outtake Arm:", outtake.armPos);
            telemetry.addData("intake sliders", Hardware.intakeSliders.getCurrentPosition());
            telemetry.addData("state", Hardware.limitSwitchIntake.getState());
            telemetry.update();
            drive.update();
            outtake.moveSlidersPreset();
            if (outtake.collect)
                outtake.resetEncoder();
            switch (robotStageValues[programstage]){
                case positionRobot:
                    if (drive.isBusy())
                        programstage = thisStage();
                    else {
                        programstage = nextStage();
                    }
                    break;
                /*case positionRobot2:
                    outtake.collect = false;
                    outtake.sliderPos = Constant.mid;
                    intake.armPosition = Constant.armCone1;
                    intake.clawAngle = Constant.clawAngleCone1;
                    intake.armGoToPreset();
                    intake.angleGoToPreset();
                    intake.slidersGoToCone(outtake.open);
                    if(Math.abs(Hardware.slider1.getPower()) != Constant.stopSlider || drive.isBusy()) {
                        programstage = thisStage();
                    }
                    else {
                        outtake.closeClaw();
                        outtake.moveV4Bar(Outtake.V4BarState.Drop);
                        runtime.reset();
                        while(runtime.seconds() < 0.9){
                            outtake.moveSlidersPreset();
                            intake.slidersGoToCone(outtake.open);
                            intake.armGoToPreset();
                            intake.angleGoToPreset();
                        }
                        outtake.openClaw();
                        runtime.reset();
                        while (runtime.seconds() < 0.5){
                            outtake.moveSlidersPreset();
                            intake.slidersGoToCone(outtake.open);
                            intake.armGoToPreset();
                            intake.angleGoToPreset();
                        }
                        outtake.moveV4Bar(Outtake.V4BarState.Collect);
                        runtime.reset();
                        while (runtime.seconds() < 0.5){
                            outtake.moveSlidersPreset();
                            intake.slidersGoToCone(outtake.open);
                            intake.armGoToPreset();
                        }
                        intake.closeClaw();
                        intake.clawAngle = Constant.intakeDrop;
                        intake.angleGoToPreset();
                        programstage = nextStage();
                    }
                    break;
                case cone1:
                    if (!outtake.collect)
                        outtake.lowerSlidersAuto();
                    intake.armPosition = Constant.armDropCone;
                    intake.clawAngle = Constant.intakeDrop;
                    intake.armGoToPreset();
                    intake.angleGoToPreset();
                    if (Hardware.limitSwitchIntake.getState()) {
                        Hardware.intakeSliders.setPower(-1.0);
                        programstage = thisStage();
                    }
                    else {
                        Hardware.intakeSliders.setPower(-0.2);
                        intake.openClaw();
                        runtime.reset();
                        while(runtime.seconds() < 0.5);
                        outtake.closeClaw();
                        runtime.reset();
                        while (runtime.seconds() < 0.5);
                        programstage = nextStage();
                    }
                    break;
                case dropCone1:
                    outtake.collect = false;
                    outtake.sliderPos = Constant.mid;
                    intake.armPosition = Constant.armCone2;
                    intake.clawAngle = Constant.clawAngleCone2;
                    intake.armGoToPreset();
                    intake.angleGoToPreset();
                    intake.slidersGoToCone(outtake.open);
                    if(Math.abs(Hardware.slider1.getPower()) != Constant.stopSlider) {
                        programstage = thisStage();
                    }
                    else {
                        outtake.closeClaw();
                        outtake.moveV4Bar(Outtake.V4BarState.Drop);
                        runtime.reset();
                        while(runtime.seconds() < 0.9){
                            outtake.moveSlidersPreset();
                            intake.slidersGoToCone(outtake.open);
                            intake.armGoToPreset();
                            intake.angleGoToPreset();
                        }
                        outtake.openClaw();
                        runtime.reset();
                        while (runtime.seconds() < 0.5){
                            outtake.moveSlidersPreset();
                            intake.slidersGoToCone(outtake.open);
                            intake.armGoToPreset();
                            intake.angleGoToPreset();
                        }
                        outtake.moveV4Bar(Outtake.V4BarState.Collect);
                        runtime.reset();
                        while (runtime.seconds() < 0.5){
                            outtake.moveSlidersPreset();
                            intake.slidersGoToCone(outtake.open);
                            intake.armGoToPreset();
                        }
                        intake.closeClaw();
                        intake.clawAngle = Constant.intakeDrop;
                        intake.angleGoToPreset();
                        programstage = nextStage();
                    }
                    break;
                case cone2:
                    if (!outtake.collect)
                        outtake.lowerSlidersAuto();
                    intake.armPosition = Constant.armDropCone;
                    intake.clawAngle = Constant.intakeDrop;
                    intake.armGoToPreset();
                    intake.angleGoToPreset();
                    if (Hardware.limitSwitchIntake.getState()) {
                        Hardware.intakeSliders.setPower(-1.0);
                        programstage = thisStage();
                    }
                    else {
                        Hardware.intakeSliders.setPower(-0.2);
                        intake.openClaw();
                        runtime.reset();
                        while(runtime.seconds() < 0.5);
                        programstage = nextStage();
                    }
                    break;
                case dropCone2:
                    outtake.collect = false;
                    outtake.sliderPos = Constant.mid;
                    intake.armPosition = Constant.armCone3;
                    intake.clawAngle = Constant.clawAngleCone3;
                    intake.armGoToPreset();
                    intake.angleGoToPreset();
                    intake.slidersGoToCone(outtake.open);
                    if(Math.abs(Hardware.slider1.getPower()) != Constant.stopSlider) {
                        programstage = thisStage();
                    }
                    else {
                        outtake.closeClaw();
                        outtake.moveV4Bar(Outtake.V4BarState.Drop);
                        runtime.reset();
                        while(runtime.seconds() < 0.9){
                            outtake.moveSlidersPreset();
                            intake.slidersGoToCone(outtake.open);
                            intake.armGoToPreset();
                            intake.angleGoToPreset();
                        }
                        outtake.openClaw();
                        runtime.reset();
                        while (runtime.seconds() < 0.5){
                            outtake.moveSlidersPreset();
                            intake.slidersGoToCone(outtake.open);
                            intake.armGoToPreset();
                            intake.angleGoToPreset();
                        }
                        outtake.moveV4Bar(Outtake.V4BarState.Collect);
                        runtime.reset();
                        while (runtime.seconds() < 0.5){
                            outtake.moveSlidersPreset();
                            intake.slidersGoToCone(outtake.open);
                            intake.armGoToPreset();
                        }
                        intake.closeClaw();
                        intake.clawAngle = Constant.intakeDrop;
                        intake.angleGoToPreset();
                        programstage = nextStage();
                    }
                    break;
                case cone3:
                    if (!outtake.collect)
                        outtake.lowerSlidersAuto();
                    intake.armPosition = Constant.armDropCone;
                    intake.clawAngle = Constant.intakeDrop;
                    intake.armGoToPreset();
                    intake.angleGoToPreset();
                    if (Hardware.limitSwitchIntake.getState()) {
                        Hardware.intakeSliders.setPower(-1.0);
                        programstage = thisStage();
                    }
                    else {
                        Hardware.intakeSliders.setPower(-0.2);
                        intake.openClaw();
                        runtime.reset();
                        while(runtime.seconds() < 0.5);
                        outtake.closeClaw();
                        runtime.reset();
                        while (runtime.seconds() < 0.5);
                        programstage = nextStage();
                    }
                    break;
                case dropCone3:
                    outtake.collect = false;
                    outtake.sliderPos = Constant.mid;
                    intake.armPosition = Constant.armCone4;
                    intake.clawAngle = Constant.clawAngleCone4;
                    intake.armGoToPreset();
                    intake.angleGoToPreset();
                    intake.slidersGoToCone(outtake.open);
                    if(Math.abs(Hardware.slider1.getPower()) != Constant.stopSlider) {
                        programstage = thisStage();
                    }
                    else {
                        outtake.closeClaw();
                        outtake.moveV4Bar(Outtake.V4BarState.Drop);
                        runtime.reset();
                        while(runtime.seconds() < 0.9){
                            outtake.moveSlidersPreset();
                            intake.slidersGoToCone(outtake.open);
                            intake.armGoToPreset();
                            intake.angleGoToPreset();
                        }
                        outtake.openClaw();
                        runtime.reset();
                        while (runtime.seconds() < 0.5){
                            outtake.moveSlidersPreset();
                            intake.slidersGoToCone(outtake.open);
                            intake.armGoToPreset();
                            intake.angleGoToPreset();
                        }
                        outtake.moveV4Bar(Outtake.V4BarState.Collect);
                        runtime.reset();
                        while (runtime.seconds() < 0.5){
                            outtake.moveSlidersPreset();
                            intake.slidersGoToCone(outtake.open);
                            intake.armGoToPreset();
                        }
                        intake.closeClaw();
                        intake.clawAngle = Constant.intakeDrop;
                        intake.angleGoToPreset();
                        programstage = nextStage();
                    }
                    break;
                case cone4:
                    if (!outtake.collect)
                        outtake.lowerSlidersAuto();
                    intake.armPosition = Constant.armDropCone;
                    intake.clawAngle = Constant.intakeDrop;
                    intake.armGoToPreset();
                    intake.angleGoToPreset();
                    if (Hardware.limitSwitchIntake.getState()) {
                        Hardware.intakeSliders.setPower(-1.0);
                        programstage = thisStage();
                    }
                    else {
                        Hardware.intakeSliders.setPower(-0.2);
                        intake.openClaw();
                        runtime.reset();
                        while(runtime.seconds() < 0.5);
                        outtake.closeClaw();
                        runtime.reset();
                        while (runtime.seconds() < 0.5);
                        programstage = nextStage();
                    }
                    break;
                case dropCone4:
                    outtake.collect = false;
                    outtake.sliderPos = Constant.mid;
                    if(Math.abs(Hardware.slider1.getPower()) != Constant.stopSlider) {
                        programstage = thisStage();
                    }
                    else {
                        outtake.closeClaw();
                        outtake.moveV4Bar(Outtake.V4BarState.Drop);
                        runtime.reset();
                        while(runtime.seconds() < 0.9){
                            outtake.moveSlidersPreset();
                        }
                        outtake.openClaw();
                        runtime.reset();
                        while (runtime.seconds() < 0.5){
                            outtake.moveSlidersPreset();
                        }
                        outtake.moveV4Bar(Outtake.V4BarState.Collect);
                        runtime.reset();
                        while (runtime.seconds() < 0.5){
                            outtake.moveSlidersPreset();
                        }
                        programstage = nextStage();
                    }
                    break;
                case choosePark:
                    if (parkZone == 1){
                        park = drive.trajectorySequenceBuilder(positionRobot.end())
                                .lineToConstantHeading(new Vector2d(90,-30))
                                .lineToConstantHeading(new Vector2d(20,-30))
                                .build();
                    }
                    else if (parkZone == 2){
                        park = drive.trajectorySequenceBuilder(positionRobot.end())
                                .lineToConstantHeading(new Vector2d(90,-30))
                                .build();
                    }
                    else
                        park = drive.trajectorySequenceBuilder(positionRobot.end())
                                .lineToConstantHeading(new Vector2d(90,-30))
                                .lineToConstantHeading(new Vector2d(155,-30))
                                .build();
                    programstage = nextStage();
                    drive.followTrajectorySequenceAsync(park);
                    break;*/
                case gotoPark:
                    if (drive.isBusy())
                        programstage = thisStage();
                    else programstage = nextStage();
                    break;
                case stopRobot:
                    if (!outtake.collect)
                        outtake.lowerSlidersAuto();
                    while (!Hardware.limitSwitch1.getState());
                    outtake.resetEncoder();
                    terminateOpModeNow();
                    break;
            }

        }
    }



}