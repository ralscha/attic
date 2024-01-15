
public class SearchAlgo
{
    String needle;
    int skip[] = new int[10];
    int nadelLen;

    private int index(char c)
    {
	    return c - 48;
    }

    SearchAlgo(String needle)
    {
        this.needle = needle;
        nadelLen = needle.length();

        for (int i = 0; i < 9; i++)
		    skip[i] = nadelLen;

    	for (int j = 0; j < nadelLen; j++)
	    	skip[index(needle.charAt(j))] = nadelLen-j-1;
    }

    int mischarsearch(String hay, int startPos)
    {
    	int i, j, t, heuLen;
    	heuLen = hay.length();

    	if (heuLen-startPos < nadelLen)
    	    return heuLen;

    	for (i = startPos + (nadelLen-1), j = nadelLen-1; j >= 0; i--, j--)
	    {
		    while(hay.charAt(i) != needle.charAt(j))
    		{
	    		t = skip[index(hay.charAt(i))];
	    		if (nadelLen-j > t)
	    		    i += nadelLen-j;
	    		else
	    		    i += t;

			    if (i >= heuLen)
				    return heuLen;

    			j = nadelLen-1;
	    	}
    	}
    	return i+1;
    }


}