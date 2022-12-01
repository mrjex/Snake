package Frontend;

import Backend.Game;
import Backend.ScoreData;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void StartMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../resources/StartMenu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void GameScene(ActionEvent event) throws Exception {

        root = FXMLLoader.load(getClass().getResource("../resources/GameScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Canvas grid = (Canvas) scene.lookup("#Grid");

        Game game = new Game(grid);
        changeDirection(scene, game);
        game.start();


    }

    public void changeDirection(Scene scene, Game game) {

        scene.setOnKeyPressed(e -> {

            switch (e.getCode()) {
                case W:
                    if (game.getDirection() == 1 || game.getDirection() == 3) {
                        game.setDirection(0);
                    }
                    break;
                case S:
                    if (game.getDirection() == 1 || game.getDirection() == 3) {
                        game.setDirection(2);
                    }
                    break;
                case D:
                    if (game.getDirection() == 0 || game.getDirection() == 2) {
                        game.setDirection(3);
                    }
                    break;
                case A:
                    if (game.getDirection() == 0 || game.getDirection() == 2) {
                        game.setDirection(1);
                    }
                    break;
                default:
                    break;
            }
        });
    }
}