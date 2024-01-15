
import java.util.*;


public class Gitter2
{
    /* 0 = weiss   */
    /* 1 = schwarz */

    int gitter[][] = { {0, 0, 1, 0, 1, 0, 0, 0},
                       {0, 0, 1, 0, 0, 1, 1, 1},
                       {0, 0, 0, 0, 0, 1, 0, 0},
                       {0, 0, 0, 0, 0, 0, 0, 0},
                       {1, 0, 0, 0, 0, 0, 0, 0},
                       {0, 1, 0, 0, 0, 1, 0, 0},
                       {1, 0, 1, 0, 1, 1, 1, 1},
                       {0, 0, 1, 0, 0, 1, 0, 0},
                       {0, 0, 1, 0, 0, 1, 0, 0} };

    final int zeilen  = 8;
    final int spalten = 9;

    final int j[] = new int[zeilen];

    int gx1, gy1, gx2, gy2;
    int gflaeche = 0;

    public Gitter2()
    {
        int aktuellespalte;
        int i1, i2, breite;

        for (aktuellespalte = 0; aktuellespalte < spalten; aktuellespalte++)
        {

            vectorUpdate(aktuellespalte);

            for (i1 = 0; i1 < zeilen-1; i1++)
            {
                breite = j[i1];
                for (i2 = i1+1; i2 < zeilen; i2++)
                {
                    if (j[i1] == 0)
                        break;

                    if (j[i2] < breite)
                        breite = j[i2];

                    if (breite == 0)
                    {
                        check(aktuellespalte, i1, 1, i2 - i1 + 1);
                        break;
                    }
                    else
                        check(aktuellespalte, i1, breite, i2 - i1 + 1);
                }
            }
        }

        System.out.println("Groesstes Rechteck ");
        System.out.println("Ecke oben links   :  x = " + gx1 + "  y = " + gy1);
        System.out.println("Ecke unten rechts :  x = " + gx2 + "  y = " + gy2);

	}

    void check(int x, int y, int breite, int hoehe)
    {
        int flaeche = breite * hoehe;
        if (flaeche > gflaeche)
        {
            gflaeche = flaeche;
            gx1 = x;
            gy1 = y;
            gx2 = x + breite - 1;
            gy2 = y + hoehe - 1;
        }
    }

    void vectorUpdate(int s)
    {
        for (int i = 0; i < zeilen; i++)
        {
            if (j[i] > 0)
                j[i]--;
            else
                j[i] = anzahlWeisse(s, i);
        }
    }

    int anzahlWeisse(int x, int y)
    {
        int c = 0;

        while ((x < spalten) && (gitter[x][y] == 0))
        {
            c++;
            x++;
        }

        return c;
    }

    public static void main(String args[])
    {
	    new Gitter2();
    }

}