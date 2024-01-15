
public class StringDifference {


	public static int getHamming(String s1, String s2) {
   
		if (s1.length() != s2.length())
			throw (new UnsupportedOperationException());
   
		int j = 0;
		for(int k = 0; k < s1.length(); k++) {
			if(s1.charAt(k) != s2.charAt(k))
				j++;
		}

		return j;	
	}
	
	
	public static int getLevenshtein(String s1, String s2) {
		
		int i = s1.length();
		int j = s2.length();
		
		int ai[][] = new int[i + 1][j + 1];
				
		for(int k = 0; k <= j; k++)
			ai[0][k] = k;

		for(int l = 0; l <= i; l++)
			ai[l][0] = l;

		for(int i1 = 1; i1 <= j; i1++) {
			for(int j1 = 1; j1 <= i; j1++)
				ai[j1][i1] = Math.min(Math.min(ai[j1 - 1][i1] + 1, ai[j1][i1 - 1] + 1), ai[j1 - 1][i1 - 1] + ro(s1, s2, j1, i, i1, j));
		
		}

		return ai[i][j];

	}
	
	 private static int ro(String s1, String s2, int i, int j, int k, int l) {
        if(i > j || k > l)
            return 0;
        return s1.charAt(i - 1) == s2.charAt(k - 1) ? 0 : 1;
    }

    
    public static int getLevenshteinWildcard(String wort, String muster) {
		
		char c;
		int pp, p, q, r;
		int[][] d;
		
		int lw = wort.length();
		int lm = muster.length();
		
		d = new int[lw + 1][lm + 1];

  		d[0][0] = 0;
  		
  		for (int i = 1; i <= lw; i++)
  			d[i][0] = d[i-1][0] + 1;
  		
  			
  		for (int j = 1; j <= lm; j++) {
			c = muster.charAt(j-1);
			
			if ((c == '*') || (c == '?'))
				p = 0;
			else
				p = 1;
				
			if (c == '*') {
				q = 0;
				r = 0;
			} else {
				q = 1;
				r = 1;
			}
			
	       d[0][j] = d[0][j-1] + q;

	       for (int i = 1; i <= lw; i++) {
          
				if (wort.charAt(i-1) == muster.charAt(j-1))  
					pp = 0;
				else 
					pp = p;
                    
				d[i][j] = Math.min(Math.min(d[i-1][j-1] + pp, d[i][j-1] + q), d[i-1][j] + r);
	       }
		}
		return d[lw][lm];
	}

	
	public static void main(String[] args) {
		
		String a = "Ralph";
		String b = "Ral*";
		
		System.out.println(getLevenshtein(a, b));
		System.out.println(getLevenshteinWildcard(a, b));
		System.out.println(getHamming(a, b));
	}

}