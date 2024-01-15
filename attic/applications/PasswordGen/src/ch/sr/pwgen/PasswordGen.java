package ch.sr.pwgen;
import java.util.*;

public class PasswordGen {

	public static final String ENGLISH = "ENGLISH";
	public static final String GERMAN = "GERMAN";

	private static final PasswordGenData data_de = new PasswordGenData("GERMAN");
	private static final PasswordGenData data_en = new PasswordGenData("ENGLISH");

	public static final String NORMAL_MODE = "NORMAL";
	public static final String NUMBER_MODE = "NUMBERS";
	public static final String SPECIAL_MODE = "SPECIALS";
	public static final String MIX_MODE = "MIX";
	
	private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
	private static final String numbers = "0123456789";
	private static final String specials = "+*%&/()-.,;:$!?";
	private static final Random rand = new Random();

	private static void completePassword(StringBuffer password, String mode) {
		if (mode.equals(NORMAL_MODE))
			return;

		if (mode.equals(NUMBER_MODE)) {
			char c = numbers.charAt(rand.nextInt(numbers.length()));
			password.insert(rand.nextInt(password.length()), c);
		} else if (mode.equals(SPECIAL_MODE)) {
			char c1 = specials.charAt(rand.nextInt(specials.length()));
			password.insert(rand.nextInt(password.length()), c1);
		} else if (mode.equals(MIX_MODE)) {
			char c2 = numbers.charAt(rand.nextInt(numbers.length()));
			char c3 = specials.charAt(rand.nextInt(specials.length()));
			password.insert(rand.nextInt(password.length()), c2);
			password.insert(rand.nextInt(password.length()), c3);
		}
	}

	public static List generate(int nopw, int len, String language) {
		return generate(nopw, len, language, NORMAL_MODE);
	}

	public static List generate(int nopw, int pwlen, String language, String mode) {
	
		StringBuffer password = new StringBuffer(pwlen);
		ArrayList pwList = new ArrayList();
		int c1, c2, c3;
	
		PasswordGenData data = data_de;
		if (language.equals(ENGLISH))
			data = data_en;
		
		if (mode.equals(NUMBER_MODE) || mode.equals(SPECIAL_MODE))
			pwlen--;
		else if (mode.equals(MIX_MODE))
			pwlen -= 2;
			

		// Pick a random starting point.
		for (int i = 0; i < nopw; i++) {
			password.setLength(0);
			double pik = rand.nextDouble(); // random number [0,1]
			long ranno = (long)(pik * data.getSigma()); // weight by sum of frequencies
			long sum = 0;
			for (c1 = 0; c1 < 26; c1++) {
				for (c2 = 0; c2 < 26; c2++) {
					for (c3 = 0; c3 < 26; c3++) {
						sum += data.get(c1, c2, c3);
						if (sum > ranno) {
							password.append(alphabet.charAt(c1));
							password.append(alphabet.charAt(c2));
							password.append(alphabet.charAt(c3));
							c1 = 26;
							c2 = 26;
							c3 = 26;
						}
					}
				}
			}	
			
			// Now do a random walk.
			int nchar = 3;
			while (nchar < pwlen) {
				c1 = alphabet.indexOf(password.charAt(nchar - 2));
				c2 = alphabet.indexOf(password.charAt(nchar - 1));
				sum = 0;
				for (c3 = 0; c3 < 26; c3++)
					sum += data.get(c1, c2, c3);
				if (sum == 0) {
					break;
				}
				pik = rand.nextDouble();
				ranno = (long)(pik * sum);
				sum = 0;
				for (c3 = 0; c3 < 26; c3++) {
					sum += data.get(c1, c2, c3);
					if (sum > ranno) {
						password.append(alphabet.charAt(c3));
						c3 = 26;
					}
				}
				nchar++;
			}

			if (!mode.equals(NORMAL_MODE))
				completePassword(password, mode);
			
			pwList.add(password.toString());
		}

		return pwList;
	}

}
