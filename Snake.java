import java.util.ArrayList;

public class Snake {
  private GridPos headPos;
  private ArrayList<GridPos> bodyPos;
  private int direction; // 0 = north, 1 = west, 2 = south, 3 = east

  public Snake(){
    this.headPos = new GridPos(5,5);
    this.bodyPos = new ArrayList<>();
    this.direction = 2;
  }

  public void updatePos(){
    for(int i = 0; ){

    }
     headPos.move(direction); 
  }
}
