package org.firstinspires.ftc.teamcode.BioBuzzAuto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class HelloWorldBioBuzz extends OpMode {

    @Override
    public void init() {

        telemetry.addData("Hello", "World Bio BUZZ");
    }

    @Override
    public void loop() {

    }
}
