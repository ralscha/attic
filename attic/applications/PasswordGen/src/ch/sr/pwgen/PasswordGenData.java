package ch.sr.pwgen;
import java.io.*;

class PasswordGenData {

	private int tris[][][] = null;
	private long sigma = 0;

	PasswordGenData(String language) {
		tris = new int[26][26][26];
		
		String fileName = "char_de.dat";
		if (language.equals(PasswordGen.ENGLISH))
			fileName = "char_en.dat";
			
		String line;	
		InputStream is = getClass().getResourceAsStream("/"+fileName);
		
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));

			while ((line = buf.readLine()) != null) {
				int a = line.charAt(0)-97;
				int b = line.charAt(1)-97;
				int c = line.charAt(2)-97;
				int total = Integer.parseInt(line.substring(4));
				tris[a][b][c] = total;				
			}

			for (int c1 = 0; c1 < 26; c1++) {
				for (int c2 = 0; c2 < 26; c2++) {
					for (int c3 = 0; c3 < 26; c3++) {
						sigma += (long) tris[c1][c2][c3];
					}
				}
			}
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
	}

	void set(int x1, int x2, int x3, short v) {
		tris[x1][x2][x3] = v;
	}

	long get(int x1, int x2, int x3) {
		return (long)tris[x1][x2][x3];
	}

	long getSigma() {
		return sigma;
	}

}
