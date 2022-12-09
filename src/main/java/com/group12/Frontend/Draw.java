package com.group12.Frontend;

import com.group12.Backend.Food;
import com.group12.Backend.GridPos;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Draw {
    private GraphicsContext gc;

    public Draw (GraphicsContext gc){
        this.gc = gc;
    }

    public void drawBackground(ArrayList<GridPos> positions){
        for(GridPos position : positions){
            this.gc.setFill(position.getColor());
            this.gc.fillRect(position.getxPos(), position.getyPos(), 50, 50);
        }
    }
    public void draw(GridPos pos){
        this.gc.setFill(pos.getColor());
        this.gc.fillRect(pos.getxPos(), pos.getyPos(), 50, 50);
    }

    public void draw(Food food) {

//        this.draw((GridPos) food);
        this.gc.drawImage(food.getImage(), food.getxPos(), food.getyPos(), 50, 50);

    }


}
