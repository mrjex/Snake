package Backend;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.TimerTask;

// Sources retrieved:
// https://stackoverflow.com/questions/12908412/print-hello-world-every-x-seconds (2022-12-15): The one with 210+ upvotes
// https://www.youtube.com/watch?v=wJO_cq5XeSA (2022-12-15)

        /*
            // Ideas for improvement that will be implemented by JoelM soon:
            * Once the list of predetermined songs is done, it will loop from the beginning again
            * Make the relationship between this class and MainGame.java clean and readable (smooth code and good comments)
         */

public class SongList extends TimerTask
{
    public static Clip currentClip;
    public static int numberOfSongsInList = 3;
    public static int currentSongIndex;
    public static long[] songDurations = new long[numberOfSongsInList];
    public static String[] songFilePaths = {"Sky High - Trinity.wav", "Robots - Pecan Pie.wav", "Energy I Need - Pecan Pie.wav"};

    private int i;
    private int numberOfTimesToRun;

    private int currentNumberOfRuns;

    public SongList(int i, int numberOfTimesToRun)
    {
        this.i = i;

        currentNumberOfRuns = 0;
        this.numberOfTimesToRun = numberOfTimesToRun;
    }

    @Override
    public void run()
    {
        playBackgroundSongs();

        // Stop repeatedly executing when the predetermined times to run is fulfilled
        if (++currentNumberOfRuns == numberOfTimesToRun)
        {
            cancel();
        }
    }

    private void playBackgroundSongs()
    {
        currentSongIndex = i;
        System.out.println("Song number " + String.valueOf((currentSongIndex + 1)) + " is playing!");

        createAudioClip(i, true);
    }

    public static void getSongDurations()
    {
        for (int j = 0; j < songFilePaths.length; j++)
        {
            createAudioClip(j, false);
        }
    }

    private static void createAudioClip(int index, boolean startClip)
    {
        try
        {
            File musicPath = new File(songFilePaths[index]);

            if (musicPath.exists())
            {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);

                if (startClip)
                {
                    clip.start();
                }

                currentClip = clip;
                songDurations[index] = setCommaNDigitsFromEnd(clip.getMicrosecondLength(), 3);
            }
            else
            {
                System.out.println("File was not found!");
            }
        }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
        }
    }

    // Source used: https://stackoverflow.com/questions/29012224/java-how-to-remove-last-numbers-of-a-long (retrieved 2022-12-15)
    // The purpose with this function is that Java completely ignores the comma located 3 digits from the last digit
    // when retrieving a clip's length using 'clip.getMicrosecondLength'

    // Note: My suggestion is that we make a class called "Utils" where we have other generalized
    // public static functions whose behaviours don't fit in classes with specific behaviour
    public static long setCommaNDigitsFromEnd(long number, int n)
    {
        return (long)(number / Math.pow(10, n));
    }

    public static void stop()
    {
        currentClip.close();
        currentClip.stop();
    }
}
