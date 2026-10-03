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

    private DigitalChannel touchSensor; //rename when use is decided.
            public void init(HardwareMap hwMap) {
    //************ initialization *****************//
        touchSensor = hwMap.get(DigitalChannel.class, "touch_sensor");//touch_sensor needs to match what's in your config file.
        touchSensor.setMode(DigitalChannel.Mode.INPUT);

        frontRightMotor = hwMap.get(DcMotor.class, "frontRightMotor");// "motor" needs to match exactly as in configuration
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        ticksPerRev =frontRightMotor.getMotorType().getTicksPerRev();
    }


       public boolean isTouchSensorPressed(){
            return !touchSensor.getState();
       }

       public boolean isTouchSensorReleased(){
            return touchSensor.getState();
       }
        public boolean getTouchSensorState(){
        return touchSensor.getState();
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
