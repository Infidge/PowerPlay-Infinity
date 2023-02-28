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
import org.openftc.apriltag.AprilTagDetection;

import java.util.ArrayList;

@Autonomous(name="Auto_Info1", group="Roadrunner")
public class Auto_Info1 extends LinearOpMode {

    private enum robotStage{
        scan,
        positionRobot,
        dropCone,
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
    ElapsedTime runtime = new ElapsedTime();
    ElapsedTime detectionTime = new ElapsedTime();
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

        Trajectory positionRobot;
        Trajectory park = null;

        positionRobot = drive.trajectoryBuilder(startPose, true)
                .lineToLinearHeading(new Pose2d(92.5,-92.5, Math.toRadians(135)))
                .build();
        waitForStart();
        drive.followTrajectoryAsync(positionRobot);
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
            intake.armGoToPreset();
            intake.angleGoToPreset();
            outtake.moveSlidersPreset();
            switch (robotStageValues[programstage]){
                case scan:
                    while (!aprilTag.tagFound && detectionTime.seconds() < 0.2) {
                        ArrayList<AprilTagDetection> currentDetections = aprilTag.aprilTagDetectionPipeline.getLatestDetections();
                        if (currentDetections.size() != 0) {
                            for (AprilTagDetection tag : currentDetections) {
                                if (tag.id == id1) {
                                    parkZone = 1;
                                    aprilTag.tagFound = true;
                                    break;
                                }
                                else if (tag.id == id3){
                                    parkZone = 3;
                                    aprilTag.tagFound = true;
                                    break;
                                }
                                else {
                                    parkZone = 2;
                                    aprilTag.tagFound = true;
                                    break;
                                }
                            }
                        }
                    }
                    programstage = nextStage();
                    drive.followTrajectoryAsync(positionRobot);
                    break;
                case positionRobot:
                    if (outtake.collect)
                        outtake.collect = false;
                    outtake.sliderPos = Constant.mid;
                    outtake.moveV4Bar(Outtake.V4BarState.Drop);
                    if (drive.isBusy() || Math.abs(Hardware.slider1.getPower()) != Constant.stopSlider)
                        programstage = thisStage();
                    else programstage = nextStage();
                    break;
                case dropCone:
                    outtake.openClaw();
                    runtime.reset();
                    while (runtime.seconds() < 0.5){
                        outtake.moveSlidersPreset();
                    }
                    outtake.moveV4Bar(Outtake.V4BarState.Collect);
                    outtake.lowerSlidersAuto();
                    runtime.reset();
                    while (runtime.seconds() < 1.0);
                    programstage = nextStage();
                    break;
                case choosePark:
                    if (parkZone == 1){
                        park = drive.trajectoryBuilder(positionRobot.end())
                                .lineToLinearHeading(new Pose2d(30,-90,Math.toRadians(90)))
                                .build();
                    }
                    else if (parkZone == 2){
                        park = drive.trajectoryBuilder(positionRobot.end())
                                .lineToLinearHeading(new Pose2d(90,-90,Math.toRadians(90)))
                                .build();
                    }
                    else
                        park = drive.trajectoryBuilder(positionRobot.end())
                                .lineToLinearHeading(new Pose2d(150,-90,Math.toRadians(90)))
                                .build();
                    programstage = nextStage();
                    drive.followTrajectoryAsync(park);
                    break;
                case gotoPark:
                    if (drive.isBusy())
                        programstage = thisStage();
                    else programstage = nextStage();
                    break;
                case stopRobot:
                    terminateOpModeNow();
                    break;
            }
        }
    }



}