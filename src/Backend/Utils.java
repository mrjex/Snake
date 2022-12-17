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

        Text teest = (Text)(Controller.scene.lookup("#songNameText"));
        teest.setText(newMessage);
    }

    private static String removeNLastCharactersInString(String stringText, int n)
    {
        return stringText.substring(0, stringText.length() - n);
    }
}
