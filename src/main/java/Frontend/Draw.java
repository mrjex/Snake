package Frontend;

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
            this.gc.fillRect(position.getxPos(), position.getyPos(), 50, 50);

        }



    }
    public void draw(GridPos gp){

    }
}
