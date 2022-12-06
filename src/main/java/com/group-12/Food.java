
import javafx.scene.paint.Color;

public class Food extends GridPos {

    private String name;


    public Food(int x, int y, Fruits type) {

        super(x, y,Color.RED);
        this.name = type.getName();


    }

    public String getName() {
        return this.name;
    }



}
