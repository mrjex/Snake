package Backend;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Grid {

    private final double WIDTH;
    private final double HEIGHT;
    private final int POS_LENGTH = 50;
    private final static ArrayList<GridPos> positions = new ArrayList<>();;
    private Snake snake;

    public Grid() {

        this.WIDTH = 600;
        this.HEIGHT = 600;
        this.drawGrid();
        this.snake = new Snake();

    }


    private void drawGrid() {

        double horizontalPositions = Math.floor(this.WIDTH / POS_LENGTH);
        double verticalPositions = Math.floor(this.HEIGHT / POS_LENGTH);

        for (int x = 0; x < horizontalPositions; x++) {

            for (int y = 0; y < verticalPositions; y++) {
                if( (x + y) % 2 == 0) {
                    GridPos addedPosition = new GridPos(x * POS_LENGTH, y * POS_LENGTH, Color.web("17E16E"));
                    Grid.positions.add(addedPosition);
                }else{
                    GridPos addedPosition = new GridPos(x * POS_LENGTH, y * POS_LENGTH, Color.web("23BB64"));
                    Grid.positions.add(addedPosition);
                }
            }

        }

    }
    public ArrayList<GridPos> getPositions(){
        return Grid.positions;
    }
    public GridPos spawnFood() {

        int randomIndex = (int) (Math.random() * Grid.positions.size());

        GridPos position = Grid.positions.get(randomIndex); // save this to get the x and y
        Food apple = new Food(position.getxPos(), position.getyPos(), Fruits.APPLE);

        Grid.positions.set(randomIndex, apple);

        return apple;


    }
    public GridPos getHeadPos(){
        return snake.getHeadPos();
    }

    public boolean moveSnake(){
        return snake.updatePos(this.WIDTH, this.HEIGHT);
    }

    @Override
    public String toString() {
        return Grid.positions.toString();
    }
    public void setDirection(int newDirection){
        snake.setDirection(newDirection);
    }
    public int getDirection(){
        return snake.getDirection();
    }
    public void grow(){
        snake.grow();
    }
    public ArrayList<GridPos> getBodyPos(){
        return snake.getBodyPos();
    }
}
