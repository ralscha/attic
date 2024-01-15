

public class LottoSystem
{

    int fix[] = {1,2};
    int var[] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16};
    int nfix = 2;
    int nvar = 14;

    int zr[] = new int[6];
    int l[] = new int[6];
    int anz = 0;

    public static void main(String args[])
    {
        new LottoSystem();
    }

    public LottoSystem()
    {
        go();
    }

    void go()
    {
        for (int i = 0; i < nfix; i++)
            zr[i] = fix[i];

        for (l[0] = start(0); l[0] < nvar; l[0]++)
        {
            for (l[1] = start(1); l[1] < nvar; l[1]++)
            {
                for (l[2] = start(2); l[2] < nvar; l[2]++)
                {
                    for (l[3] = start(3); l[3] < nvar; l[3]++)
                    {
                        for (l[4] = start(4); l[4] < nvar; l[4]++)
                        {
                            for (l[5] = start(5); l[5] < nvar; l[5]++)
                            {
                                for (int i = nfix; i < 6; i++)
                                    zr[i] = var[l[i]];
                                printZR();
                            }
                        }
                    }
                }

            }

        }

        System.out.println("Anzahl Moeglichkeiten "+anz);

    }

    int start(int loop)
    {
        if (loop < nfix)       return(nvar-1);
        else if (loop == nfix) return(0);
        else                   return(l[loop-1]+1);
    }

    void printZR()
    {
        anz++;
        for (int i = 0; i < 5; i++)
        {
            if (zr[i] < 10)
                System.out.print(" ");
            System.out.print(""+zr[i]+",");
        }
        if (zr[5] < 10)
            System.out.print(" ");
        System.out.print(zr[5]);
        System.out.println();
    }

}
