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
    public static Clip currentClip;
    public static String[] songFilePaths = {"Lazy Love - KEM.wav", "Robots - Pecan Pie.wav", "Energy I Need - Pecan Pie.wav", "Sky High - Trinity.wav", "Music Is - Pryces.wav", "Bees In The Garden - Moire.wav"};
    public static String[] musicThemePlaylists = {"Chill", "Trap"};


    public static String currentList = "Chill";
    public static int[] listIndices = new int[musicThemePlaylists.length];
    public static String[] chillSongs = {"Lazy Love - KEM.wav", "Music Is - Pryces.wav", "Bees In The Garden - Moire.wav"};
    public static int currentListIndex = 0;
    public static Clip mostRecentClip = null;

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

    public static void getSongListIndicesBoundaries()
    {
        // [3, 2] ---> [0, 2], [3, 4]
        // [3, 2, 3, 4, 2] ---> [0, 2], [3, 4], [5, 7], [8, 11], [12, 13]

        int[][] output = new int[SongList.numberOfSongsInList.length][];

        int min = 0;
        int max = SongList.numberOfSongsInList[0] - 1;

        for (int i = 0; i < SongList.numberOfSongsInList.length - 1; i++)
        {
            output[i] = new int[]{min, max};

            min += SongList.numberOfSongsInList[i];
            max += SongList.numberOfSongsInList[i + 1];
        }

        output[output.length - 1] = new int[]{min, max};
    }

    public static void startAudioClip2(int indexOfList)
    {
        try
        {
            // File musicPath = new File(SongList.songs.get(SongList.songIndex));

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

    public static void changeSong(boolean increase)
    {
        if (increase)
        {
            listIndices[currentListIndex]++;
        }
        else
        {
            if (listIndices[currentListIndex] == 0) // JavaFX sucks - They calculate (-1 % 3) = -1, which is wrong..
                listIndices[currentListIndex] = chillSongs.length - 1;
            else
                listIndices[currentListIndex]--;
        }

        listIndices[currentListIndex] %= chillSongs.length; // Isn't general: Only works for 1 list. Solution: 2D array - Requires every list to have same length. Otherwise, Solution: Jagged 2D array
        System.out.println(chillSongs[listIndices[currentListIndex]]);

        startAudioClip(listIndices[currentListIndex]);
        Utils.updateText(scene, "#songNameText", chillSongs[listIndices[currentListIndex]], true);
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
        double barProgression = (SongUtils.setCommaNDigitsFromEnd(SongUtils.currentClip.getMicrosecondPosition(), 3) / (double) SongUtils.setCommaNDigitsFromEnd(SongUtils.currentClip.getMicrosecondLength(), 3));

        if (barProgression == 1)
        {
            System.out.println("Song is done!");
            SongUtils.changeSong(true);
        }

        ProgressBar bar = (ProgressBar) (Controller.scene.lookup("#songProgressBar"));
        bar.setProgress(barProgression);
    }
}
