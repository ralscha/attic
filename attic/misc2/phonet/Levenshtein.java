public class Levenshtein {
	String a, b;

	int r(int i, int j) {
		//count from -1 to length()
		if (a.charAt(i - 1) == b.charAt(j - 1))
			return 0;
		return 1;
	}

	int distance(int i, int j) {
		//Row or column?
		if (j == 0)//first item of each row

			return i;
		if (i == 0)//first item of each column

			return j;

		//get the minimum of 3 possibilities
		//  (i-1,j)+1   (i,j-1)+1 and (i-1,j-1)+r(i,j)
		int d = distance(i - 1, j) + 1;
		int t = distance(i, j - 1) + 1;
		if (t < d)
			d = t;
		t = distance(i - 1, j - 1) + r(i, j);
		if (t < d)
			d = t;
		return d;
	}

	public int distance(String aval, String bval) {
		a = aval;
		b = bval;
		return distance(a.length(), b.length());
	}

	public Levenshtein() {
	}

	public void compare(String a, String b) {
		System.out.println("a:"+a + "  b:"+b + " dis:"+distance(a, b));
	}

	//TESTING
	public static void main(String argv[]) {
		Levenshtein l = new Levenshtein();

		l.compare("BANKUFKINA","BANKOFKINA");

	}
}