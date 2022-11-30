package Backend;

import java.util.ArrayList;

public class Game {
    private Grid gameGrid;

    public Game(){
        this.gameGrid = new Grid();
    }

    public boolean play(int direction) throws Exception{
        long frameRate = 1000/2; // one second in milisecond / framrate
        long time = System.currentTimeMillis();      
        Thread.sleep((frameRate-time%frameRate));

        return gameGrid.moveSnake(direction); //If snake hasn't collided


        /*int i = 0;
        while(i < 30){
            long time = System.currentTimeMillis();      
            Thread.sleep((frameRate-time%frameRate));
            System.out.println((i+1) + " " + System.currentTimeMillis()%10000);
            i++;
        }*/
    }
    public ArrayList<GridPos> getPos(){
        return gameGrid.getPositions();
    }
    public GridPos getHeadPos(){
        return gameGrid.getHeadPos();
    }
}
