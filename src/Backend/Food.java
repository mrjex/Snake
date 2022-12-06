package Backend;

import javafx.scene.paint.Color;

public abstract class Food extends GridPos {

    private int score;



    public Food(int x, int y, int score) {

        super(x, y, Color.RED);
        this.score = score;
    }

    public int getScore(){
        return this.score;
    }
}
