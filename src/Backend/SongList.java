package Backend;

import Frontend.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class SongList
{
    // Note: The values of the variables below are not assigned in the constructor,
    // because they are static, meaning they belong to the class itself, not the instances or objects we create of it
    public static int listIndex = 0;
    public static int songIndex = 0;

    // Have a number for each class that is the number of songs in the current list
    public static ArrayList<String> songs = new ArrayList<>();
    public static int[] numberOfSongsInList = {3, 2}; // Note for JoelM: Set its values in a more general way dependent on variables instead of magic numbers
    // Benefit of the list and the system of cooperating variables built around it: More freedom for the developers (us)
    // when developing the game: The number of songs in each list don't need to be the same --> Less restrictions for us
    // Using a 2D array - Would restrict us: Waste allocated space or same number of songs for each list?

    public static ArrayList<Integer> currentSongIndices = new ArrayList<>();
    public static int[][] songListIndicesBoundaries;
    public static String[] listNames = {"Chill", "Trap"};

    public SongList(String[] newSongs, int listIndex)
    {
        addSongs(newSongs, listIndex);
    }

    public void addSongs(String[] newSongs, int listIndex)
    {
        songs.addAll(Arrays.asList(newSongs));
        currentSongIndices.add(songListIndicesBoundaries[listIndex][0]);
    }

    public static void toggleSongList(int newListIndex)
    {
        listIndex = newListIndex;
        songIndex = currentSongIndices.get(listIndex);

        SongUtils.startAudioClip();
        Utils.updateText(Controller.scene, "#songNameText", songs.get(songIndex), true);
    }

    public static String selectSongList() // Logic for determining UI
    {
        for (int i = 0; i < songListIndicesBoundaries.length; i++)
        {
            if (Utils.isWithinRange(songIndex, songListIndicesBoundaries[i]))
            {
                return listNames[i];
            }
        }

        return "-1";
    }

    public static void synchronizeThumbnailWithSong()
    {
        String commonPath = "resources/assets/";
        String list = "";
        String newSong = "";

        if (SongList.listIndex == 0)
        {
            list = "ChillList";
        }
        else if (SongList.listIndex == 1)
        {
            list = "TrapList";
        }

        newSong = SongList.songs.get(SongList.songIndex); // Create method: 'GetCurrentSong()'?

        // System.out.println(commonPath + list + "/" + Utils.removeNLastCharactersInString(newSong, 4) + ".png");

        // Note for JoelM: To avoid using function from Utils, remove ".wav" in MainGame.java and add function 'SetFormat(String format)' where 'format' = "wav", "png"
        Image newSongImage = new Image(commonPath + list + "/" + Utils.removeNLastCharactersInString(newSong, 4) + ".png");
        ImageView songPictureDisplayer = (ImageView)(Controller.scene.lookup("#songPictureDisplayer")); // Reuse method in Utils.java?, Seperate logic from UI!
        songPictureDisplayer.setImage(newSongImage);
    }
}
