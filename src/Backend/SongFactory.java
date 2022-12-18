package Backend;

public class SongFactory // Experiment
{
    public static ChillList chillList = new ChillList(new String[]{"Song here"}, 0);

    // Return ChillList?
    public static ChillList createList()
    {
        return new ChillList(new String[]{"Song here"}, 0);
    }
}
