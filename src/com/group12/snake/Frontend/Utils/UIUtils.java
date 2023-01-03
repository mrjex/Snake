package com.group12.snake.Frontend.Utils;

import com.group12.snake.Frontend.Controller;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UIUtils
{
    public static void updateText(Scene scene, String textId, String newMessage, boolean isSong)
    {
        if (isSong)
        {
            newMessage = com.group12.snake.Backend.Utils.Utils.removeNLastCharactersInString(newMessage, 4);
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

    public static Object linkObjectWithId(String id)
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
                currentText.setFill(UISongUtils.selectedSongTextGradient);
        }

        // Use pattern - The order of the texts in the array determines their color
        else
        {
            boolean useDefaultColor;

            for (int i = 0; i < texts.length; i++)
            {
                useDefaultColor = (i % pattern) == 1;

                if (useDefaultColor)
                    texts[i].setFill(UISongUtils.selectedSongTextGradient);
                else
                    texts[i].setFill(UISongUtils.normalSongTextGradient);
            }
        }
    }

    public static void updateSongListTexts()
    {
        for (int i = 0; i < 5; i++) // Note for JoelM: Remove magic number - There are 5 unique texts in the scene - Variable: 'maximumSongsInList'
        {
            Text songText = UIUtils.getText("#song" + i);

            UISongUtils.setSongListTextsOpacity(songText, i);
            UISongUtils.setSongListTexts(songText, i);
        }
    }

    public static Text getText(String id)
    {
        return (Text)(Controller.scene.lookup(id));
    }
}
