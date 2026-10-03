package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ByteBustersBee {

    //Define all the motors
    private DcMotor frontRightMotor;
    private double ticksPerRev; //revolution
    //Define all Servos


    //Define all Touch Sensors


            public void init(HardwareMap hwMap) {
    //************ initialization *****************//

        frontRightMotor = hwMap.get(DcMotor.class, "frontRightMotor");// "motor" needs to match exactly as in configuration
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        ticksPerRev =frontRightMotor.getMotorType().getTicksPerRev();
    }



    //****************************End Touch Sensor Code***********************

    //********************DC Motor initialization and methods ********************

    public void setFrontRightMotorSpeed(double speed){
                frontRightMotor.setPower(speed);
    }

public double getMotorRevs(){
                //normalizing ticks to revolutions
                return frontRightMotor.getCurrentPosition()/ticksPerRev;
}

}
