import Backend.SongList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Timer;

public class MainGame extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("resources/StartMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        Timer timer = new Timer();
        SongList.getSongDurations();
        long currentDelay = 0;

        for (int i = 0; i < SongList.numberOfSongsInList; i++)
        {
            timer.schedule(new SongList(i, 1), currentDelay);
            currentDelay += SongList.songDurations[i];
        }

        /*
        Notes for JoelM:
        * Make a general function that converts X to Y (time). Example: "Seconds" to "Minutes", "Hours" to "Milliseconds"
         */
    }

    public static void main(String[] args) {
        launch();
    }
}