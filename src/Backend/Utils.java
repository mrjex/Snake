package Backend;
import Frontend.Controller;
import javafx.scene.Scene;
import javafx.scene.text.Text;

public class Utils
{
    public static void updateText(Scene scene, String textId, String newMessage, boolean isSong)
    {
        if (isSong)
        {
            newMessage = removeNLastCharactersInString(newMessage, 4); // Remove ".wav" for every song
        }

        Text text = (Text)(Controller.scene.lookup("#songNameText"));
        text.setText(newMessage);
    }

    private static String removeNLastCharactersInString(String stringText, int n)
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

    public static int limitValue(boolean upperLimit, int value, int[] limits)
    {
        if (upperLimit)
        {
            if (value > limits[1])
            {
                value = limits[0];
            }
        }
        else if (value < limits[0])
        {
            value = limits[1];
        }

        return value;
    }
}
