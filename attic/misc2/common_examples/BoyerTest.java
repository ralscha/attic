
import common.util.*;
import java.io.*;

public class BoyerTest {
   
	public static void main(String[] args) {

		System.out.println(Boyer.indexOf("dogcatwombat","cat"));
		System.out.println("dogcatwombat".indexOf("cat"));
		System.out.println(Boyer.indexOf("crtcamccmcarogcatwombat","cat"));
		System.out.println("crtcamccmcarogcatwombat".indexOf("cat"));
		System.out.println(Boyer.indexOf("dogcatwombat",""));
		System.out.println("dogcatwombat".indexOf(""));
		System.out.println(Boyer.indexOf("",""));
		System.out.println("".indexOf(""));
		System.out.println(Boyer.indexOf("","abcde"));
		System.out.println("".indexOf("abcde"));
		System.out.println(Boyer.indexOf("dogcatwombat","cow"));
		System.out.println("dogcatwombat".indexOf("cow"));

		try {

			// O P E N
			// Any file of sample characters
			File f = new File ("temp.txt");
			int size = (int) f.length();
			FileReader fr;
			fr = new FileReader(f);

			// R E A D
			char[] ca = new char[size];
			int charsRead = fr.read(ca);
			String s = new String(ca);

			long start;
			long stop;
			int result = 0;


			start = System.currentTimeMillis();
			for (int i = 0; i < 1000; i++) {
				// Need to make different so optimiser will actually do
				// the work repeatedly.
				result = Boyer.indexOf(ca, "efficiency"+i % 10);
			}
			System.out.println("Boyer char[]" + result);
			stop = System.currentTimeMillis();
			System.out.println("Elapsed:" + (stop - start));


			// benchmark Boyer.indexOf
			start = System.currentTimeMillis();
			for (int i = 0; i < 1000; i++) {
				// Need to make different so optimiser will actually do
				// the work repeatedly.
				result = Boyer.indexOf(s, "efficiency"+i % 10);
			}
			System.out.println("Boyer String" + result);
			stop = System.currentTimeMillis();
			System.out.println("Elapsed:" + (stop - start));

			// Benchmark String.IndexOf
			start = System.currentTimeMillis();
			for (int i = 0; i < 1000; i++) {
				result = s.indexOf("efficiency"+i % 10);
			}
			System.out.println("String " + result);
			stop = System.currentTimeMillis();
			System.out.println("Elapsed:" + (stop - start));

			// C L O S E
			fr.close();

		} catch (IOException e) {
			System.out.println(e);
			return;
		}

	} // end main
}