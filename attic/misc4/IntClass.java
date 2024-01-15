package Lotto;

public class IntClass
{
    int SIZE;
    int zahlen[];


    public IntClass(int SIZE)
    {
        this.SIZE = SIZE;
        zahlen = new int[SIZE];

        for (int i = 0; i < SIZE; i++)
            zahlen[i] = i;
    }

    public int[] getZahlen1()
    {
        return (int[])zahlen.clone();
    }

    public int[] getZahlen2()
    {
        int z[] = new int[SIZE];
        System.arraycopy(zahlen,0,z,0,zahlen.length);
        return z;
    }

    public int[] getZahlen3()
    {
	    int z[] = new int[SIZE];

	    for (int i = 0; i < SIZE; i++)
    		z[i] = zahlen[i];

    	return z;
	}

	public int[] getZahlen4()
	{
	    return zahlen;
	}
}