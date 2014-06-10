package sorting_hat.data;

/**
 * This class stores game results for a given level. Note that this is
 * just a data holding class. It will be manipulated fully by the
 * SortingHatRecord class, which stores all the records and manages
 * loading and saving.
 * 
 * @author Richard McKenna & __Sarmeet Singh__
 */
public class SortingHatLevelRecord
{
    public String algorithm;
    public int gamesPlayed;
    public int wins;
    public int perfectWins;
    public String perfetTime;
    public long perfectMilisecs;
}