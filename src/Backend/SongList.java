package Backend;

import Backend.Utils.SongUtils;
import Frontend.Utils.UIUtils;
import Backend.Utils.Utils;
import Frontend.Controller;
import Frontend.Utils.UIUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class SongList // Note for JoelM - Design solutions:
// 1. Rename to 'SongListUtils.java'
// 2. Make an interface of this - More than constants
// 3. Make this an abstract class, remove sub-classes but keep the constructor
// 4. Polymorhpism inheritance?

{
    // Note: The values of the variables below are not assigned in the constructor,
    // because they are static, meaning they belong to the class itself, not the instances or objects we create of it
    public static int listIndex = 0;
    public static int songIndex = 0;

    public static int previousListIndex = 0;

    // Have a number for each class that is the number of songs in the current list
    public static ArrayList<String> songs = new ArrayList<>();
    public static ArrayList<Integer> currentSongIndices = new ArrayList<>();
    public static int[][] songListIndicesBoundaries;

    // The two variables below needs to be updates manually when adding a new songlist:
    public static final int[] numberOfSongsInList = {3, 2, 2, 2}; // Note for JoelM: Set its values in a more general way dependent on variables instead of magic numbers
    // Benefit of the list and the system of cooperating variables built around it: More freedom for the developers (us)
    // when developing the game: The number of songs in each list don't need to be the same --> Less restrictions for us
    // Using a 2D array - Would restrict us: Waste allocated space or same number of songs for each list?
    public static final String[] listNames = {"Chill", "Trap", "HipHop", "Disco"}; // + "List"

    /*
    public static final LinearGradient selectedSongTextGradient = new LinearGradient(
            0.0, 0.0, 0.6667, 1.0, true, CycleMethod.NO_CYCLE,
            new Stop(0.0, new Color(0.95, 0.057, 0.057, 1.0)),
            new Stop(1.0, new Color(0.2526, 0.0229, 0.8842, 1.0)));

    public final static Color normalSongTextGradient = new Color(0.0, 0.0, 0.0, 1.0);

     */

    public SongList(String[] newSongs, int listIndex)
    {
        addSongs(newSongs, listIndex);
    }

    public void addSongs(String[] newSongs, int listIndex)
    {
        songs.addAll(Arrays.asList(newSongs));
        currentSongIndices.add(songListIndicesBoundaries[listIndex][0]);
    }

    public static void toggleSongList(int newListIndex, boolean songIsPaused) // Note for JoelM: Put this in 'SongUtils.java' and add 'scene' and 'id' parameters
    {
        listIndex = newListIndex;
        songIndex = currentSongIndices.get(listIndex); // Set index to start of list

        SongUtils.startAudioClip(songIsPaused);
        Frontend.Utils.UIUtils.updateText(Controller.scene, "#songNameText", songs.get(songIndex), true);
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

    public static void synchronizeThumbnailWithSong() // Put this in SongUtils.java
    {
        String commonPath = "resources/assets/";
        String list = "";
        String newSong = "";

        // Note for JoelM: Come up with design solution to counteract this poor manual-dependent solution
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

        // Note for JoelM: To avoid using function from Utils, remove ".wav" in MainGame.java and add function 'SetFormat(String format)' where 'format' = "wav", "png"
        Image newSongImage = new Image(commonPath + list + "/" + Utils.removeNLastCharactersInString(newSong, 4) + ".png");
        ImageView songPictureDisplayer = (ImageView)(Controller.scene.lookup("#songPictureDisplayer")); // Reuse method in Utils.java?, Seperate logic from UI!
        songPictureDisplayer.setImage(newSongImage);
    }

    public static void updateSongListTexts() // Put this in UIUtils.java - Note for JoelM: 'UISongUtils.java'?
    {
        for (int i = 0; i < 5; i++) // Note for JoelM: Remove magic number - There are 5 unique texts in the scene - Variable: 'maximumSongsInList'
        {
            Text songText = (Text)(Controller.scene.lookup("#song" + i));
            setSongListTextsOpacity(songText, i);
            setSongListTexts(songText, i);
        }
    }

    private static void setSongListTexts(Text songText, int i)
    {
        if (Utils.checkIfIndexIsWithinRangeOfList(numberOfSongsInList[listIndex], i))
        {
            int startIdxOfCurrentList = songListIndicesBoundaries[listIndex][0];
            String nameOfWAVSong = songs.get(startIdxOfCurrentList + i);
            String songNumber = (i + 1) + ". ";

            String nameOfSong = songNumber + Utils.removeNLastCharactersInString(nameOfWAVSong, 4);
            songText.setText(nameOfSong);
        }
    }

    private static void setSongListTextsOpacity(Text songText, int i)
    {
        // Make text visible if its index is within the range of the length of the list
        if (Utils.checkIfIndexIsWithinRangeOfList(numberOfSongsInList[listIndex], i))
        {
            songText.setOpacity(1);
        }

        // Make text invisible if the size of the current list of songs doesn't support enough songs
        else
        {
            songText.setOpacity(0);
        }
    }

    // Note for JoelM: Clean this method - Make it more smooth
    public static void updateSelectedSongText(int previousSongIndex, boolean userSwitchedList)
    {
        int songIdIndex = getSongInListIndex(songIndex, false);
        previousSongIndex = getSongInListIndex(previousSongIndex, userSwitchedList);

        Text selectedText = (Text)(Controller.scene.lookup("#song" + songIdIndex));
        Text previouslySelectedText = (Text)(Controller.scene.lookup("#song" + previousSongIndex));

        Text[] textsThatChangesColors = new Text[]{previouslySelectedText, selectedText};

        // Note for JoelM: Make overload method in UIUtils.java
        previouslySelectedText.setFont(Frontend.Utils.UIUtils.getNormalFont("Verdana", 8)); // JoelM: Avoid magic number '8', create variable for font size. Connect it to size set in GameScene
        selectedText.setFont(Frontend.Utils.UIUtils.getSelectedFont("Verdana", false, 8));

        UIUtils.changeTextColor(textsThatChangesColors, true, 2);
    }

    // Returns the index for the song within the list itself
    // without regards to its index in the entirety of all the stored songs in the ArrayList<String> songs
    private static int getSongInListIndex(int index, boolean userSwitchedList)
    {
        // If the user switched list, we need the previous text-index for the previous list
        if (userSwitchedList)
        {
            return index - songListIndicesBoundaries[previousListIndex][0];
        }

        return index - songListIndicesBoundaries[listIndex][0];
    }
}
