package Backend;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Grid {

    private final double WIDTH;
    private final double HEIGHT;
    private final int POS_LENGTH = 50;
    private final static ArrayList<GridPos> positions = new ArrayList<>();;
    private Snake snake;

    public Food food;

    public Grid() {

        this.WIDTH = 600;
        this.HEIGHT = 600;
        this.drawGrid();
        this.snake = new Snake();
        this.food = this.spawnFood();

    }


    private void drawGrid() {

        double horizontalPositions = Math.floor(this.WIDTH / POS_LENGTH);
        double verticalPositions = Math.floor(this.HEIGHT / POS_LENGTH);

        for (int x = 0; x < horizontalPositions; x++) {

            for (int y = 0; y < verticalPositions; y++) {

                Color color = (x+y) % 2 == 0 ? Color.web("17E16E") : Color.web("23BB64");
                GridPos addedPosition = new GridPos(x * POS_LENGTH, y * POS_LENGTH, color);
                Grid.positions.add(addedPosition);

            }

        }

    }
    public ArrayList<GridPos> getPositions(){
        return Grid.positions;
    }
    public Food spawnFood() {

        int randomIndex = (int) (Math.random() * Grid.positions.size());
        Random random = new Random();
        int randomNum = random.nextInt(100);

        GridPos position = Grid.positions.get(randomIndex); // save this to get the x and y
        Food returnedFood = new Apple(position.getxPos(), position.getyPos());

        //Uses variable randomNum to give a percentage of different food to spawn
        if(randomNum >= 70 && randomNum < 90){
            returnedFood = new Pizza(position.getxPos(), position.getyPos());
        }else if(randomNum >= 90){
            returnedFood = new HotDog(position.getxPos(), position.getyPos());
        }

        return returnedFood;
    }
    public GridPos getHeadPos(){
        return snake.getHeadPos();
    }

    public int moveSnake(){
        int code = snake.updatePos(this.WIDTH, this.HEIGHT, this.food);
        if(code == 1) {

            this.food = this.spawnFood();
        }
        return code;
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

    public Food getFood() { return this.food; }
    public ArrayList<GridPos> getBodyPos(){
        return snake.getBodyPos();
    }
}
