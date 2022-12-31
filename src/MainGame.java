import Backend.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Backend.Utils.SongUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.jar.JarFile;

public class MainGame extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("resources/StartMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        SongList.songListIndicesBoundaries = SongList.getSongListIndicesBoundaries();

        // + ".wav"
        SongList.addSongs(new String[] {
                "Lazy Love - KEM.wav", "Music Is - Pryces.wav", "Bees In The Garden - Moire.wav", // ChillList
                "Sky High - Trinity.wav", "Energy I Need - Pecan Pie.wav",                        // TrapList
                "The Flower - RA.wav", "Jazz And Hop - kidcut.wav",                               // HipHopList
                "Disco Street - Andrey Rossi.wav", "Soulful Sparks - Soundroll.wav"               // DiscoList
        });

        SongUtils.startAudioClip(false);
    }

    public static void main(String[] args) {
        launch();
    }
}