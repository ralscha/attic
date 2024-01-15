
import java.io.*;
import java.util.*;

public class PayCif {

	
	public static void main(String[] args) {

		String line;
		Set cifs = new TreeSet();
		
		try {
			if (args.length == 1) {
	   			File dir = new File(args[0]);	
	   			File[] files = dir.listFiles();
	   
	   			for (int i = 0; i < files.length; i++) {
	   				System.out.println(files[i].getPath());
					BufferedReader br = new BufferedReader(new FileReader(files[i]));
					while((line = br.readLine()) != null) {
						if (line.length() > 500) {
							String acct = line.substring(79,103).trim();
							if (acct.length() == 16) {
								if (!acct.substring(4,6).equals("98")) {
									cifs.add(acct);
								}
							} else {
								System.out.println(acct);
							}
						}
						
					}
					br.close();
	   			}
	   				   			
	   			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cifs.dat")));
	   			Iterator it = cifs.iterator();
	   			while(it.hasNext()) {
	   				pw.println(it.next());
	   			}
	   			pw.close();

			} else {
				System.out.println("java PayCif <dir>");
			}
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
	
	}

}