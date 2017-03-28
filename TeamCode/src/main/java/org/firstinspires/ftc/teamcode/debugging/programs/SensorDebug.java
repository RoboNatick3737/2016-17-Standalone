package org.firstinspires.ftc.teamcode.debugging.programs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.autonomous.AutoBase;
import org.firstinspires.ftc.teamcode.debugging.ConsoleManager;

@Autonomous(name = "Sensor Debug", group = "Utility Group")

public class SensorDebug extends AutoBase
{
    //Called after runOpMode() has finished initializing.
    protected void driverStationSaysGO() throws InterruptedException
    {
        while (true) {
            ConsoleManager.outputConstantDataToDrivers(
                    new String[]
                    {
                            "Option 1 Color Sensor",
                            "ARGB: " + option1ColorSensor.argb() + " Alpha: " + option1ColorSensor.alpha(),
                            "Blue: " + option1ColorSensor.blue() + " Red: " + option1ColorSensor.red(),
                            "",
                            "Option 2 Color Sensor",
                            "ARGB: " + option2ColorSensor.argb() + " Alpha: " + option2ColorSensor.alpha(),
                            "Blue: " + option2ColorSensor.blue() + " Red: " + option2ColorSensor.red(),
                            "",
                            "Bottom Color Sensor",
                            "ARGB: " + bottomColorSensor.argb() + " Alpha: " + bottomColorSensor.alpha(),
                            "Blue: " + bottomColorSensor.blue() + " Red: " + bottomColorSensor.red(),
                            "",
                            "Heading: " + getValidGyroHeading(),
                            "",
                            "Front Range Sensor: " + frontRangeSensor.cmUltrasonic(),
                            "Back Range Sensor: " + sideRangeSensor.cmUltrasonic()
                    }
            );
            idle();
        }
    }
}