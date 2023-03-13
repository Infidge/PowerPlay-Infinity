package org.firstinspires.ftc.teamcode.hardware;

public class IntakeEnums {

    public enum IntakeArmAngle {
        PICKUP_CONE(0.5),
        TRANSFER_CONE(0.5),

        PICKUP_CONE_1(0.5),
        PICKUP_CONE_2(0.5);

        private final double angle;

        IntakeArmAngle(final double angle) {
            this.angle = angle;
        }

        double get() {
            return angle;
        }
    }

    public enum IntakeClawAngle {
        PICKUP_CONE(0.5),
        TRANSFER_CONE(0.5),

        PICKUP_CONE_1(0.5),
        PICKUP_CONE_2(0.5);

        private final double angle;

        IntakeClawAngle(final double angle) {
            this.angle = angle;
        }

        double get() {
            return angle;
        }
    }

    public enum IntakeClawPosition {
        OPEN(0.46),
        CLOSED(0.0);

        private final double position;

        IntakeClawPosition(final double position) {
            this.position = position;
        }

        double get() {
            return position;
        }
    }

    public enum IntakeSlidersPosition {
        AUTONOMOUS_CONE_STACK(100);

        private final int position;

        IntakeSlidersPosition(final int position) {
            this.position = position;
        }

        int get() {
            return position;
        }
    }

    public enum IntakeSlidersPower {
        HOLD(0.003),
        EXTEND(0.5),
        RETRACT(-0.5);
        
        private final double power;
        
        IntakeSlidersPower(final double power) {
            this.power = power;
        }
        
        double get() {
            return power;
        }
    }
    
}
