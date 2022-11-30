package Backend;

import Frontend.Draw;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;

public class Game extends AnimationTimer {

    private Grid gameGrid;
    private Canvas grid;
    private long lastUpdate;

    public Game(Canvas grid){
        this.gameGrid = new Grid();
        this.grid = grid;
        this.lastUpdate = 2000000000;
    }

    @Override
    public void handle(long time) {
        try {
            long frameRate = (long)Math.pow(10,9)/2; // one second in milisecond / framrate
        }catch(Exception exception){

        }


        if(time-lastUpdate >= 1000000000) {
            if(gameGrid.moveSnake(2)) {
                this.stop();
            }
            Draw playground = new Draw(grid.getGraphicsContext2D());
            playground.drawBackground(getPos());
            playground.draw(getHeadPos());
            System.out.println(getHeadPos());
            lastUpdate = time;

        }


    }

    public boolean play(int direction) throws Exception{
        long frameRate = 1000/2; // one second in milisecond / framrate
        long time = System.currentTimeMillis();      
        Thread.sleep((frameRate-time%frameRate));

        return gameGrid.moveSnake(direction); //If snake hasn't collided


    }
    public ArrayList<GridPos> getPos(){
        return gameGrid.getPositions();
    }
    public GridPos getHeadPos(){
        return gameGrid.getHeadPos();
    }
}
