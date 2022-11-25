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

    public void updatePos() { //First updates the bodies position and then moves the head
        for (int i = 0; i < bodyPos.size(); i++) {

            if (i < bodyPos.size() - 1) {
                this.bodyPos.set(i, this.bodyPos.get(i + 1));
            } else {
                this.bodyPos.set(i, headPos);
            }
        }
        this.headPos.move(direction);
    }

    public void grow() {

        //when collision with food is detected this method will be called
        this.bodyPos.add(headPos);

    }

}
