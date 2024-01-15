import java.util.Random;

public class Biest
{
    private int gen[] = new int[6]; /* v, r, sr, rw, sl, l */
    private int i;
    private int moves;
    private int energy;
    private int richt;
    Random r;

    private static int field[];

    public Biest()
    {
        moves = 0;
        energy = 0;
        r = new Random();
    }


    public void randomInit()
    {
        for (i = 0; i < 6; i++)
            gen[i] = r.nextInt() % 10;

        energy = r.nextInt() % 50 + 50;
        richt = r.nextInt() % 6;
    }

    public void incMove()
    {
        moves++;
        energy--;
    }

    public void incEnergy()
    {
        energy += 40;
        if (energy > 1500) energy = 1500;
    }

    public boolean canRepro()
    {
        if ((moves >= 800) && (energy >= 1000))
            return true;
        else
            return false;
    }

    public int move()
    {
        int total = 0;
        for (i = 0; i < 6; i++)
            total += abs(genar[i]);

        int zufall = abs((r.nextInt() % total) + 1);
		int summe = 0;
		int x = 0;
		summe = abs(gen[x]);
        while (summe < zufall)
	        summe += abs(gen[++x]);


        richt = (richt + x) % 6;
		return(richt);
    }

    private int abs(int v)
    {
        if (v < 0)
            return(-v);
        else
            return(v);
    }
}