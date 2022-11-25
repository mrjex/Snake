import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainGame extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("resources/StartMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Backend.Snake: The Backend.Game");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

    }

    public static void main(String[] args) {
        launch();
    }
}