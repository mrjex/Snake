package Frontend.Utils;

import Backend.SongList;
import Backend.Utils.Utils;
import Backend.Utils.SongUtils;
import Frontend.Controller;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UIUtils // 'UISongUtils.java'?
{
    public static final LinearGradient selectedSongTextGradient = new LinearGradient(
            0.0, 0.0, 0.6667, 1.0, true, CycleMethod.NO_CYCLE,
            new Stop(0.0, new Color(0.95, 0.057, 0.057, 1.0)),
            new Stop(1.0, new Color(0.2526, 0.0229, 0.8842, 1.0)));

    public final static Color normalSongTextGradient = new Color(0.0, 0.0, 0.0, 1.0);


    public static void updateText(Scene scene, String textId, String newMessage, boolean isSong)
    {
        if (isSong)
        {
            newMessage = Backend.Utils.Utils.removeNLastCharactersInString(newMessage, 4);
        }

        Text text = (Text)(scene.lookup(textId));
        text.setText(newMessage);
    }

    public static Font getNormalFont(String fontType, int size)
    {
        return Font.font(fontType, FontWeight.NORMAL, FontPosture.REGULAR, size);
    }

    public static Font getSelectedFont(String fontType, boolean italicFont, int size)
    {
        if (italicFont)
        {
            return Font.font(fontType, FontWeight.BOLD, FontPosture.ITALIC, size);
        }
        else
        {
            return Font.font(fontType, FontWeight.BOLD, size);
        }
    }

    public static Object linkObjectWithId(String id) // Note for JoelM: Create class 'UI-Utils.java' where we put this method and 'updateText()'?
    {
        return Controller.scene.lookup(id);
    }

    // This method changes the color of texts in an array, with two predetermined constant colors
    // Note that 'selectedSongTextGradient' is assumed to be the "default" color.
    public static void changeTextColor(Text[] texts, boolean usePattern, int pattern)
    {
        // Do not use pattern - Change the color of each text to 'selectedSongTextGradient'
        if (!usePattern)
        {
            for (Text currentText : texts)
                currentText.setFill(selectedSongTextGradient);
        }

        // Use pattern - The order of the texts in the array determines their color
        else
        {
            boolean useDefaultColor;

            for (int i = 0; i < texts.length; i++)
            {
                useDefaultColor = (i % pattern) == 1;

                if (useDefaultColor)
                    texts[i].setFill(selectedSongTextGradient);
                else
                    texts[i].setFill(normalSongTextGradient);
            }
        }
    }

    public static void updateSongListTexts()
    {
        for (int i = 0; i < 5; i++) // Note for JoelM: Remove magic number - There are 5 unique texts in the scene - Variable: 'maximumSongsInList'
        {
            Text songText = UIUtils.getText("#song" + i);

            setSongListTextsOpacity(songText, i);
            setSongListTexts(songText, i);
        }
    }

    private static void setSongListTexts(Text songText, int i) // UIUtils.java
    {
        if (Utils.checkIfIndexIsWithinRangeOfList(SongList.numberOfSongsInList[SongList.listIndex], i))
        {
            int startIdxOfCurrentList = SongList.songListIndicesBoundaries[SongList.listIndex][0];

            String nameOfWAVSong = SongList.songs.get(startIdxOfCurrentList + i);
            String songNumber = (i + 1) + ". ";

            String nameOfSong = songNumber + Utils.removeNLastCharactersInString(nameOfWAVSong, 4);
            songText.setText(nameOfSong);
        }
    }

    private static void setSongListTextsOpacity(Text songText, int i) // UIUtils.java
    {
        // Make text visible if its index is within the range of the length of the list
        if (Utils.checkIfIndexIsWithinRangeOfList(SongList.numberOfSongsInList[SongList.listIndex], i))
        {
            songText.setOpacity(1);
        }

        // Make text invisible if the size of the current list of songs doesn't support enough songs
        else
        {
            songText.setOpacity(0);
        }
    }

    public static void changeTextFont(Text[] texts) // Note for JoelM: Fix when I got time
    {

    }

    public static Text getText(String id)
    {
        return (Text)(Controller.scene.lookup(id));
    }
}
