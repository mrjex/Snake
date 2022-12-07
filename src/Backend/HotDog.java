package Backend;

import javafx.scene.paint.Color;

public class HotDog extends Food{

    public HotDog(int xPos, int yPos){
        super(xPos,yPos,2);
        super.setProbability(0.3);
        super.setColor(Color.BROWN);
    }
}
