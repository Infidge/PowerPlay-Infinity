package org.firstinspires.ftc.teamcode.hardware;

public class IntakeEnums {

    public enum IntakeArmAngle {
        PICKUP_CONE(0.5),
        TRANSFER_CONE(0.5);

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
        TRANSFER_CONE(0.5);

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

    public enum IntakeSlidersPower {
        RETRACT(0.5),
        EXTEND(-0.5);
        
        private final double power;
        
        IntakeSlidersPower(final double power) {
            this.power = power;
        }
        
        double get() {
            return power;
        }
    }
    
}
