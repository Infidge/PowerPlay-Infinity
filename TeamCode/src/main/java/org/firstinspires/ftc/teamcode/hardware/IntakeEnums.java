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

    public enum IntakeSlidersMovement {
        RETRACT(0.5),
        EXTEND(-0.5);
        
        private final double power;
        
        IntakeSlidersMovement(final double power) {
            this.power = power;
        }
        
        double getPower() {
            return power;
        }
    }
    
}
