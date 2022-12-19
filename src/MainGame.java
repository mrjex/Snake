import Backend.ChillList;
import Backend.SongList;
import Backend.SongUtils;
import Backend.TrapList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGame extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("resources/StartMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        SongList.songListIndicesBoundaries = SongUtils.getSongListIndicesBoundaries();

        // Note for JoelM: Optimize this solution in accordance with the design principles when everything works
        ChillList chillList = new ChillList(new String[] {"Lazy Love - KEM.wav", "Music Is - Pryces.wav", "Bees In The Garden - Moire.wav"}, 0);
        TrapList trapList = new TrapList(new String[]{"Sky High - Trinity.wav", "Energy I Need - Pecan Pie.wav"}, 1);

        SongUtils.startAudioClip();

        System.out.println(SongList.currentSongIndices.get(0) + ", " + SongList.currentSongIndices.get(1)); // 0, 3
        System.out.println(SongList.songs);
    }

    public static void main(String[] args) {
        launch();
    }
}