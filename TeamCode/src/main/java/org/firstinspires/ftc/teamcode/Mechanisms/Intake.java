package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

//push to repo
public class Intake {
    private DcMotor frontIntake,backIntake, pollenShooter,nectarShooter;
    private CRServo pollenServo, nectarServo;
    private Servo pollenSwitch,nectarSwitch,frontSorter,backSorter;
    private DigitalChannel distanceNectar, distancePollen;
    private Servo ledShooterReady;

    public void init(HardwareMap hwMap)
        {
            //Motors
            frontIntake = hwMap.get(DcMotor.class, "motor_intake_front");
            backIntake = hwMap.get(DcMotor.class, "motor_intake_back");
            pollenShooter = hwMap.get(DcMotor.class, "motor_shooter_pollen");
            nectarShooter = hwMap.get(DcMotor.class, "motor_shooter_nectar");

            //CRServos
            nectarServo = hwMap.get(CRServo.class,"servo_lift_nectar");
            pollenServo = hwMap.get(CRServo.class, "servo_lift_pollen");

            //Servo
            pollenSwitch = hwMap.get(Servo.class, "switch_pollen");
            nectarSwitch = hwMap.get(Servo.class, "switch_nectar");
            frontSorter = hwMap.get(Servo.class, "front_sorter");
            backSorter = hwMap.get(Servo.class, "back_sorter");
            //The front sorter is mounted facing the other way, so REVERSE it.
            //Now setPosition(1) moves it the opposite way from the back sorter
            //(the SDK turns position p into 1 - p for us).
            frontSorter.setDirection(Servo.Direction.REVERSE);

            //LED - goBILDA RGB Indicator Light, configured as a "Servo" on a servo port
            ledShooterReady = hwMap.get(Servo.class, "shooter_ready");

            //Sensors - these are plugged into DIGITAL ports, so they only report
            //on/off ("something there" or "nothing there"), not a distance.
            distanceNectar = hwMap.get(DigitalChannel.class, "distance_nectar");
            distancePollen = hwMap.get(DigitalChannel.class, "distance_pollen");
            //INPUT means we read from the sensor (we don't send it signals).
            distanceNectar.setMode(DigitalChannel.Mode.INPUT);
            distancePollen.setMode(DigitalChannel.Mode.INPUT);

        }


        public void setPollenServoSpeed(double speed)
        {
            pollenServo.setPower(speed);
        }
        public void setNectarServoSpeed(double speed)
        {
            nectarServo.setPower(speed);
        }
        public void setFrontIntakeMotorSpeed(double speed)
        {
            //accepts values from -1.0 to 1.0
            frontIntake.setPower(speed);
        }
        public void setbackIntakeMotorSpeed(double speed)
        {
            //accepts values from -1.0 to 1.0
            backIntake.setPower(speed);
        }
    public void setNectarShooterSpeed(double speed)
    {
        //accepts values from -1.0 to 1.0
        nectarShooter.setPower(speed);
    }
    public void setPollenShooterSpeed(double speed)
    {
        //accepts values from -1.0 to 1.0
        pollenShooter.setPower(speed);
    }

    // ---------------- Switch and sorter servos ----------------
    // These are normal (positional) servos, so the value is a position from 0.0 to 1.0,
    // not a speed. Both servos in each pair always move together.
    public void setSwitchPosition(double position)
    {
        pollenSwitch.setPosition(position);
        nectarSwitch.setPosition(position);
    }
    public void setSorterPosition(double position)
    {
        frontSorter.setPosition(position);
        backSorter.setPosition(position);
    }

    // ---------------- Distance sensors (digital) ----------------
    // We use goBILDA Laser Distance Sensors (1m range) in DIGITAL mode.
    // getState() gives true (high voltage) or false (low voltage).
    // goBILDA says the output is HIGH when an object is closer than the threshold,
    // and LOW (0V) when nothing is there, so DETECTED_WHEN_LOW is false.
    // The threshold (about 25-264 mm) is set with the small knob (potentiometer) on the sensor,
    // NOT in code. Turning the knob all the way clockwise puts it in analog mode, which this code doesn't use.
    public static final boolean DETECTED_WHEN_LOW = false;

    public boolean isNectarDetected()
    {
        return distanceNectar.getState() != DETECTED_WHEN_LOW;
    }
    public boolean isPollenDetected()
    {
        return distancePollen.getState() != DETECTED_WHEN_LOW;
    }
    // Raw signals straight from the digital ports (true = high voltage, false = 0V).
    // Shown on telemetry to help find wiring/config/mode problems.
    public boolean getNectarRawState()
    {
        return distanceNectar.getState();
    }
    public boolean getPollenRawState()
    {
        return distancePollen.getState();
    }
    // True when EITHER sensor sees something.
    public boolean isAnythingDetected()
    {
        return isNectarDetected() || isPollenDetected();
    }

    // ---------------- LED ----------------
    // We use the goBILDA RGB Indicator Light (PWM). It is controlled like a SERVO:
    // the "position" picks the color. Values from goBILDA's chart:
    //   0.0 = off, 0.277 = red, 0.333 = orange, 0.388 = yellow, 0.444 = sage,
    //   0.500 = green, 0.555 = azure, 0.611 = blue, 0.666 = indigo, 0.722 = violet, 1.0 = white
    public static final double LED_RED = 0.277;
    public static final double LED_GREEN = 0.500;

    public void setLedGreen()
    {
        ledShooterReady.setPosition(LED_GREEN);
    }
    public void setLedRed()
    {
        ledShooterReady.setPosition(LED_RED);
    }


}
