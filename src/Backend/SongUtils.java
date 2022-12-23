package Backend;

import Frontend.Controller;
import javafx.scene.control.ProgressBar;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

// Sources retrieved:
// https://stackoverflow.com/questions/12908412/print-hello-world-every-x-seconds (2022-12-15): The one with 210+ upvotes
// https://www.youtube.com/watch?v=wJO_cq5XeSA (Retrieved 2022-12-15)

        /*
            // Ideas for improvement that will be implemented by JoelM soon:
            * Make the relationship between this class and MainGame.java clean and readable (smooth code and good comments)
         */

public class SongUtils
{
    public static Clip currentClip;
    public static Clip mostRecentClip = null;

    public static int[][] getSongListIndicesBoundaries()
    {
        // [3, 2] ---> [0, 2], [3, 4]
        // [3, 2, 3, 4, 2] ---> [0, 2], [3, 4], [5, 7], [8, 11], [12, 13]

        int[][] listBoundaries = new int[SongList.numberOfSongsInList.length][];

        int min = 0;
        int max = SongList.numberOfSongsInList[0] - 1;

        for (int i = 0; i < SongList.numberOfSongsInList.length - 1; i++)
        {
            listBoundaries[i] = new int[]{min, max};

            min += SongList.numberOfSongsInList[i];
            max += SongList.numberOfSongsInList[i + 1];
        }

        listBoundaries[listBoundaries.length - 1] = new int[]{min, max};
        return listBoundaries;
    }

    public static void startAudioClip(boolean songIsPaused)
    {
        try
        {
            File musicPath = new File(SongList.songs.get(SongList.songIndex));

            if (musicPath.exists())
            {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();

                if (mostRecentClip != null)
                {
                    mostRecentClip.close();
                    mostRecentClip.stop();
                }

                clip.open(audioInput);

                if (!songIsPaused)
                {
                    clip.start();
                }

                currentClip = clip;
                mostRecentClip = clip;
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

    public static void stop()
    {
        currentClip.close();
        currentClip.stop();
    }

    // Toggle song in current song list: Is executed when user presses 'Q', 'E' or when song is finished
    public static void toggleSong(boolean increase, boolean songIsPaused) // Note for JoelM: Put in SongList.java in next commit
    {
        // User clicks 'E' and moves on to the next song in the current song list
        if (increase)
        {
            SongList.songIndex++;
            SongList.songIndex = Utils.limitValue(SongList.songIndex, SongList.songListIndicesBoundaries[SongList.listIndex]);
        }

        // User clicks 'Q' and goes back to the previous song
        else
        {
            SongList.songIndex--;
            SongList.songIndex = Utils.limitValue(SongList.songIndex, SongList.songListIndicesBoundaries[SongList.listIndex]);
        }

        startAudioClip(songIsPaused);
        Utils.updateText(Controller.scene, "#songNameText", SongList.songs.get(SongList.songIndex), true);
    }

    public static void togglePause(boolean pause)
    {
        if (pause)
        {
            currentClip.stop();
        }
        else
            currentClip.start();
    }

    public static void updateSongBarProgression()
    {
        double barProgression = (Utils.setCommaNDigitsFromEnd(currentClip.getMicrosecondPosition(), 3) / (double) Utils.setCommaNDigitsFromEnd(currentClip.getMicrosecondLength(), 3));

        // Toggle/Play next song if the current song has completed
        if (barProgression == 1)
        {
            toggleSong(true, false);
            SongList.synchronizeThumbnailWithSong();
        }

        ProgressBar bar = (ProgressBar) (Controller.scene.lookup("#songProgressBar"));
        bar.setProgress(barProgression);
    }
}
