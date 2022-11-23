import java.util.ArrayList;

public class Snake {
    private GridPos headPos;
    private ArrayList<GridPos> bodyPos;
    private int direction; // 0 = north, 1 = west, 2 = south, 3 = east

    public Snake() {
        this.headPos = new GridPos(5, 5);
        this.bodyPos = new ArrayList<>();
        this.direction = 2;
    }

    public boolean updatePos(double WIDTH, double HEIGHT) { //First updates the bodies position and then moves the head
        for (int i = 0; i < bodyPos.size(); i++) {

            if (i < bodyPos.size() - 1) {
                this.bodyPos.set(i, this.bodyPos.get(i + 1));
            } else {
                this.bodyPos.set(i, headPos);
            }
        }
        switch (this.direction) {
            case 0:
                headPos.setyPos(headPos.getyPos()-1);
                break;

            case 1:
                headPos.setxPos(headPos.getxPos()-1);
                break;

            case 2:
                headPos.setyPos(headPos.getyPos()+1);
                break;

            case 3:
                headPos.setxPos(headPos.getxPos()+1);
                break;

            default:
                //Error
                break;
        }
        return checkCollision(WIDTH, HEIGHT);
    }

    public boolean checkCollision(double WIDTH, double HEIGHT){ //Call this after updatePos method to see if snake has collided
        for(GridPos currentPos : bodyPos){
            if(headPos.equals(currentPos)){
                return true;
            }
        }

        if(headPos.getxPos() == WIDTH  && headPos.getyPos()== HEIGHT){return true;}

        return false;
    }
}
