package Backend.Utils;
import Frontend.Controller;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Utils // Note for JoelM: Create separate back-end from front-end: Create 'UIUtils.java' and put suitable methods in new script
{
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

    public static boolean checkIfIndexIsWithinRangeOfList(int lengthOfList, int index)
    {
        return index >= 0 && index < lengthOfList;
    }
}
