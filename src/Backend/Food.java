package Backend;

import javafx.scene.paint.Color;

public abstract class Food extends GridPos {

    private int score;
    private double probability;


    public Food(int x, int y, int score) {

        super(x, y, Color.RED);
        this.score = score;
        this.probability = 0.0;

    }

    public int getScore(){
        return this.score;
    }

    public void setProbability(double probability){
        this.probability = probability;
    }




}
