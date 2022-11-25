package Backend;

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

    @Override
    public boolean equals(Object obj) { //Checks if yPos and xPos are equal and if they are returns true
        if(obj == null){ return false;} //Not sure about the implementation, should work but if you want to change it feel free
        if(obj == this){return true;}
        else if(obj instanceof GridPos){
            GridPos otherGridPos = (GridPos) obj;
            if(this.xPos == otherGridPos.getxPos() && this.yPos == otherGridPos.getyPos()){return true;}
        }
        return false;
    }

}
