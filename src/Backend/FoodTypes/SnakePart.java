package Backend.FoodTypes;

import Backend.GridPos;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class SnakePart extends GridPos {
    private static final Image image = new Image("resources/assets/Red_circle.svg.png");;
    public SnakePart(int xPos, int yPos, Color color) {
        super(xPos, yPos, color);
        }

    public Image getImage() {
        return image;
    }
}

