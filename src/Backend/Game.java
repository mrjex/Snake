package Backend;

import Frontend.Controller;
import Frontend.Draw;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Accordion;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.fxml.FXML;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class Game extends AnimationTimer {

    private Grid gameGrid;
    private Canvas canvas;
    public long lastUpdate;
    public boolean canTurn;
    private ArrayList<ScoreData> scoreList;
    public static boolean isFrenzy;

    @FXML
    private Text songNameText;

    public Game(Canvas grid, boolean frenzy){

        this.gameGrid = new Grid();
        this.canvas = grid;
        this.lastUpdate = 0;
        this.canTurn = true;
        this.scoreList = Game.readScores();
        this.isFrenzy = frenzy;
        gameGrid.spawnFood(isFrenzy);


    }

    public static ArrayList<ScoreData> readScores() {

        ArrayList<ScoreData> leaderboard = new ArrayList<>();

        try {

            byte[] bytes = Files.readAllBytes(Paths.get("Scores/score.json"));
            String fileContent = new String (bytes);

            JSONObject obj = new JSONObject(fileContent);
            JSONArray scores = obj.getJSONArray("scores");
            for(Object score : scores) {

                JSONObject scoreObject = (JSONObject) score;
//                String name = (String) scoreObject.get("name");
                int value = (Integer) scoreObject.get("value");
                leaderboard.add(new ScoreData(value));

            }


        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

        Collections.sort(leaderboard);
        return leaderboard;

    }
    @Override
    public void handle(long time) {
        if(time-lastUpdate >= Math.pow(10,9)/5) {

            // Notes for JoelM - Two steps:
            // Check if SongList.currentClip updates to the next clip
            // Find a smooth way to store current song index to put in the denominator below

            double barProgression = (double)(SongList.setCommaNDigitsFromEnd(SongList.currentClip.getMicrosecondPosition(), 3) / (double)SongList.songDurations[SongList.currentSongIndex]); // past index: 0

            ProgressBar bar = (ProgressBar) (Controller.scene.lookup("#songProgressBar"));
            bar.setProgress(barProgression);

            songNameText = (Text)(Controller.scene.lookup("#songNameText")); // JoelM: Optimize
            songNameText.setText(SongList.songFilePaths[SongList.currentSongIndex].substring(0, SongList.songFilePaths[SongList.currentSongIndex].length() - 4)); // JoelM: Optimize

            int code = gameGrid.moveSnake(isFrenzy);
            if (code == 1){
                Label label = (Label) canvas.getScene().lookup("#score");
                String scores = String.valueOf(getScore(gameGrid.getBodyPos()));
                label.setText(scores);

                // Sound effect here - Eat specific food
            }

            if (code == 2) {
                // Sound effect here - Snake dies

                try {
                    ScoreData currentScore = new ScoreData(this.getScore(gameGrid.getBodyPos()));
                    this.scoreList.add(currentScore);

                    JSONObject jo = new JSONObject();
                    JSONArray scores = new JSONArray();

                    for(ScoreData score : this.scoreList) {

                        JSONObject data = new JSONObject();
                        data.put("value", score.getValue());
                        data.put("timestamp", score.getDate());
                        scores.put(data);

                    }
                    jo.put("scores", scores);
                    FileWriter writer = new FileWriter("Scores/score.json");
                    writer.write(jo.toString());
                    writer.flush();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                this.stop();

                Stage window = (Stage)canvas.getScene().getWindow();
                try {

                    Parent root = FXMLLoader.load(getClass().getResource("../resources/GameOverScreen.fxml"));
                    Scene scene = new Scene(root);
                    window.setScene(scene);

                    Label label = (Label) scene.lookup("#finalScore");
                    label.setText(String.valueOf(getScore(gameGrid.getBodyPos())));

                    VBox vbox = (VBox) scene.lookup("#leaderboard");

                    ArrayList<ScoreData> leaderboard = Game.readScores();
                    Accordion accordion = new Accordion();

                    for (ScoreData score : leaderboard) {

                        TitledPane titledPane = new TitledPane("Score: " + score.getValue(), new Label("Obtained at " + score.getDate()));
                        accordion.getPanes().add(titledPane);

                    }

                    vbox.getChildren().add(accordion);

                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }


            }

            if(code != 2) {

                Draw playground = new Draw(canvas.getGraphicsContext2D());
                playground.drawBackground(getPos());
                playground.draw(getHeadPos());
                for(GridPos bodyPos : gameGrid.getBodyPos()){
                    playground.draw(bodyPos);
                }
                playground.draw(gameGrid.getFood());
                lastUpdate = time;


        }

            this.canTurn = true;

        }



    }
    public void setDirection(int newDirection){
        this.gameGrid.setDirection(newDirection);
    }
    public int getDirection(){
        return gameGrid.getDirection();
    }

    public int getScore(ArrayList<GridPos> positions) {
        return positions.size() - 3;
    }
    /*public boolean play(int direction) throws Exception{
        long frameRate = 1000/2; // one second in milisecond / framrate
        long time = System.currentTimeMillis();      
        Thread.sleep((frameRate-time%frameRate));

        return gameGrid.moveSnake(); //If snake hasn't collided


    }*/
    public ArrayList<GridPos> getPos(){
        return gameGrid.getPositions();
    }
    public GridPos getHeadPos(){
        return gameGrid.getHeadPos();
    }

}
