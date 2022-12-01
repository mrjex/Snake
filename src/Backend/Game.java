package Backend;

import Frontend.Draw;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;

public class Game extends AnimationTimer {

    private Grid gameGrid;
    private Canvas canvas;
    private long lastUpdate;


    public Game(Canvas grid){
        this.gameGrid = new Grid();
        this.canvas = grid;
        this.lastUpdate = 0;
    }

    @Override
    public void handle(long time) {

        if(time-lastUpdate >= Math.pow(10,9)/5) {

            if(gameGrid.moveSnake() == 2) {
                this.stop();
            }
            Draw playground = new Draw(canvas.getGraphicsContext2D());
            playground.drawBackground(getPos());
            playground.draw(getHeadPos());
            for(GridPos bodyPos : gameGrid.getBodyPos()){
                playground.draw(bodyPos);
            }
            playground.draw(gameGrid.getFood());
//            System.out.println(gameGrid.getBodyPos());
//            System.out.println(getHeadPos());
            System.out.println("Score: " + this.getScore(gameGrid.getBodyPos()));
            lastUpdate = time;

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
