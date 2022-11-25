package Backend;

public class Food extends GridPos {

    private String name;
    private String color;

    public Food(int x, int y, Fruits type) {

        super(x, y);
        this.name = type.getName();
        this.color = type.getColor();

    }

    public String getName() {
        return this.name;
    }

    public String getColor() {
        return this.color;
    }

}
