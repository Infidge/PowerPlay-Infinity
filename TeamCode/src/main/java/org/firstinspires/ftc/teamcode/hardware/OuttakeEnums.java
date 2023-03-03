package org.firstinspires.ftc.teamcode.hardware;

public class OuttakeEnums {

    public enum OuttakeArm {
        COLLECT(0.12),
        DROP(0.85);

        private final double position;
        OuttakeArm(final double position) {
            this.position = position;
        }
        double getPosition() {
            return position;
        }
    }

    public enum OuttakeClaw {
        OPEN(0.33),
        CLOSED(0.1);

        private final double position;
        OuttakeClaw(final double position) {
            this.position = position;
        }
        double getPosition() {
            return position;
        }
    }

    public enum OuttakeSlidersState {
        MOVE(1.0),
        STOP(0.003);

        private final double power;
        OuttakeSlidersState(final double power) {
            this.power = power;
        }
        double getPower() {
            return power;
        }
    }

    public enum OuttakeSlidersPosition {
        LOW_JUNCTION(41),
        MID_JUNCTION(527),
        HIGH_JUNCTION(1000);

        private final int position;
        OuttakeSlidersPosition (final int position) {
            this.position = position;
        }
        int getPosition() {
            return position;
        }
    }

}
