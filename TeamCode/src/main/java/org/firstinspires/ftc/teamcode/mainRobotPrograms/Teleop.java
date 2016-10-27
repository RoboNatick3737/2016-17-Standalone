package org.firstinspires.ftc.teamcode.mainRobotPrograms;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import java.util.ResourceBundle;

/**
 * TeleOp Mode
 * <p/>
 * Enables control of the robot via the gamepad
 */

//Add the teleop to the op mode register.
@TeleOp(name="Teleop", group="Teleop Group")
//@Disabled

public class Teleop extends RobotBase {

    @Override
    protected void driverStationSaysINITIALIZE()
    {
    }

    private enum ControlMode
    {
        RACE_CAR,
        NORMAL
    }
    protected void driverStationSaysGO() throws InterruptedException
    {
        //Audio Control Variables
        float lastTimeAudioTogglePressed = 0;

        //Normal mode variables
        double left, right;
        boolean backwards = false;
        ControlMode currentControlMode = ControlMode.NORMAL;
        double lastTimeToggleDirectionPressed = 0;

        //Race Car Mode Variables
        double raceCarPower = 0;

        //Toggle Variables
        double lastTimeDriveModeTogglePressed = 0;

        //Keep looping while opmode is active (waiting a hardware cycle after all of this is completed, just like loop()).
        while (true)
        {
            /******************** DRIVING CONTROL ********************/

            //Toggle Drive Mode
            if (gamepad1.x && (System.currentTimeMillis() - lastTimeDriveModeTogglePressed) > 1000)
            {
                if (currentControlMode == ControlMode.RACE_CAR)
                    currentControlMode = ControlMode.NORMAL;
                else
                    currentControlMode = ControlMode.RACE_CAR;

                OutputToDriverStation("Swapped to " + currentControlMode.toString() + " contro mode after getting X Press");

                lastTimeDriveModeTogglePressed = System.currentTimeMillis();
            }

            if (currentControlMode == ControlMode.NORMAL)
            {
                //Driving Toggle
                if (backwards)
                { // Driving backwards
                    left = -gamepad1.right_stick_y;
                    right = -gamepad1.left_stick_y;
                } else
                { // Driving forwards
                    left = gamepad1.left_stick_y;
                    right = gamepad1.right_stick_y;
                }

                // clip the right/left values so that the values never exceed +/- 1
                right = Range.clip(right, -1, 1);
                left = Range.clip(left, -1, 1);

                // Write the values to the motors.  Scale the robot in order to run the robot more effectively at slower speeds.
                frontLeft.setPower(scaleInput(left));
                frontRight.setPower(scaleInput(right));
                backLeft.setPower(scaleInput(left));
                backRight.setPower(scaleInput(right));

                //Wait a second before switching to backwards again (can only toggle once every second).
                if (gamepad1.a && (System.currentTimeMillis() - lastTimeToggleDirectionPressed) > 1000)
                {
                    backwards = !backwards; // Switch driving direction
                    lastTimeToggleDirectionPressed = System.currentTimeMillis();
                }
            }
            else if (currentControlMode == ControlMode.RACE_CAR)
            {
                //Get the power of the system.
                raceCarPower = gamepad1.right_trigger - gamepad1.left_trigger;

                double differenceFactor = (gamepad1.dpad_right ? 0.25 : 0) - (gamepad1.dpad_left ? 0.25 : 0);
                double leftPower = 0.75 * raceCarPower - differenceFactor;
                double rightPower = 0.75 * raceCarPower + differenceFactor;

                frontLeft.setPower(leftPower);
                backLeft.setPower(leftPower);
                frontRight.setPower(rightPower);
                backRight.setPower(rightPower);
            }

            /********************* AUDIO CONTROL ************************/
            if (gamepad1.y && (System.currentTimeMillis() - lastTimeAudioTogglePressed) > 1000)
            {
                if (CurrentlyPlayingAudio())
                {
                    StopPlayingAudio();
                    OutputToDriverStation("Got Y toggle, stopped playing audio.");
                }
                else
                {
                    PlayAudio(DownloadedSongs.values()[(int) (Math.random() * DownloadedSongs.values().length)]);
                    OutputToDriverStation("Got Y toggle, started playing a new song.");
                }
                lastTimeAudioTogglePressed = System.currentTimeMillis();
            }

            idle();
        }
    }

    protected void driverStationSaysSTOP ()
    {
    }

    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */

    double scaleInput(double dVal) {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);
        if (index < 0) {
            index = -index;
        } else if (index > 16) {
            index = 16;
        }

        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        return dScale;
    }

}