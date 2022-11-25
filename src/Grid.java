import java.util.ArrayList;

public class Grid {

    private final double WIDTH;
    private final double HEIGHT;
    private final int POS_LENGTH = 1;
    private final ArrayList<GridPos> positions;
    private Snake snake;

    public Grid() {

        this.WIDTH = 800;
        this.HEIGHT = 600;
        this.positions = new ArrayList<>();
        this.drawGrid();
        this.snake = new Snake();

    }


    private void drawGrid() {

        double horizontalPositions = Math.floor(this.WIDTH / POS_LENGTH);
        double verticalPositions = Math.floor(this.HEIGHT / POS_LENGTH);

        for (int x = 0; x < horizontalPositions; x++) {

            for (int y = 0; y < verticalPositions; y++) {

                GridPos addedPosition = new GridPos(x * POS_LENGTH, y * POS_LENGTH);
                this.positions.add(addedPosition);

            }

        }

        System.out.println("Generated a " + this.WIDTH + "x" + this.HEIGHT + " grid with units of size " + this.POS_LENGTH + ".");

    }

    public boolean moveSnake(){
        return snake.updatePos(this.WIDTH, this.HEIGHT); 
    }

    @Override
    public String toString() {
        return this.positions.toString();
    }
}
