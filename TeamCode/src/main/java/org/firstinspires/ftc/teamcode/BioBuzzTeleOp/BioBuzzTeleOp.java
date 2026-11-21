package org.firstinspires.ftc.teamcode.BioBuzzTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

//import org.firstinspires.ftc.teamcode.Mechanisms.ByteBustersBee;
import org.firstinspires.ftc.teamcode.Mechanisms.MecanumDrive;
import org.firstinspires.ftc.teamcode.Mechanisms.Intake;

@TeleOp
public class BioBuzzTeleOp extends OpMode
{
    //   ByteBustersBee byteBustersBee = new ByteBustersBee();
    MecanumDrive drive = new MecanumDrive();
    Intake intake = new Intake();
    double forward, strafe, rotate;

    @Override
    public void init()
    {
        //byteBustersBee.init(hardwareMap);
        drive.init(hardwareMap);

        intake.init(hardwareMap);
    }

    public void loop() {
        int a = 1;

        //set intake servo and motor speed
        intake.setNectarServoSpeed(-1 * a);
        intake.setPollenServoSpeed(a);
        intake.setbackIntakeMotorSpeed(-1 * a);
        intake.setFrontIntakeMotorSpeed(-1 * a);

        // reverse if a button pressed
        if (gamepad1.a)
        {
           a *= -1;

        }

        telemetry.addData("Gamepad1.leftStick Y:", gamepad1.left_stick_y);
        //set Drive controls
        forward = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        telemetry.addData("Gamepad1.leftStick X:", gamepad1.left_stick_x);

        drive.driveFieldRelative(forward, strafe, rotate);


    }
}




