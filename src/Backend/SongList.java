package Backend;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class SongList
{
    public static int listIndex = 0;
    public static int songIndex = 0;

    // Have a number for each class that is the number of songs in the current list
    public static ArrayList<String> songs = new ArrayList<>();
    public static int[] numberOfSongsInList = {3, 2, 3, 4, 2}; // Note for JoelM: Set its values in a more general way dependent on variables instead of magic numbers
    // Original: [3, 2]

    public SongList(String[] newSongs)
    {
        addSongs(newSongs);
    }

    public void addSongs(String[] newSongs)
    {
        songs.addAll(Arrays.asList(newSongs));
    }
}
