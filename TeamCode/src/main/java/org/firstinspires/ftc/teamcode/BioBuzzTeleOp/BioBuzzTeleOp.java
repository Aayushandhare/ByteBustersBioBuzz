package org.firstinspires.ftc.teamcode.BioBuzzTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Mechanisms.ByteBustersBee;

@TeleOp
public class BioBuzzTeleOp extends OpMode {
    ByteBustersBee byteBustersBee = new ByteBustersBee();

    @Override
    public void init() {
        byteBustersBee.init(hardwareMap);

    }

    public void loop() {
        byteBustersBee.setFrontRightMotorSpeed(0.5);
        telemetry.addData("Motor Revs", byteBustersBee.getMotorRevs());
    }
}

