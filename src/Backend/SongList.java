package Backend;

import Frontend.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.LinearGradient;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.paint.Paint;

public abstract class SongList
{
    // Note: The values of the variables below are not assigned in the constructor,
    // because they are static, meaning they belong to the class itself, not the instances or objects we create of it
    public static int listIndex = 0;
    public static int songIndex = 0;

    // Have a number for each class that is the number of songs in the current list
    public static ArrayList<String> songs = new ArrayList<>();

    public static ArrayList<Integer> currentSongIndices = new ArrayList<>();
    public static int[][] songListIndicesBoundaries;

    // The two variables below needs to be updates manually when adding a new songlist:
    public static int[] numberOfSongsInList = {3, 2, 2, 2}; // Note for JoelM: Set its values in a more general way dependent on variables instead of magic numbers
    // Benefit of the list and the system of cooperating variables built around it: More freedom for the developers (us)
    // when developing the game: The number of songs in each list don't need to be the same --> Less restrictions for us
    // Using a 2D array - Would restrict us: Waste allocated space or same number of songs for each list?
    public static String[] listNames = {"Chill", "Trap", "HipHop", "Disco"};

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

    // Returns the name of the current list that is being played
    public static String selectSongList() // Logic for determining UI
    {
        for (int i = 0; i < songListIndicesBoundaries.length; i++)
        {
            // If the index of the current song in within the interval of the current list, that list is being played
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

        // Note for JoelM: Come up with design.solution to counteract this poor manual-dependent solution
        if (SongList.listIndex == 0)
        {
            list = "ChillList";
        }
        else if (SongList.listIndex == 1)
        {
            list = "TrapList";
        }
        else if (SongList.listIndex == 2)
        {
            list = "HipHopList";
        }
        else if (SongList.listIndex == 3)
        {
            list = "DiscoList";
        }

        newSong = SongList.songs.get(SongList.songIndex); // Create method: 'GetCurrentSong()'?

        // System.out.println(commonPath + list + "/" + Utils.removeNLastCharactersInString(newSong, 4) + ".png");

        // Note for JoelM: To avoid using function from Utils, remove ".wav" in MainGame.java and add function 'SetFormat(String format)' where 'format' = "wav", "png"
        Image newSongImage = new Image(commonPath + list + "/" + Utils.removeNLastCharactersInString(newSong, 4) + ".png");
        ImageView songPictureDisplayer = (ImageView)(Controller.scene.lookup("#songPictureDisplayer")); // Reuse method in Utils.java?, Seperate logic from UI!
        songPictureDisplayer.setImage(newSongImage);
    }

    public static void updateSongListTexts()
    {
        for (int i = 0; i < 5; i++) // Remove magic number: There are 5 unique texts in the scene
        {
            Text songText = (Text)(Controller.scene.lookup("#song" + (i + 1)));

            if (i < SongList.numberOfSongsInList[SongList.listIndex])
                songText.setOpacity(1);
            else
                songText.setOpacity(0);
        }

        updateSelectedSongText();
    }

    public static void updateSelectedSongText() // int previousSongIndex
    {
        Text songText = (Text)(Controller.scene.lookup("#song" + (songIndex + 1)));
        // songText.setUnderline(true);
        songText.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));

        Text song2 = (Text)(Controller.scene.lookup("#song2"));

        LinearGradient fgg = (LinearGradient) song2.getFill();

        songText.setFill(fgg);

    }
}
