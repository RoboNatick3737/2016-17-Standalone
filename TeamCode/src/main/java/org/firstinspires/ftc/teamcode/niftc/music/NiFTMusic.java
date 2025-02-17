package org.firstinspires.ftc.teamcode.niftc.music;

import android.media.MediaPlayer;

import org.firstinspires.ftc.teamcode.niftc.NiFTBase;
import org.firstinspires.ftc.teamcode.niftc.console.NiFTConsole;
import org.firstinspires.ftc.teamcode.niftc.threads.NiFTFlow;

/**
 * NiFTMusic enables the easy playing of audio files which are placed in the FtcRobotController/res/raw folder.  This functionality is helpful for both debugging and showing the other teams that you've got swag.
 */
public class NiFTMusic
{
    /**
     * The enum which encapsulates the res/raw folder (make sure to pre-register songs here!)
     */
    public enum AudioFiles
    {
        IMPERIAL_MARCH
    }

    private static MediaPlayer mediaPlayer = null;

    /**
     * Calling play initializes the media player with the given app context and starts playing a song.
     *
     * @param choice the pre-registered enum option which represents the file in question.
     */
    public static void play (AudioFiles choice)
    {
        try
        {
            int selectedSong = com.qualcomm.ftcrobotcontroller.R.raw.imperialmarch;

            //Add new mp3s here.
            switch (choice)
            {
                case IMPERIAL_MARCH:
                    selectedSong = com.qualcomm.ftcrobotcontroller.R.raw.imperialmarch;
                    break;
            }

            mediaPlayer = MediaPlayer.create (NiFTBase.opModeInstance.hardwareMap.appContext, selectedSong);
            mediaPlayer.start ();
            mediaPlayer.setOnCompletionListener (new MediaPlayer.OnCompletionListener ()
            {
                public void onCompletion (MediaPlayer mediaPlayer1)
                {
                    mediaPlayer1.release ();
                }
            });

            NiFTConsole.outputNewSequentialLine ("Playing " + choice.toString ());

            NiFTFlow.pauseForMS (1000); //Give the MediaPlayer some time to initialize, and register that a song is being played.
        } catch (InterruptedException e)
        {/**/} //Exit immediately.
        catch (Exception e)
        {
            NiFTConsole.outputNewSequentialLine ("Music error: " + e.getMessage ());
        }
    }

    /**
     * Calling quit() stops the currently playing media player, if it is playing.
     */
    public static void quit ()
    {
        if (mediaPlayer != null)
        {
            if (mediaPlayer.isPlaying ())
                mediaPlayer.stop (); //stopEasyTask playing
            mediaPlayer.release (); //prevent resource allocation
            mediaPlayer = null; //nullify the reference.
        }
    }

    public static boolean playing ()
    {
        return mediaPlayer != null;
    }
}
