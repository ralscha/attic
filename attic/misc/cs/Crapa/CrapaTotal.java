
import java.io.*;
import com.tce.math.TBigDecimal;


public class CrapaTotal {

	
	public static void main(String[] args) {

		final String sc = args[1];
		final String month = args[2];
		String line;
		int count = 0;
		TBigDecimal total = TBigDecimal.ZERO;
		
		try {
			if (args.length == 3) {
	   			File dir = new File(args[0]);	
	   			File[] files = dir.listFiles(new FilenameFilter() {
	   								public boolean accept(File dir, String name) {
	   									if (name.startsWith("c"+sc)) {
	   										if (name.indexOf("dat.1999"+month) != -1) {
	   											System.out.println(name);
	   											return true;
	   										}
	   									}
	   									return false;
	   								}
	   						});
	   
	   			for (int i = 0; i < files.length; i++) {
					BufferedReader br = new BufferedReader(new FileReader(files[i]));
					while((line = br.readLine()) != null) {
						if (line.length() > 200) {
							String amount = line.substring(182,200).trim();
							try {
								total = total.add(new TBigDecimal(amount));
								count++;
							} catch (NumberFormatException nfe) { }
						}
						
					}
					br.close();
	   			}
	   
	   			System.out.println("Count   = " + count);
	   			System.out.println("Total   = " + total);
	   			
			} else {
				System.out.println("java CrapaTotal <dir> <serviceCenter> <month>");
			}
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
	
	}

}