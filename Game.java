public class Game {
    private Grid gameGrid;

    public Game(){
        this.gameGrid = new Grid();
    }

    public void play(int direction) throws Exception{
        int i = 0;
        while(i < 30){
            long time = System.currentTimeMillis();      
            Thread.sleep((100/3-time%(100/3)));
            System.out.println((i+1) + " " + System.currentTimeMillis()%10000);
            i++;
        }
    }
}
