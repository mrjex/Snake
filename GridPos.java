public class GridPos {
    private int xPos;
    private int yPos;

    public GridPos(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getxPos() {
        return this.xPos;
    }

    public int getyPos() {
        return this.yPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }  
    
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }  

    @Override
    public String toString() {
        return String.format("[X: %d, Y:%d]", this.xPos, this.yPos);
    }

}
