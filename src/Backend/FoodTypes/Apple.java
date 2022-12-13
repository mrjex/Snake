package Backend.FoodTypes;

import Backend.Food;
import javafx.scene.paint.Color;

public class Apple extends Food {

    public Apple(int xPos, int yPos){
        super(xPos, yPos, 1);
        super.setProbability(0.6);
        super.setColor(Color.RED);
        this.setImage("resources/assets/apple.png");
    }
}
