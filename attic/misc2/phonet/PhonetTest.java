
import java.io.*;
import java.util.*;

public class PhonetTest {
	
	public static void main(String args[]) {	
	
		
		Phonet p = new Phonet();
		/*
		try {
		BufferedReader br = new BufferedReader(new FileReader("names.dat"));
		String line = null;
		while((line = br.readLine()) != null) {		
			
			System.out.println(line);
			System.out.println("1: "+p.phonet(line, 1));
			System.out.println("2: "+p.phonet(line, 2));
			System.out.println();
		}
		br.close();
		} catch (IOException e) {
			System.err.println(e);
		}
    */
		
		
		
		//Phonet p = new Phonet();
		
		try {
		BufferedReader br = new BufferedReader(new FileReader("text.txt"));
		String line = null;
		while((line = br.readLine()) != null) {		
			StringTokenizer st = new StringTokenizer(line);
			while(st.hasMoreTokens()) {
				String token = st.nextToken();
				System.out.println(token);
				System.out.println("1: "+p.phonet(token, 1));
				System.out.println("2: "+p.phonet(token, 2));
				System.out.println();
			}
		}
		br.close();
		} catch (IOException e) {
			System.err.println(e);
		}
    
	}
}
		
		