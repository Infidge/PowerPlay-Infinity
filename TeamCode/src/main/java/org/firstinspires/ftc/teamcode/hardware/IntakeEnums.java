package org.firstinspires.ftc.teamcode.hardware;

public class IntakeEnums {

    public enum IntakeArmAngle {
        PICKUP_CONE(0.5),
        TRANSFER_CONE(0.5);

        private final double angle;

        IntakeArmAngle(final double angle) {
            this.angle = angle;
        }

        double getAngle() {
            return angle;
        }
    }

    public enum IntakeClawAngle {
        PICKUP_CONE(0.5),
        TRANSFER_CONE(0.5);

        private final double angle;

        IntakeClawAngle(final double angle) {
            this.angle = angle;
        }

        double getAngle() {
            return angle;
        }
    }

    public enum IntakeClawState {
        OPEN(0.46),
        CLOSED(0.0);

        private final double position;

        IntakeClawState(final double position) {
            this.position = position;
        }

        double getPosition() {
            return position;
        }
    }

}
