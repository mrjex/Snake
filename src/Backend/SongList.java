package Backend;

import Frontend.Controller;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class SongList
{
    public static int listIndex = 0;
    public static int songIndex = 0;

    // Have a number for each class that is the number of songs in the current list
    public static ArrayList<String> songs = new ArrayList<>();
    public static int[] numberOfSongsInList = {3, 2}; // Note for JoelM: Set its values in a more general way dependent on variables instead of magic numbers
    // Benefit of the list and the system of cooperating variables built around it: More freedom for the developers (us)
    // when developing the game: The number of songs in each list don't need to be the same --> Less restrictions for us
    // Using a 2D array - Would restrict us: Waste allocated space or same number of songs for each list?

    public static ArrayList<Integer> currentSongIndices = new ArrayList<>();

    public SongList(String[] newSongs, int listIndex)
    {
        addSongs(newSongs, listIndex);
    }

    public void addSongs(String[] newSongs, int listIndex)
    {
        songs.addAll(Arrays.asList(newSongs));
        currentSongIndices.add(SongUtils.songListIndicesBoundaries[listIndex][0]);
    }

    public static void toggleSongList(int newListIndex)
    {
        listIndex = newListIndex;
        songIndex = currentSongIndices.get(listIndex);

        // Play new song here
        SongUtils.startAudioClip2();
        Utils.updateText(Controller.scene, "#songNameText", songs.get(songIndex), true);
    }
}
