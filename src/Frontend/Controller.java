package Frontend;

import Backend.Game;
import Backend.ScoreData;
import Backend.SongList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller{
    private Stage stage;
    public static Scene scene; // Change back to previous if it turns out to not work: "private Scene scene;"
    private Parent root;

    @FXML
    private RadioButton chill;
    @FXML
    private RadioButton trap;

    private RadioButton mostRecentSelectedRadioButton = null;

    public void StartMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../resources/StartMenu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void exit() {
        SongList.stop();
        Platform.exit();
    }

    public void FoodFrenzyButton(ActionEvent event) throws IOException {


        root = FXMLLoader.load(getClass().getResource("../resources/GameScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Canvas grid = (Canvas) scene.lookup("#Grid");

        Game game = new Game(grid, true);
        changeDirection(scene, game);
        game.start();


    }

    public void GameOverScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../resources/GameOverScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void NormalMode(ActionEvent event) throws Exception {

        root = FXMLLoader.load(getClass().getResource("../resources/GameScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Canvas grid = (Canvas) scene.lookup("#Grid");

        Game game = new Game(grid, false);
        changeDirection(scene, game);
        game.start();
    }

    public void playAgain(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("../resources/GameScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Canvas grid = (Canvas) scene.lookup("#Grid");

        Game game = new Game(grid, Game.isFrenzy);
        changeDirection(scene, game);
        game.start();

    }

    public void Leaderboard(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("../resources/Leaderboard.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        VBox vbox = (VBox) scene.lookup("#leaderboard");

        ArrayList<ScoreData> leaderboard = Game.readScores();
        Accordion accordion = new Accordion();

        for (ScoreData score : leaderboard) {

            TitledPane titledPane = new TitledPane("Score: " + score.getValue(), new Label("Obtained at " + score.getDate()));
            accordion.getPanes().add(titledPane);

        }

        vbox.getChildren().add(accordion);
        stage.show();
    }

    public void changeDirection(Scene scene, Game game) {

        Group pauseGroup = (Group) scene.lookup("#pause");

        Button button = (Button)scene.lookup("#resume");
        button.setOnAction(e -> {

            pauseGroup.setOpacity(0);
            pauseGroup.setDisable(true);

            game.start();
        });


        // Note for JoelM: Make new method for this and don't use it in this method ("changeDirection")
        scene.setOnKeyReleased(e ->
        {
            // Make method for this:
            // Make delay so that user can't spam new songs

            if (e.getCode().equals(KeyCode.Q))
            {
                if (SongList.listIndices[SongList.currentListIndex] == 0) // JavaFX sucks - They calculate (-1 % 3) = -1, which is wrong..
                    SongList.listIndices[SongList.currentListIndex] = SongList.chillSongs.length - 1;
                else
                    SongList.listIndices[SongList.currentListIndex]--;
            }
            else if (e.getCode().equals(KeyCode.E))
            {
                SongList.listIndices[SongList.currentListIndex]++;
            }

            SongList.listIndices[SongList.currentListIndex] %= SongList.chillSongs.length; // Isn't general: Only works for 1 list. Solution: 2D array - Requires every list to have same length. Otherwise, Solution: Jagged 2D array
            System.out.println(SongList.chillSongs[SongList.listIndices[SongList.currentListIndex]]);
        });

        scene.setOnKeyPressed(e ->
        {
            if (e.getCode().equals(KeyCode.ESCAPE)) // Note for JoelM: This if-statement does not belong in this function - Make a new one
            {
                game.stop();

                pauseGroup.setDisable(false);
                pauseGroup.setOpacity(1);
            }

            if(!game.canTurn) return;

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

            game.canTurn = false;
        });
    }

    public void changeValue(ActionEvent event)
    {
        if(chill.isSelected())
        {
            deselectRadioButton(mostRecentSelectedRadioButton, chill);
            mostRecentSelectedRadioButton = chill;

            // Play "Chill" song-list here
        }

        if(trap.isSelected())
        {
            deselectRadioButton(mostRecentSelectedRadioButton, trap);
            mostRecentSelectedRadioButton = trap;

            // Play "Trap" song-list here
        }
    }

    private void deselectRadioButton(RadioButton mostRecentSelected, RadioButton selected)
    {
        if (mostRecentSelected != null && mostRecentSelected != selected)
        {
            if (mostRecentSelected != null)
            {
                mostRecentSelected.setSelected(false);
            }
        }
    }
}