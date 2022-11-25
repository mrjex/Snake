package Backend;

public class Game {
    private Grid gameGrid;

    public Game(){
        this.gameGrid = new Grid();
    }

    public void play(int direction) throws Exception{
        long frameRate = 1000/2; // one second in milisecond / framrate
        long time = System.currentTimeMillis();      
        Thread.sleep((frameRate-time%frameRate));

        if(gameGrid.moveSnake() == false){ //If snake hasn't collided
            System.out.println("Moved");
            play(direction);
        }

        /*int i = 0;
        while(i < 30){
            long time = System.currentTimeMillis();      
            Thread.sleep((frameRate-time%frameRate));
            System.out.println((i+1) + " " + System.currentTimeMillis()%10000);
            i++;
        }*/
    }
}
