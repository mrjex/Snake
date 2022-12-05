package Frontend;
import Backend.Game;
import Frontend.Draw;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class MainGame extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("resources/GameScene.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Backend.Snake: The Backend.Game");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        Canvas grid = (Canvas) scene.lookup("#Grid");
        Game game = new Game();
        Draw background = new Draw(grid.getGraphicsContext2D());
        background.drawBackground(game.getPos());

    }

    public static void main(String[] args) {
        launch();
    }
}
