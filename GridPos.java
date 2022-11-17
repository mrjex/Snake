public class GridPos {
  int xPos;
  int yPos; 

  public GridPos(int xPos, int yPos){
    this.xPos = xPos;
    this.yPos = yPos;
  }

  public void move(int direction){ //Moves the position of the head depending on which direction the snake is moving in
    switch (direction) {
      case 0:
        this.yPos -= 1; 
      break;

      case 1:
        this.xPos -= 1; 
      break;

      case 2:
        this.yPos += 1; 
      break;

      case 3:
        this.xPos += 1; 
      break;

      default:
        //Error
      break;
    }
  }
}
