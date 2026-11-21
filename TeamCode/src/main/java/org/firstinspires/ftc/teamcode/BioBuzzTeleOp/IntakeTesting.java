package org.firstinspires.ftc.teamcode.BioBuzzTeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Mechanisms.Intake;

@TeleOp
public class IntakeTesting extends OpMode  {
    Intake intake = new Intake();
    int a=1;
//adding comment to test pushing to the repo
    @Override
    public void init() {
        intake.init(hardwareMap);

    }

    @Override
    public void loop(){

        intake.setNectarServoSpeed(a);
        intake.setPollenServoSpeed(a);
        intake.setbackIntakeMotorSpeed(-1 * a);
        intake.setFrontIntakeMotorSpeed(-1*a);
        intake.setNectarShooterSpeed(1);
        intake.setPollenShooterSpeed(1);
       if(gamepad1.aWasPressed()){
          a *= -1;
         }
    }
}
