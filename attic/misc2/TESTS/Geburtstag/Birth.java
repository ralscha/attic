
import java.awt.*;
import java.util.Random;

public class Birth
{
    final static int versuche = 5000;

    public static void main(String args[])
    {
        Random rand = new Random();
        boolean auto = true;
        boolean punkt = false;
        int punktcounter = 0;

        for (int x = 0; x < versuche; x++)
        {
            for (int i = 0; i < 467; i++)
            {
                if (auto)
                {
                    if (rand.nextFloat() < 0.5) /* Auto verspaetet */
                    {
                        auto = false;
                        punkt = false;
                    }
                    else
                        punkt = true;
                }
                else
                {
                    if (rand.nextFloat() < 0.25) /* U-Bahn verspaet */
                    {
                        auto = true;
                        punkt = false;
                    }
                    else
                        punkt = true;
                }
            }

            if (punkt)
                punktcounter++;
        }

        System.out.println("Puenktlich : " + punktcounter);
        System.out.println("Verspaetet : " + (versuche - punktcounter));
        System.out.println("Chance     : " + ((double)punktcounter / (double)versuche));
    }
}