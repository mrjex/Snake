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
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("resources/GameScene.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Backend.Snake: The Backend.Game");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        Canvas grid = (Canvas) scene.lookup("#Grid");
        Game game = new Game();
        Draw playground = new Draw(grid.getGraphicsContext2D());
        playground.drawBackground(game.getPos());
        playground.draw(game.getHeadPos());
        int i = 0;
        while (!game.play(3)){
            System.out.println(i);
            i++;
            playground.drawBackground(game.getPos());
            playground.draw(game.getHeadPos());
            System.out.println(game.getHeadPos());
        }



    }

    public static void main(String[] args) {
        launch();
    }
}