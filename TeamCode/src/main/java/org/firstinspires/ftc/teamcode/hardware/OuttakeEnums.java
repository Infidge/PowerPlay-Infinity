package org.firstinspires.ftc.teamcode.hardware;

public class OuttakeEnums {

    public enum OuttakeArmAngle {
        COLLECT_CONE(0.12),
        DROP_LOW_JUNCTION(0.85),
        DROP_MID_OR_HIGH_JUNCTION(0.85);

        private final double angle;

        OuttakeArmAngle(final double angle) {
            this.angle = angle;
        }

        double get() {
            return angle;
        }
    }

    public enum OuttakeClawPosition {
        OPEN(0.33),
        CLOSED(0.1);

        private final double position;

        OuttakeClawPosition(final double position) {
            this.position = position;
        }

        double get() {
            return position;
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

        int get() {
            return position;
        }
    }

    public enum OuttakeSlidersPower {
        HOLD(0.003),
        LOWER(-1.0),
        RAISE(1.0);

        private final double power;

        OuttakeSlidersPower(final double power) {
            this.power = power;
        }

        double get() {
            return power;
        }
    }

}
