package Frontend;

import Backend.Food;
import Backend.Game;
import Backend.Grid;
import Backend.GridPos;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Draw {
    private GraphicsContext gc;

    public Draw (GraphicsContext gc){
        this.gc = gc;
    }

    public void drawBackground(ArrayList<GridPos> positions){
        for(GridPos position : positions){
            this.gc.setFill(position.getColor());
            this.gc.fillRect(position.getxPos(), position.getyPos(), Grid.POS_LENGTH, Grid.POS_LENGTH);
        }
    }
    public void draw(GridPos pos){
        this.gc.setFill(pos.getColor());
        this.gc.fillRect(pos.getxPos(), pos.getyPos(), Grid.POS_LENGTH, Grid.POS_LENGTH);
    }

    public void draw(Food food) {

//        this.draw((GridPos) food);
        this.gc.drawImage(food.getImage(), food.getxPos(), food.getyPos(), Grid.POS_LENGTH, Grid.POS_LENGTH);

    }


}
