
package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class MecanumDrive {
    private DcMotor frontRightMotor, frontLeftMotor, backLeftMotor, backRightMotor;
    private  IMU imu;

    public void init(HardwareMap hardwareMap)
    {
        //************ DC Motors *****************
        frontLeftMotor = hardwareMap.get(DcMotor.class, "motor_fl");
        backLeftMotor = hardwareMap.get(DcMotor.class, "motor_bl");
        frontRightMotor = hardwareMap.get(DcMotor.class, "motor_fr");
        backRightMotor = hardwareMap.get(DcMotor.class, "motor_br");


        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);


//    frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//    backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//    frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//    backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        imu = hardwareMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);

        imu.initialize(new IMU.Parameters(RevOrientation));



        // "Motor FR" needs to match exactly as in configuration


        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void drive(double forward, double strafe, double rotate)
    {
        double frontLeftPower = forward + strafe + rotate;
        double backLeftPower = forward - strafe +rotate;
        double frontRightPower = forward - strafe - rotate;
        double backRightPower = forward + strafe - rotate;

        double maxPower = 1.0;
        double maxSpeed = 1.0;

        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));

        // frontLeftMotor.setPower(0.5);

        frontLeftMotor.setPower(maxSpeed *(frontLeftPower/maxPower));
        backLeftMotor.setPower(maxSpeed *(backLeftPower/maxPower));
        frontRightMotor.setPower(maxSpeed *(frontRightPower/maxPower));
        backRightMotor.setPower(maxSpeed * (backRightPower/maxPower));

    }
    public void driveFieldRelative(double forward, double strafe, double rotate)
    {

        double theta = Math.atan2(forward, strafe);
        double r = Math.hypot(strafe, forward);

        theta = AngleUnit.normalizeRadians(theta -
                imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        double newForward = r *Math.sin(theta);
        double newStrafe = r *Math.cos(theta);

        this.drive(newForward,newStrafe,rotate);


    }

    // ---------------- IMU helpers ----------------
    // Sets the robot's current heading as "0 degrees" (straight ahead for field-relative driving).
    public void resetYaw()
    {
        imu.resetYaw();
    }
    // The robot's heading in degrees, for telemetry.
    public double getHeadingDegrees()
    {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    /****************e.g setter methods remove later if not used anywhere******************/
    public void setFrontRightMotorSpeed(double speed)
    {
        //accepts values from -1.0 to 1.0
        frontRightMotor.setPower(speed);
    }
    public void setFrontLeftMotorSpeed(double speed)
    {
        //accepts values from -1.0 to 1.0
        frontLeftMotor.setPower(speed);
    }
    public void setBackLeftMotorSpeed(double speed)
    {
        //accepts values from -1.0 to 1.0
        backLeftMotor.setPower(speed);
    }
    public void setBackRightMotorSpeed(double speed)
    {
        //accepts values from -1.0 to 1.0
        backRightMotor.setPower(speed);
    }
/****************************End of setter methods*********************/
}
