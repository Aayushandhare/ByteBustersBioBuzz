package org.firstinspires.ftc.teamcode.BioBuzzTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.Mechanisms.MecanumDrive;

/*
 * Full driver-controlled mode: omni (mecanum) driving plus all intake controls.
 *
 * Gamepad 1 controls:
 *   Left stick      - drive forward/back and strafe left/right (field-relative)
 *   Right stick X   - turn
 *   A (tap)         - flip the direction of the intakes and lift servos
 *   Right bumper    - hold: switch servos go to 1, release: back to 0
 *   B               - hold: sorter servos go to 1, release: back to 0
 *   Back            - reset the IMU heading (do this while the robot faces away from you)
 *
 * LED: green when either distance sensor sees something, red when neither does.
 */
@TeleOp(name = "BioBuzz Full TeleOp")
public class BioBuzzFullTeleOp extends OpMode {

    MecanumDrive drive = new MecanumDrive();
    Intake intake = new Intake();

    // Starting speeds. Lift servos run at 1, intakes at -1.
    // These are FIELDS (outside loop()), so they keep their value between loops.
    // If they were declared inside loop() they would reset every time and A would never flip them.
    double liftServoSpeed = 1.0;
    double intakeSpeed = -1.0;

    @Override
    public void init() {
        // drive.init() sets the motor directions and initializes the IMU
        // with the hub's orienta
        // tion (logo UP, USB FORWARD).
        drive.init(hardwareMap);
        intake.init(hardwareMap);

        // Make the direction the robot faces right now count as "forward".
        drive.resetYaw();

        // Put the servos in their "not pressed" positions before the match starts.
        intake.setSwitchPosition(0);
        intake.setSorterPosition(0);

        telemetry.addLine("Initialized. Face the robot away from you before pressing START.");
    }

    @Override
    public void loop() {
        // ---------------- Drive ----------------
        // On the gamepad, pushing a stick UP gives a NEGATIVE y value, so we flip it
        // to make "stick up" mean "drive forward".
        double forward = gamepad1.left_stick_y;
        double strafe =-gamepad1.left_stick_x;
        double rotate =-gamepad1.right_stick_x;
        drive.driveFieldRelative(forward, strafe, rotate);

        // Back button: reset heading if field-relative driving gets turned around.
        if (gamepad1.backWasPressed()) {
            drive.resetYaw();
        }

        // ---------------- Intake direction (A toggles) ----------------
        // aWasPressed() is true for only ONE loop per tap, so holding A flips once, not over and over.
        // Multiplying by -1 flips the direction: 1 becomes -1, -1 becomes 1.
        if (gamepad1.aWasPressed()) {
            liftServoSpeed *= -1;
            intakeSpeed *= -1;
        }
        intake.setNectarServoSpeed(liftServoSpeed);
        intake.setPollenServoSpeed(liftServoSpeed);
        intake.setFrontIntakeMotorSpeed(intakeSpeed);
        intake.setbackIntakeMotorSpeed(intakeSpeed);

        // ---------------- Switch servos (right bumper) ----------------
        if (gamepad1.right_bumper) {
            intake.setSwitchPosition(0);
        } else {
            intake.setSwitchPosition(1);
        }

        // ---------------- Sorter servos (B) ----------------

        if (gamepad1.b) {
            intake.setSorterPosition(1);
        } else {
            intake.setSorterPosition(0);
        }

        // ---------------- LED from distance sensors ----------------
        if (intake.isAnythingDetected()) {
            intake.setLedGreen();
        }
        else  {
            intake.setLedRed();
        }


        // ---------------- Telemetry (shows on the Driver Station) ----------------
        telemetry.addData("Heading (deg)", "%.1f", drive.getHeadingDegrees());
        telemetry.addData("Lift servo speed", liftServoSpeed);
        telemetry.addData("Intake speed", intakeSpeed);
        telemetry.addData("Nectar sensor detects", intake.isNectarDetected());
        telemetry.addData("Pollen sensor detects", intake.isPollenDetected());
        telemetry.addData("Something detected", intake.isAnythingDetected());

        // Raw sensor signals, for troubleshooting.
        // A digital port only reads on/off, so code can't tell analog mode from digital mode.
        // Check the light on the sensor itself: red/green = digital mode, blue = analog mode.
        telemetry.addData("Nectar raw signal", intake.getNectarRawState() ? "HIGH" : "LOW (0V)");
        telemetry.addData("Pollen raw signal", intake.getPollenRawState() ? "HIGH" : "LOW (0V)");
        telemetry.addLine("Sensor mode: check the light ON the sensor - red/green = digital, blue = analog");
    }
}
