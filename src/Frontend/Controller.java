package Frontend;

import Backend.*;
import Backend.Utils.SongUtils;
import Backend.Utils.Utils;
import Frontend.Utils.UIUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// import javax.swing.text.html.ImageView;

import java.io.IOException;
import java.util.ArrayList;

public class Controller{
    private Stage stage;
    public static Scene scene; // Change back to previous if it turns out to not work: "private Scene scene;"
    private Parent root;

    @FXML
    private RadioButton chill;
    @FXML
    private RadioButton trap;
    @FXML
    private RadioButton hipHop;
     @FXML
     private RadioButton disco;
    @FXML
    private CheckBox pauseCheckBox;
    private RadioButton mostRecentSelectedRadioButton = null;

    public void StartMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../resources/StartMenu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void exit() {
        SongUtils.stop();
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

    // The 3 methods contained in this generalized method can be used in all the methods above except 'exit'
    // The reason this isn't refactored before pushing remotely is because I don't want to take credit
    // for the code itself, but for the refactoring design-aspect of it.
    public void generalizedMethod(ActionEvent event) throws IOException {
        root = getRoot("directory here");
        setScene(event);

        boolean bool = false; // This value varies depending on what method
        // we are inspecting of the ones above ('Leaderboard', 'playAgain', 'normalMode', 'foodFrenzyButton')
        startGame(bool);
    }

    // Gets the root with respect to its directory
    public Parent getRoot(String directory) throws IOException {
        return FXMLLoader.load(getClass().getResource(directory));
    }

    //
    public void setScene(ActionEvent event)
    {
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }

    public void startGame(boolean frenzyModeIsSelected)
    {
        Canvas grid = (Canvas) scene.lookup("#Grid");
        Game game = new Game(grid, frenzyModeIsSelected);

        changeDirection(scene, game);
        game.start();
    }

    public boolean checkIfUserPressedEQ(Scene scene, KeyEvent teest)
    {
        // scene.setOnKeyReleased();
        return teest.getCode().equals(KeyCode.E) || teest.getCode().equals(KeyCode.Q);
    }

    public void changeDirection(Scene scene, Game game) {

        Group pauseGroup = (Group) scene.lookup("#pause");

        Button button = (Button)scene.lookup("#resume");
        button.setOnAction(e -> {

            pauseGroup.setOpacity(0);
            pauseGroup.setDisable(true);

            game.start();
        });

        // Note for JoelM: Make new method for this
        scene.setOnKeyReleased(e ->
        {
            if (e.getCode().equals(KeyCode.E) || e.getCode().equals(KeyCode.Q))
            {
                int previousSongIndex = SongList.songIndex;
                CheckBox pauseBox = (CheckBox)scene.lookup("#pauseCheckBox"); // Avoid NullPointerException when referring to 'pauseCheckBox'

                boolean increaseIndexOfSong = e.getCode().equals(KeyCode.E);
                SongUtils.toggleSong(increaseIndexOfSong, pauseBox.isSelected());

                SongList.synchronizeThumbnailWithSong();
                previousSongIndex = Utils.limitValue(previousSongIndex, SongList.songListIndicesBoundaries[SongList.listIndex]);
                SongList.updateSelectedSongText(previousSongIndex, false);
            }
        });

        scene.setOnKeyPressed(e ->
        {
            if (e.getCode().equals(KeyCode.ESCAPE)) // Note for JoelM: This if-statement does not belong in this function - Fix
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

    // Note for JoelM: Refactor this code
    public void changeSongList(ActionEvent event)
    {
        int selectedListIndex = 0;
        int previousIndex = SongList.songIndex;
        RadioButton[] listButtons = new RadioButton[]{chill, trap, hipHop, disco};

        if (!checkIfAnyListIsSelectedTwice(listButtons))
        {
            // Note for JoelM: Possible to make this more optimal in terms of design?
            if(chill.isSelected())
            {
                deselectRadioButton(mostRecentSelectedRadioButton, chill);
                mostRecentSelectedRadioButton = chill;

                // System.out.println("chill");
            }

            if(trap.isSelected())
            {
                selectedListIndex = 1;
                deselectRadioButton(mostRecentSelectedRadioButton, trap);
                mostRecentSelectedRadioButton = trap;

                // System.out.println("trap");
            }

            if (hipHop.isSelected())
            {
                selectedListIndex = 2;
                deselectRadioButton(mostRecentSelectedRadioButton, hipHop);
                mostRecentSelectedRadioButton = hipHop;

                // System.out.println("hiphop");
            }

            if (disco.isSelected())
            {
                selectedListIndex = 3;
                deselectRadioButton(mostRecentSelectedRadioButton, disco);
                mostRecentSelectedRadioButton = disco;

                // System.out.println("disco");
            }

            // Attempt 1: Refactoring the above lines of code:
            /*
            int[] testIndices = getSelectedListIndex(listButtons);
            selectNewList(listButtons, testIndices);
            selectedListIndex = testIndices[testIndices.length - 1];
             */

            SongList.toggleSongList(selectedListIndex, pauseCheckBox.isSelected());
            SongList.synchronizeThumbnailWithSong();

            UIUtils.updateSongListTexts();
            SongList.updateSelectedSongText(previousIndex, true);
        }
    }

    private void selectNewList(RadioButton[] listButtons, int[] indices)
    {
        for (int i = 0; i < indices.length; i++)
        {
            deselectRadioButton(mostRecentSelectedRadioButton, listButtons[indices[i]]);
            mostRecentSelectedRadioButton = listButtons[indices[i]];
        }
    }

    private int[] getSelectedListIndex(RadioButton[] listButtons)
    {
        int j = 0;
        int[] lists = new int[]{-1, -1};

        for (int i = 0; i < listButtons.length; i++)
            if (listButtons[i].isSelected())
            {
                lists[j++] = i;
            }

        if (j == 2)
            return lists;
        else
            return new int[]{lists[0]};
    }

    // Selected Twice in a row - According to SceneBuilder - Toggle - Would result in turning off/on list - But we have pause button
    private boolean checkIfAnyListIsSelectedTwice(RadioButton[] selectedLists)
    {
        // Go through every possible list-index
        for (int i = 0; i < SongList.numberOfSongsInList.length; i++)
        {
            if (checkIfCurrentListWasSelectedTwice(selectedLists[i], i))
            {
                selectedLists[i].setSelected(true);
                return true;
            }
        }

        return false;
    }

    private boolean checkIfCurrentListWasSelectedTwice(RadioButton selectedList, int listIndex)
    {
        return !selectedList.isSelected() && SongList.listIndex == listIndex;
    }

    private void deselectRadioButton(RadioButton mostRecentSelected, RadioButton selected)
    {
        if (mostRecentSelected != null && mostRecentSelected != selected)
        {
            mostRecentSelected.setSelected(false);
            SongList.previousListIndex = SongList.listIndex;
        }
    }

    public void pauseSong()
    {
        SongUtils.togglePause(pauseCheckBox.isSelected());
    }
}