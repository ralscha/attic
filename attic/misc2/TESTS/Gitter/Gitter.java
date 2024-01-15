
import java.awt.*;
import java.util.*;


public class Gitter
{

    int gitter[][] = { {1, 0, 0, 1, 0},
                       {0, 0, 0, 0, 0},
                       {0, 0, 0, 0, 0},
                       {0, 1, 1, 0, 0},
                       {0, 1, 0, 0, 1} };

    int zeilen  = 5;
    int spalten = 5;

    Vector arealist = new Vector();

    public Gitter()
    {

        /* Dem Rand entlang */
        int l;

        int x1, y1, x2, y2;

        /* Oberer Rand */
        for (int x = 0; x < spalten; x++)
        {
            l = SucheNachUnten(x, -1);
            if (l != 0)
            {
                y1 = 0;
                y2 = l - 1;
                x1 = SucheZeileNachLinks(y1, y2, x);
                x2 = SucheZeileNachRechts(y1, y2, x);

                add(x1, y1, x2, y2);
            }
        }

        /* Unterer Rand */
        for (int x = 0; x < spalten; x++)
        {
            l = SucheNachOben(x, zeilen);
            if (l != 0)
            {
                y1 = zeilen - l;
                y2 = zeilen-1;
                x1 = SucheZeileNachLinks(y1, y2, x);
                x2 = SucheZeileNachRechts(y1, y2, x);

                add(x1, y1, x2, y2);
            }
        }

        /* Linker Rand */
        for (int y = 0; y < zeilen; y++)
        {
            l = SucheNachLinks(spalten, y);
            if (l != 0)
            {
                x1 = spalten - l;
                x2 = spalten - 1;;
                y1 = SucheZeileNachOben(x1, x2, y);
                y2 = SucheZeileNachUnten(x1, x2, y);

                add(x1, y1, x2, y2);
            }
        }

        /* Rechter Rand */
        for (int y = 0; y < zeilen; y++)
        {
            l = SucheNachRechts(-1, y);
            if (l != 0)
            {
                x1 = 0;
                x2 = -1 + l;
                y1 = SucheZeileNachOben(x1, x2, y);
                y2 = SucheZeileNachUnten(x1, x2, y);

                add(x1, y1, x2, y2);
            }
        }

        /* Jedes schwarze Feld pruefen */

        for (int px = 0; px < spalten; px++)
        {
            for (int py = 0; py < zeilen; py++)
            {
                if (gitter[px][py] == 1)
                {
                    l = SucheNachOben(px, py);
                    if (l != 0)
                    {
                       y1 = py - l;
                       y2 = py-1;
                       x1 = SucheZeileNachLinks(y1, y2, px);
                       x2 = SucheZeileNachRechts(y1, y2, px);

                       add(x1, y1, x2, y2);
                    }

                    l = SucheNachUnten(px, py);
                    if (l != 0)
                    {
                        y1 = py + 1;
                        y2 = py + l;
                        x1 = SucheZeileNachLinks(y1, y2, px);
                        x2 = SucheZeileNachRechts(y1, y2, px);

                        add(x1, y1, x2, y2);
                    }

                    l = SucheNachRechts(px, py);
                    if (l != 0)
                    {
                        x1 = px + 1;
                        x2 = px + l;
                        y1 = SucheZeileNachOben(x1, x2, py);
                        y2 = SucheZeileNachUnten(x1, x2, py);

                        add(x1, y1, x2, y2);
                    }

                    l = SucheNachLinks(px, py);
                    if (l != 0)
                    {
                        x1 = px - l;
                        x2 = px - 1;
                        y1 = SucheZeileNachOben(x1, x2, py);
                        y2 = SucheZeileNachUnten(x1, x2, py);

                        add(x1, y1, x2, y2);
                    }


                }
            }
        }



        /* Groesstes Feld heraussuchen */
        Enumeration e = arealist.elements();

        while(e.hasMoreElements())
        {
            System.out.println((Area)e.nextElement());
        }

	}


    public static void main(String args[])
    {
	    new Gitter();
    }


    int SucheNachOben(int x, int y)
    {
        int c = 0;
        y--;
        while (y >= 0 && gitter[x][y] == 0)
        {
            c++;
            y--;
        }

        return c;
    }

    int SucheNachUnten(int x, int y)
    {
        int c = 0;
        y++;
        while (y < zeilen && gitter[x][y] == 0)
        {
            c++;
            y++;
        }

        return c;

    }

    int SucheNachLinks(int x, int y)
    {
        int c = 0;
        x--;
        while (x >= 0 && gitter[x][y] == 0)
        {
            c++;
            x--;
        }

        return c;
    }

    int SucheNachRechts(int x, int y)
    {
        int c = 0;
        x++;
        while (x < spalten && gitter[x][y] == 0)
        {
            c++;
            x++;
        }

        return c;
    }

    int SucheZeileNachOben(int x1, int x2, int y)
    {
        if (y == 0) return y;

        y--;
        while (y >= 0)
        {
            for (int n = x1; n <= x2; n++)
            {
                if (gitter[n][y] == 1) return ++y;
            }
            y--;
        }
        return 0;
    }

    int SucheZeileNachUnten(int x1, int x2, int y)
    {
        if (y == zeilen-1) return y;

        y++;
        while (y < zeilen)
        {
            for (int n = x1; n <= x2; n++)
            {
                if (gitter[n][y] == 1) return --y;
            }
            y++;
        }
        return (zeilen-1);
    }

    int SucheZeileNachLinks(int y1, int y2, int x)
    {
        if (x == 0) return x;

        x--;
        while (x >= 0)
        {
            for (int n = y1; n <= y2; n++)
            {
                if (gitter[x][n] == 1) return ++x;
            }
            x--;
        }
        return 0;
    }

    int SucheZeileNachRechts(int y1, int y2, int x)
    {
        if (x == spalten-1) return x;

        x++;
        while (x < spalten)
        {
            for (int n = y1; n <= y2; n++)
            {
                if (gitter[x][n] == 1) return --x;
            }
            x++;
        }
        return (spalten-1);
    }


    void add(int x1, int y1, int x2, int y2)
    {
        Area a = new Area(x1, y1, x2, y2);
        boolean found = false;

        Enumeration e = arealist.elements();

        while(!found && e.hasMoreElements())
        {
            found = a.equals((Area)e.nextElement());
        }

        if (!found)
            arealist.addElement(a);

    }

}