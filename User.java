public class User 
{
    private final String name;
    private final int password;
    private int wins;
    private int losses;
    private int draws;
    
    public User(String name, int password)
    {
        this.name = name;
        this.password = password;
        wins = 0;
        losses = 0;
        draws = 0;
    }

    public String getName()
    {
        return name;
    }

    public int getPassword()
    {
        return password;
    }

    public int getWins()
    {
        return wins;
    }

    public int getLosses()
    {
        return losses;
    }

    public int getDraws()
    {
        return draws;
    }

    public void giveWin()
    {
        wins++;
    }

    public void giveLoss()
    {
        losses++;
    }

    public void giveDraw()
    {
        draws++;
    }

    public String recordString()
    {
        return getName() + " has " + getWins() + " wins, " + getLosses() + " losses, and " + getDraws() + " draws.";
    }
}
