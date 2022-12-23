package Backend;
import Frontend.Controller;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Utils
{
    public static void updateText(Scene scene, String textId, String newMessage, boolean isSong)
    {
        if (isSong)
        {
            newMessage = removeNLastCharactersInString(newMessage, 4);
        }

        Text text = (Text)(Controller.scene.lookup("#songNameText"));
        text.setText(newMessage);
    }

    // Used to remove ".wav" for every song in this project
    public static String removeNLastCharactersInString(String stringText, int n)
    {
        return stringText.substring(0, stringText.length() - n);
    }

    // Source used: https://stackoverflow.com/questions/29012224/java-how-to-remove-last-numbers-of-a-long (retrieved 2022-12-15)
    // The purpose with this function is that Java completely ignores the comma located 3 digits from the last digit
    // when retrieving a clip's length using 'clip.getMicrosecondLength'
    public static long setCommaNDigitsFromEnd(long number, int n)
    {
        return (long)(number / Math.pow(10, n));
    }

    public static int limitValue(int value, int[] limits)
    {
        if (value > limits[1])
            return limits[0];
        else if (value < limits[0])
            return limits[1];

        return value;
    }

    public static boolean isWithinRange(int value, int[] range)
    {
        return (value >= range[0] && value <= range[1]);
    }

    public static Object linkObjectWithId(String id) // Note for JoelM: Create class 'UI-Utils.java' where we put this method and 'updateText()'?
    {
        return Controller.scene.lookup(id);
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
}
