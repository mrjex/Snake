package Backend;

import Frontend.Draw;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Game extends AnimationTimer {

    private Grid gameGrid;
    private Canvas canvas;
    public long lastUpdate;
    public boolean canTurn;
    private ArrayList<ScoreData> scoreList;


    public Game(Canvas grid){

        this.gameGrid = new Grid();
        this.canvas = grid;
        this.lastUpdate = 0;
        this.canTurn = true;
        this.scoreList = Game.readScores();

    }

    public static ArrayList<ScoreData> readScores() {

        ArrayList<ScoreData> leaderboard = new ArrayList<>();
        try {

            FileInputStream fs = new FileInputStream("Scores/score.txt");
            ObjectInputStream os = new ObjectInputStream(fs);
            leaderboard = (ArrayList<ScoreData>) os.readObject();

        } catch (Exception e) {

        }

        Collections.sort(leaderboard);
        return leaderboard;

    }
    @Override
    public void handle(long time) {



        if(time-lastUpdate >= Math.pow(10,9)/5) {


            int code = gameGrid.moveSnake();
            if (code == 1){
                Label label = (Label) canvas.getScene().lookup("#score");
                String scores = String.valueOf(getScore(gameGrid.getBodyPos()));
                label.setText(scores);
            }

            if (code == 2) {

                try {

                    ScoreData currentScore = new ScoreData(this.getScore(gameGrid.getBodyPos()));
                    this.scoreList.add(currentScore);
                    FileOutputStream os = new FileOutputStream("Scores/score.txt");
                    ObjectOutputStream objectOutput = new ObjectOutputStream(os);
                    objectOutput.writeObject(this.scoreList);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                this.stop();
                Stage window = (Stage)canvas.getScene().getWindow();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("../resources/GameOverScreen.fxml"));
                    Scene scene = new Scene(root);
                    window.setScene(scene);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }


            }

            if(code == 0) {

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
