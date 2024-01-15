import java.io.Serializable;

public class Tip implements Serializable
{
	private int nr;
	private int zahlen[] = new int[6];

    public Tip(int nr, int zahlen[])
    {
        this.nr = nr;
        for (int i = 0; i < 6; i++)
            this.zahlen[i] = zahlen[i];

    }

    public int[] getZahlen()
    {
        return(zahlen);
    }

    public int getNr()
    {
        return(nr);
    }

}

