package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.examples.AprilTagDetectionPipeline;
import org.firstinspires.ftc.teamcode.hardware.Hardware_Baciu;
import org.firstinspires.ftc.teamcode.hardware.Intake_Baciu;
import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeArmAngle;
import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeClawPosition;
import org.firstinspires.ftc.teamcode.hardware.OuttakeEnums.OuttakeSlidersPosition;
import org.firstinspires.ftc.teamcode.hardware.Outtake_Baciu;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@Autonomous(name = "Auto_Baciu", group = "Baciu")
public class Auto_Baciu extends LinearOpMode {

    private enum FSMStages {
        scanAprilTag,
        positionRobot,
        waitToPositionRobot,
        outtakeConePreload,
        intakeCone1,
        outtakeCone1,
        intakeCone2,
        outtakeCone2,
        parkRobot,
        stopRobot
    }

    private final FSMStages[] fsmStages = FSMStages.values();
    private int fsmStage = 0;

    private final Intake_Baciu intake = new Intake_Baciu();
    private final Outtake_Baciu outtake = new Outtake_Baciu();

    @Override
    public void runOpMode() throws InterruptedException {
        Hardware_Baciu.init(hardwareMap);

        intake.init();
        outtake.init();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvCamera camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        double tagSize = 0.166;
        double fx = 578.272;
        double fy = 578.272;
        double cx = 402.145;
        double cy = 221.506;

        AprilTagDetectionPipeline aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagSize, fx, fy, cx, cy);

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(800, 448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {}
        });

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d startPose = new Pose2d(90, -159, Math.toRadians(270));
        drive.setPoseEstimate(startPose);

        Trajectory positionRobot = drive.trajectoryBuilder(startPose, true)
                .lineToLinearHeading(new Pose2d(92.5, -92.5, Math.toRadians(135)))
                .build();

        int[] tagsOfInterest = new int[] {13, 19, 12};
        ElapsedTime tagDetectionTime = new ElapsedTime();
        boolean tagFound = false;
        int parkZone = 2;

        waitForStart();

        telemetry.setMsTransmissionInterval(50);

        tagDetectionTime.reset();

        while (opModeIsActive()) {
            drive.update();
            intake.tickLimitSwitch();
            intake.tickSliders();
            outtake.tickLimitSwitch();
            outtake.tickSliders();

            switch (fsmStages[fsmStage]) {
                case scanAprilTag:
                    while (!tagFound && tagDetectionTime.seconds() < 0.2) {
                        ArrayList<AprilTagDetection> detections = aprilTagDetectionPipeline.getDetectionsUpdate();

                        if (detections.size() != 0) {
                            for (AprilTagDetection tag : detections) {
                                for (int i = 0; i < 3; i++) {
                                    if (tag.id == tagsOfInterest[i]) {
                                        parkZone = i;
                                        tagFound = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    fsmStage++;
                    break;
                case positionRobot:
                    drive.followTrajectoryAsync(positionRobot);
                    fsmStage++;
                    break;
                case waitToPositionRobot:
                    if (!drive.isBusy()) fsmStage++;
                    break;
                case outtakeConePreload:
                    outtake.moveSliders(OuttakeSlidersPosition.MID_JUNCTION);
                    outtake.moveArm(OuttakeArmAngle.DROP_MID_OR_HIGH_JUNCTION);
                    while (!outtake.isStabilizingSliders()) {
                        outtake.tickLimitSwitch();
                        outtake.tickSliders();
                    }
                    outtake.moveClaw(OuttakeClawPosition.OPEN);
                    break;
                case intakeCone1:

                    break;
            }
        }
    }

}
