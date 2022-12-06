package Backend;

import javafx.scene.paint.Color;

public class Pizza extends Food{

    public Pizza(int xPos, int yPos){
        super(xPos, yPos, 3);
        super.setColor(Color.YELLOW);
    }
}
