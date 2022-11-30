package Frontend;

import Backend.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void StartMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../resources/StartMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void GameScene(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("../resources/GameScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Canvas grid = (Canvas) scene.lookup("#Grid");
        Game game = new Game(grid);
        game.start();

    }
}