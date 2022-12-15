
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// import javax.print.attribute.standard.Media;
import java.io.File;
import java.util.Timer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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

            /*
            // No delay to start the first song
            if (i == 0)
            {
                timer.schedule(new SongList(i, 1), 0);
            }
            //
            else
            {
                timer.schedule(new SongList(i, 1), SongList.songDurations[i - 1]);
            }

             */
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