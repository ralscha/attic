
package ch.sr.pwgen;
import java.util.*;

public class Test {

	public static void main(String args[]) {
		
		List l = PasswordGen.generate(50, 10, PasswordGen.GERMAN, PasswordGen.NORMAL_MODE);
		
		Iterator it = l.iterator();
 		while(it.hasNext()) {
			System.out.println(it.next());
		}
		
		/*
		try {
			old.PasswordGenData pgd = new old.PasswordGenData();
			PrintWriter pw = new PrintWriter(new FileWriter("char_en.dat"));
			for (int a = 0; a < 26; a++) {
				for (int b = 0; b < 26; b++) {
					for (int c = 0; c < 26; c++) {
						pw.print(""+((char)(a+97))+((char)(b+97))+((char)(c+97)));
						pw.print(" ");
						pw.println(pgd.get(a, b, c));
					}
				}
			}
			pw.close();
		} catch (IOException ioe) {
			System.err.println(ioe);
		} 
		*/
	}

}