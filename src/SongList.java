import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.TimerTask;

// Sources retrieved:
// https://stackoverflow.com/questions/12908412/print-hello-world-every-x-seconds (2022-12-15): The one with 210+ upvotes
// https://www.youtube.com/watch?v=wJO_cq5XeSA (2022-12-15)

        /*
            // Ideas for improvement that will be implemented by JoelM soon:
            * Once the list of predetermined songs is done, it will loop from the beginning again
            * Make a general loop in MainGame.java dependent on the variables retrieved from the songs in this class
            * Make the relationship between this class and MainGame.java clean and readable (smooth code and good comments)
         */

public class SongList extends TimerTask
{
    public int i; // Private?
    public int numberOfTimesToRun; // Private?

    private int currentNumberOfRuns;
    private String[] filePaths = {"SkyHighTrinitySnakeBackgroundSound2.wav", "RobotSnakeBackgroundSound.wav"};

    public SongList(int i, int numberOfTimesToRun)
    {
        this.i = i;
        this.numberOfTimesToRun = numberOfTimesToRun;
        currentNumberOfRuns = 0;
    }

    @Override
    public void run()
    {
        playBackgroundSongs();

        // Stop repeatedly executing when the predetermined times to run is fulfilled
        if (++currentNumberOfRuns == numberOfTimesToRun)
        {
            cancel();
        }
    }

    public void playBackgroundSongs()
    {
        System.out.println("Song number " + String.valueOf((i + 1)) + " is playing!");

        try
        {
            File musicPath = new File(this.filePaths[i]);

            if (musicPath.exists())
            {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();

                clip.open(audioInput);
                clip.start();

                // System.out.println(clip.getMicrosecondLength());
                // System.out.println(clip.getMicrosecondPosition());
            }
            else
            {
                System.out.println("File was not found!");
            }
        }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
        }
    }
}
