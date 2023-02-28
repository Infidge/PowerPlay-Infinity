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

@Autonomous(name="Auto_Move", group="Roadrunner")
public class Auto_Move extends LinearOpMode {

    private enum robotStage{
        positionRobot,
        positionRobot2,
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
    ElapsedTime detectionTime = new ElapsedTime();
    ElapsedTime armMovementTime = new ElapsedTime();
    ElapsedTime clawTime = new ElapsedTime();
    ElapsedTime sliderTime = new ElapsedTime();
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
                .lineToLinearHeading(new Pose2d(90,-30,Math.PI + 0.244978663))
                .build();
        positionRobot2 = drive.trajectoryBuilder(positionRobot.end(), true)
                .lineToLinearHeading(new Pose2d(100,-52,Math.PI + 0.244978663))
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
            switch (robotStageValues[programstage]){
                case positionRobot:
                    if(drive.isBusy())
                        programstage = thisStage();
                    else {
                        drive.followTrajectoryAsync(positionRobot2);
                        programstage = nextStage();
                    }
                    break;
                case positionRobot2:
                    if (drive.isBusy())
                        programstage = thisStage();
                    else
                        programstage = nextStage();
                    break;
                case stopRobot:
                    terminateOpModeNow();
                    break;
            }
        }
    }



}