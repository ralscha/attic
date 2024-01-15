package gtf.util;
 
public final class CheckSumCalculator {

	private static int mult[];
	
	static {
		mult = new int[11];
		mult[ 0] = 6;
		mult[ 1] = 5;
		mult[ 2] = 4;
		mult[ 3] = 3;
		mult[ 4] = 2;
		mult[ 5] = 7;
		mult[ 6] = 6;
		mult[ 7] = 5;
		mult[ 8] = 4;
		mult[ 9] = 3;
		mult[10] = 2;
	}

	public static int calc(String cifStr) {
		if (cifStr.length() != 11) 
			throw new IllegalArgumentException("argument must have 11 characters " + cifStr);
		
	
		char[] cif = cifStr.toCharArray();

		int total = 0;
		for (int i = 0; i < 11; i++) 
			total += (Character.getNumericValue(cif[i]) * mult[i]);

		int mod = 11 - (total % 11);
		if (mod > 9)
			return 0;
		else
			return mod;
	}
 
	public static boolean check(String cifStr) {
		if (cifStr.length() != 12) 
			throw new IllegalArgumentException("argument must have 12 characters " + cifStr);

		if (credoc.Branches.getInstance().getRZ(cifStr.substring(0,4)) == 2) {
		int pz = calc(cifStr.substring(0,11));
	
		if (pz == (Character.getNumericValue(cifStr.toCharArray()[11]))) 
			return true;
		else
			return false;
		} else {
		return true; // bei RZ3 Cifs immer true
		}
			
	}
}