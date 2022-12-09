package com.group12.Backend.FoodTypes;

import com.group12.Backend.Food;
import javafx.scene.paint.Color;

public class Pizza extends Food {

    public Pizza(int xPos, int yPos){

        super(xPos, yPos, 3);
        super.setProbability(0.1);
        super.setColor(Color.YELLOW);
        super.setImage("resources/Assets/pizza.png");

    }
}
