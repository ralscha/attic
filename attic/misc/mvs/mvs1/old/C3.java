
import java.io.*;
import java.util.*;
import common.io.*;

public class C3 {

	private static int[][][] tris = new int[26][26][26];

	
	public static void main(String[] args) {

		String line;
		StringTokenizer st;

		
		try {
			if (args.length == 1) {
				
				File f = new File(args[0]);
				File fileList[] = f.listFiles(new IncludeEndingFileFilter(".txt"));
				
				for (int i = 0; i < fileList.length; i++) {
					System.err.println(fileList[i].getName());
					BufferedReader br = new BufferedReader(new FileReader(fileList[i]));
					while((line = br.readLine()) != null) {

						st = new StringTokenizer(line);
						while(st.hasMoreElements()) {
							splitChars((String)st.nextElement());
						}
					}
					br.close();
					
				}
			}
			
			for (int a = 0; a < 26; a++) {
				for (int b = 0; b < 26; b++) {
					for (int c = 0; c < 26; c++) {
						System.out.println(""+(char)(a+97)+(char)(b+97)+(char)(c+97)+" "+tris[a][b][c]);
					}
				}
			}
			
			
		} catch (Exception e) {
			System.err.println(e);
		}

	}
	
	private static void splitChars(String word) {
		word = removeNonAZ(word.toLowerCase());
		
		for (int i = 0; i < word.length()-2; i++) {
			int c1 = word.charAt(i)-97;
			int c2 = word.charAt(i+1)-97;
			int c3 = word.charAt(i+2)-97;
			tris[c1][c2][c3]++;
				
		}
	}
	
	private static String removeNonAZ(String word) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < word.length(); i++) {
			if (isA2Z(word.charAt(i)))
				sb.append(word.charAt(i));
		}
		return sb.toString();
	}
	
	private static boolean isA2Z(char c) {
		return ((c >= 'a') && (c <= 'z'));
	}
}