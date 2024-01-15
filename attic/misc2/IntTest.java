

public class IntTest
{

    public static void main(String args[])
    {
        long start, stop;
        int zahlen[];
        IntClass ic = new IntClass(6);
        zahlen = ic.getZahlen1();

       /*
        for (int i = 0; i < zahlen.length; i++)
        {
            System.out.println(zahlen[i]);
        }
*/
        zahlen[3] = 99;

       	start = System.currentTimeMillis();
       	for (int i = 0; i < 10000; i++)
            zahlen = ic.getZahlen1();
        stop = System.currentTimeMillis();
        System.out.println("clone    "+(stop-start));

        start = System.currentTimeMillis();
       	for (int i = 0; i < 10000; i++)
            zahlen = ic.getZahlen2();
        stop = System.currentTimeMillis();
        System.out.println("arrcopy  "+(stop-start));

        start = System.currentTimeMillis();
       	for (int i = 0; i < 10000; i++)
            zahlen = ic.getZahlen3();
        stop = System.currentTimeMillis();
        System.out.println("for      "+(stop-start));


         start = System.currentTimeMillis();
       	for (int i = 0; i < 100000; i++)
            zahlen = ic.getZahlen4();
        stop = System.currentTimeMillis();
        System.out.println("ohne      "+(stop-start));


/*
        for (int i = 0; i < zahlen.length; i++)
        {
            System.out.println(zahlen[i]);
        }

*/
    }
}
