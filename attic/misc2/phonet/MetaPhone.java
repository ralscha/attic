public class MetaPhone {

	
	private final static String frontv = "EIY";
	private final static String vowels = "AEIOU";
	private final static String varson = "CSPTG";	
	
	public static String metaPhone(String s) {
		int i = 0;
		boolean flag = false;
		
		if (s == null || s.length() == 0)
			return "";
		
		if (s.length() == 1)
			return s.toUpperCase();
		
		char ac[] = s.toUpperCase().toCharArray();
		
		StringBuffer stringbuffer = new StringBuffer(40);
		StringBuffer stringbuffer1 = new StringBuffer(10);
		
		switch (ac[0]) {
			case 'G':
			case 'K':
			case 'P':
				if (ac[1] == 'N')
					stringbuffer.append(ac, 1, ac.length - 1);
				else
					stringbuffer.append(ac);
				break;

			case 'A':
				if (ac[1] == 'E')
					stringbuffer.append(ac, 1, ac.length - 1);
				else
					stringbuffer.append(ac);
				break;

			case 'W':
				if (ac[1] == 'R') {
					stringbuffer.append(ac, 1, ac.length - 1);
					break;
				}
				if (ac[1] == 'H') {
					stringbuffer.append(ac, 1, ac.length - 1);
					stringbuffer.setCharAt(0, 'W');
				} else {
					stringbuffer.append(ac);
				}
				break;

			case 'X':
				ac[0] = 'S';
				stringbuffer.append(ac);
				break;

			default:
				stringbuffer.append(ac);
				break;

		}
		
		int j = stringbuffer.length();
		for (int k = 0; i < 4 && k < j;) {
			char c = stringbuffer.charAt(k);
			if (c != 'C' && k > 0 && stringbuffer.charAt(k - 1) == c) {
				k++;
			} else {
				switch (c) {
					default:
						break;

					case 'A':
					case 'E':
					case 'I':
					case 'O':
					case 'U':
						if (k == 0) {
							stringbuffer1.append(c);
							i++;
						}
						break;

					case 'B':
						if (k > 0 && k + 1 != j && stringbuffer.charAt(k - 1) == 'M')
							stringbuffer1.append(c);
						else
							stringbuffer1.append(c);
						i++;
						break;

					case 'C':
						if (k > 0 && stringbuffer.charAt(k - 1) == 'S' && k + 1 < j &&
    							frontv.indexOf(stringbuffer.charAt(k + 1)) >= 0)
							break;
						String s1 = stringbuffer.toString();
						if (s1.indexOf("CIA", k) == k) {
							stringbuffer1.append('X');
							i++;
							break;
						}
						if (k + 1 < j && frontv.indexOf(stringbuffer.charAt(k + 1)) >= 0) {
							stringbuffer1.append('S');
							i++;
							break;
						}
						if (k > 0 && s1.indexOf("SCH", k - 1) == k - 1) {
							stringbuffer1.append('K');
							i++;
							break;
						}
						if (s1.indexOf("CH", k) == k) {
							if (k == 0 && j >= 3 && vowels.indexOf(stringbuffer.charAt(2)) < 0)
								stringbuffer1.append('K');
							else
								stringbuffer1.append('X');
							i++;
						} else {
							stringbuffer1.append('K');
							i++;
						}
						break;

					case 'D':
						if (k + 2 < j && stringbuffer.charAt(k + 1) == 'G' &&
    							frontv.indexOf(stringbuffer.charAt(k + 2)) >= 0) {
							stringbuffer1.append('J');
							k += 2;
						} else {
							stringbuffer1.append('T');
						}
						i++;
						break;

					case 'G':
						if (k + 2 == j && stringbuffer.charAt(k + 1) == 'H' || k + 2 < j &&
    							stringbuffer.charAt(k + 1) == 'H' &&
    							vowels.indexOf(stringbuffer.charAt(k + 2)) < 0)
							break;
						String s2 = stringbuffer.toString();
						if (k > 0 && s2.indexOf("GN", k) == k || s2.indexOf("GNED", k) == k)
							break;
						boolean flag1;
						if (k > 0 && stringbuffer.charAt(k - 1) == 'G')
							flag1 = true;
						else
							flag1 = false;
						if (k + 1 < j && frontv.indexOf(stringbuffer.charAt(k + 1)) >= 0 &&
    							!flag1)
							stringbuffer1.append('J');
						else
							stringbuffer1.append('K');
						i++;
						break;

					case 'H':
						if (k + 1 != j && (k <= 0 ||
                    							varson.indexOf(stringbuffer.charAt(k - 1)) < 0) &&
    							vowels.indexOf(stringbuffer.charAt(k + 1)) >= 0) {
							stringbuffer1.append('H');
							i++;
						}
						break;

					case 'F':
					case 'J':
					case 'L':
					case 'M':
					case 'N':
					case 'R':
						stringbuffer1.append(c);
						i++;
						break;

					case 'K':
						if (k > 0) {
							if (stringbuffer.charAt(k - 1) != 'C')
								stringbuffer1.append(c);
						} else {
							stringbuffer1.append(c);
						}
						i++;
						break;

					case 'P':
						if (k + 1 < j && stringbuffer.charAt(k + 1) == 'H')
							stringbuffer1.append('F');
						else
							stringbuffer1.append(c);
						i++;
						break;

					case 'Q':
						stringbuffer1.append('K');
						i++;
						break;

					case 'S':
						String s3 = stringbuffer.toString();
						if (s3.indexOf("SH", k) == k || s3.indexOf("SIO", k) == k ||
    							s3.indexOf("SIA", k) == k)
							stringbuffer1.append('X');
						else
							stringbuffer1.append('S');
						i++;
						break;

					case 'T':
						String s4 = stringbuffer.toString();
						if (s4.indexOf("TIA", k) == k || s4.indexOf("TIO", k) == k) {
							stringbuffer1.append('X');
							i++;
							break;
						}
						if (s4.indexOf("TCH", k) == k)
							break;
						if (s4.indexOf("TH", k) == k)
							stringbuffer1.append('0');
						else
							stringbuffer1.append('T');
						i++;
						break;

					case 'V':
						stringbuffer1.append('F');
						i++;
						break;

					case 'W':
					case 'Y':
						if (k + 1 < j && vowels.indexOf(stringbuffer.charAt(k + 1)) >= 0) {
							stringbuffer1.append(c);
							i++;
						}
						break;

					case 'X':
						stringbuffer1.append('K');
						stringbuffer1.append('S');
						i += 2;
						break;

					case 'Z':
						stringbuffer1.append('S');
						i++;
						break;

				}
				k++;
			}
			if (i > 4)
				stringbuffer1.setLength(4);
		}

		return stringbuffer1.toString();
	}
}