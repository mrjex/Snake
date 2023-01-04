package com.group12.snake.Backend;

import com.group12.snake.Backend.Utils.SongUtils;
import com.group12.snake.Frontend.Utils.UIUtils;
import com.group12.snake.Backend.Utils.Utils;
import com.group12.snake.Frontend.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;

// Note: This is a back-end class with general behaviour and logic that heavily cooperates with the front-end-related class 'UIUtils'

// Justification of design choice:
// The variables should be static and belong to the class rather than to objects.
// Allowing multiple objects means enabling multiple song lists at once in the gameplay.
// Having a single player snake-game with more than one song list or radio would create
// confusion and hurt the user experience.

// However, if it would be a multiplayer game, where each player would have their own
// radio, it would be appropriate to capitalize upon the programming feature of creating
// several objects of same type that stores unique values (in this case selected list and
// selected song)

public class SongList // JoelM - Decision to make: 'SongList.java' or 'SongListUtils.java'

// Note for JoelM - Design solutions:
// 1. Rename to 'SongListUtils.java'
// 2. Make an interface of this - More than constants
// 3. Make this an abstract class, remove sub-classes but keep the constructor
// 4. Polymorphism inheritance?

{
    // Note: The values of the variables below are not assigned in the constructor,
    // because they are static, meaning they belong to the class itself, not the instances or objects we create of it
    public static int listIndex = 0;
    public static int songIndex = 0;
    public static int previousListIndex = 0;
    public static ArrayList<String> songs = new ArrayList<>();
    public static ArrayList<Integer> startIndicesForSongLists = new ArrayList<>();
    public static int[][] songListIndicesBoundaries;

    // The two variables below needs to be updates manually when adding a new songlist:
    public static final int[] numberOfSongsInList = {3, 2, 2, 2}; // Note for JoelM: Set its values in a more general way dependent on variables instead of magic numbers

    // Benefit of the list and the system of cooperating variables built around it: More freedom for the developers (us)
    // when developing the game: The number of songs in each list don't need to be the same --> Less restrictions for us
    // Using a 2D array - Would restrict us: Waste allocated space or same number of songs for each list?
    public static final String[] listNames = {"ChillList", "TrapList", "HipHopList", "DiscoList"};

    public static void addSongs(String[] newSongs)
    {
        songs.addAll(Arrays.asList(newSongs));
    }

    public static void toggleSongList(int newListIndex, boolean songIsPaused) // Note for JoelM: Put this in 'SongUtils.java' and add 'scene' and 'id' parameters
    {
        listIndex = newListIndex;
        songIndex = songListIndicesBoundaries[listIndex][0];

        SongUtils.startAudioClip(songIsPaused);
        UIUtils.updateText(Controller.scene, "#songNameText", songs.get(songIndex), true);
    }

    public static void synchronizeThumbnailWithSong()
    {
        String list = getCurrentList();
        String newSong = getCurrentSong();

        String commonPath = "assets/";
        // Note for JoelM: To avoid using function from Utils, remove ".wav" in com.group12.snake.MainGame.java and add function 'SetFormat(String format)' where 'format' = "wav", "png"
        Image newSongImage = new Image(commonPath + list + "/" + Utils.removeNLastCharactersInString(newSong, 4) + ".png");

        ImageView songPictureDisplayer = (ImageView)(Controller.scene.lookup("#songPictureDisplayer")); // Reuse method in Utils.java?, Seperate logic from UI!
        songPictureDisplayer.setImage(newSongImage);
    }

    public static String getCurrentSong()
    {
        return songs.get(songIndex);
    }

    public static String getCurrentList()
    {
        return listNames[listIndex];
    }

    // Note for JoelM: Clean this method - Make it more smooth
    public static void updateSelectedSongText(int previousSongIndex, boolean userSwitchedList) // UIUtils.java
    {
        int songIdIndex = getSongInListIndex(songIndex, false);
        previousSongIndex = getSongInListIndex(previousSongIndex, userSwitchedList);

        // Create method - Seperate Front-end from Back-end
        Text selectedText = UIUtils.getText("#song" + songIdIndex);
        Text previouslySelectedText = UIUtils.getText("#song" + previousSongIndex);

        Text[] textsThatChangesColors = new Text[]{previouslySelectedText, selectedText};

        // Note for JoelM: Make overload method in UIUtils.java
        previouslySelectedText.setFont(UIUtils.getNormalFont("Verdana", 8)); // JoelM: Avoid magic number '8', create variable for font size. Connect it to size set in GameScene
        selectedText.setFont(UIUtils.getSelectedFont("Verdana", false, 8));

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

    public static int[][] getSongListIndicesBoundaries()
    {
        // [3, 2] ---> [0, 2], [3, 4]
        // [3, 2, 3, 4, 2] ---> [0, 2], [3, 4], [5, 7], [8, 11], [12, 13]

        int[][] listBoundaries = new int[numberOfSongsInList.length][];

        int min = 0;
        int max = SongList.numberOfSongsInList[0] - 1;

        for (int i = 0; i < numberOfSongsInList.length - 1; i++)
        {
            listBoundaries[i] = new int[]{min, max};

            min += SongList.numberOfSongsInList[i];
            max += SongList.numberOfSongsInList[i + 1];
        }

        listBoundaries[listBoundaries.length - 1] = new int[]{min, max};
        return listBoundaries;
    }

    public static int getNumberOfSongLists()
    {
        return numberOfSongsInList.length;
    }
}
