

public class IntegerConversion {

	public static void main(String[] args) {
		new IntegerConversion();
	}

	public IntegerConversion() {
		int i;
		//Warmup
		for (i = 0; i < 100; i++) {
			convert(String.valueOf(i));
		}
	
		long start = System.currentTimeMillis();
		for (i = 0; i < 900000; i++) {
			convert(String.valueOf(i));
		}
		System.out.println(System.currentTimeMillis() - start);
		
		start = System.currentTimeMillis();
		for (i = 0; i < 900000; i++) {
			Integer.parseInt(String.valueOf(i));
		}
		System.out.println(System.currentTimeMillis() - start);
				
	}

	private int convert(String s) {
		int n=0,
		len = s.length();
		for ( int i = 0; i < len; i++ )
		n = n*10 + s.charAt(i) - '0';
		return n;
	}


}