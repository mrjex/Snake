package Backend;

import Frontend.Controller;
import javafx.scene.control.ProgressBar;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;

import static Frontend.Controller.scene;

// Sources retrieved:
// https://stackoverflow.com/questions/12908412/print-hello-world-every-x-seconds (2022-12-15): The one with 210+ upvotes
// https://www.youtube.com/watch?v=wJO_cq5XeSA (Retrieved 2022-12-15)

        /*
            // Ideas for improvement that will be implemented by JoelM soon:
            * Make the relationship between this class and MainGame.java clean and readable (smooth code and good comments)
         */

public class SongUtils
{
    // Note: The values of the variables below are not assigned in the constructor,
    // because they are static, meaning they belong to the class itself, not the instances or objects we create of it
    public static Clip currentClip;
    public static String[] songFilePaths = {"Lazy Love - KEM.wav", "Robots - Pecan Pie.wav", "Energy I Need - Pecan Pie.wav", "Sky High - Trinity.wav", "Music Is - Pryces.wav", "Bees In The Garden - Moire.wav"};
    public static String[] musicThemePlaylists = {"Chill", "Trap"};

    public static String currentList = "Chill";
    // public static int[] listIndices = new int[musicThemePlaylists.length];
    public static String[] chillSongs = {"Lazy Love - KEM.wav", "Music Is - Pryces.wav", "Bees In The Garden - Moire.wav"};
    public static int currentListIndex = 0;
    public static Clip mostRecentClip = null;

    public static int[][] songListIndicesBoundaries;

    public static void startAudioClip(int indexOfList)
    {
        try
        {
            File musicPath = new File(chillSongs[indexOfList]);

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
                clip.start();

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

    public static void startAudioClip2()
    {
        try
        {
            File musicPath = new File(SongList.songs.get(SongList.songIndex));
            // File musicPath = new File(chillSongs[indexOfList]);

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
                clip.start();

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
    public static void toggleSong(boolean increase) // Note for JoelM: Put in SongList.java in next commit
    {
        System.out.println("Before: " + SongList.songIndex);

        if (increase)
        {
            SongList.songIndex++;
            SongList.songIndex = Utils.limitValue(true, SongList.songIndex, songListIndicesBoundaries[SongList.listIndex]);
        }
        else
        {
            SongList.songIndex--;
            SongList.songIndex = Utils.limitValue(false, SongList.songIndex, songListIndicesBoundaries[SongList.listIndex]);
        }

        // listIndices[currentListIndex] %= chillSongs.length; // Isn't general: Only works for 1 list. Solution: 2D array - Requires every list to have same length. Otherwise, Solution: Jagged 2D array
        System.out.println("After: " + SongList.songIndex);

        startAudioClip2();
        Utils.updateText(Controller.scene, "#songNameText", SongList.songs.get(SongList.songIndex), true);
    }

    public static void toggleSongAudio(boolean pause)
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
        double barProgression = (Utils.setCommaNDigitsFromEnd(SongUtils.currentClip.getMicrosecondPosition(), 3) / (double) Utils.setCommaNDigitsFromEnd(SongUtils.currentClip.getMicrosecondLength(), 3));

        // Toggle/Play next song if the current song has completed
        if (barProgression == 1)
        {
            SongUtils.toggleSong(true);
        }

        ProgressBar bar = (ProgressBar) (Controller.scene.lookup("#songProgressBar"));
        bar.setProgress(barProgression);
    }
}
