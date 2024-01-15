package gtf.common;

/**
 * CheckSumCalculator
 * Utility class. Provides checking and computing a checksum of a cif
 * 
 * @author Ralph Schaer
 * @version 1.0, 5.7.1999
 */
public final class CheckSumCalculator {
	private static int mult[];
	static {
		mult = new int[11];
		mult[0] = 6;
		mult[1] = 5;
		mult[2] = 4;
		mult[3] = 3;
		mult[4] = 2;
		mult[5] = 7;
		mult[6] = 6;
		mult[7] = 5;
		mult[8] = 4;
		mult[9] = 3;
		mult[10] = 2;
	} 

	/**
	 * Compute the checksum of a 11 character cif
	 * @param cifStr
	 * @return checksum
	 * @see
	 */
	public static int calc(String cifStr) {
		if (cifStr.length() != 11) {
			throw new IllegalArgumentException("argument must have 11 characters " 
														  + cifStr);
		} 

		char[] cif = cifStr.toCharArray();
		int total = 0;

		for (int i = 0; i < 11; i++) {
			total += (Character.getNumericValue(cif[i]) * mult[i]);
		} 

		int mod = 11 - (total % 11);

		if (mod > 9) {
			return 0;
		} else {
			return mod;
		} 
	} 

	/**
	 * Check if a cif have the correct check sum
	 * @param cifStr
	 * @return true = checksum ok, false = checksum not ok
	 */
	public static boolean check(String cifStr) {

		if (cifStr.length() != 12) {
			throw new IllegalArgumentException("argument must have 12 characters " 
														  + cifStr);
		} 

		if (Branches.getRZ(cifStr.substring(0, 4)) == 2) {
			int pz = calc(cifStr.substring(0, 11));

			if (pz == (Character.getNumericValue(cifStr.toCharArray()[11]))) {
				return true;
			} else {
				return false;
			} 
		} else {
			return true;	// bei RZ3 Cifs immer true
		} 
	} 

	public static void main(String args[]) {
		System.out.println(CheckSumCalculator.calc("08929400510"));
		System.out.println(CheckSumCalculator.calc("08969400510"));
	}
}