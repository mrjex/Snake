package Backend.FoodTypes;

import Backend.Food;
import javafx.scene.paint.Color;

public class Pizza extends Food {

    public Pizza(int xPos, int yPos){

        super(xPos, yPos, 3);
        super.setColor(Color.YELLOW);
        super.setImage("resources/assets/pizza.png");

    }
}
