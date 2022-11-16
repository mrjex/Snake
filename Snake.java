import java.util.ArrayList;

public class Snake {
  private GridPos headPos;
  private ArrayList<GridPos> bodyPos;

  public Snake(){
    headPos = new GridPos(1,1);
    bodyPos = new ArrayList<>();
  }
}
