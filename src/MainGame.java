import Backend.*;
import Frontend.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.LinearGradient;
import javafx.scene.text.Text;
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
        Game.chillList = new ChillList(new String[] {"Lazy Love - KEM.wav", "Music Is - Pryces.wav", "Bees In The Garden - Moire.wav"}, 0);
        Game.trapList = new TrapList(new String[]{"Sky High - Trinity.wav", "Energy I Need - Pecan Pie.wav"}, 1);
        Game.hipHopList = new HipHopList(new String[]{"The Flower - RA.wav", "Jazz And Hop - kidcut.wav"}, 2);
        Game.discoList = new DiscoList(new String[]{"Disco Street - Andrey Rossi.wav", "Soulful Sparks - Soundroll.wav"}, 3);

        SongUtils.startAudioClip();
    }

    public static void main(String[] args) {
        launch();
    }
}