
import java.io.*;
import com.tce.math.TBigDecimal;


public class PaymentTotal {

	
	public static void main(String[] args) {

		String line;
		int count = 0;
		TBigDecimal total = TBigDecimal.ZERO;
		
		try {
			if (args.length == 2) {
				String sc = args[1];
	   			File dir = new File(args[0]);	
	   			File[] files = dir.listFiles();
	   
	   			for (int i = 0; i < files.length; i++) {
					BufferedReader br = new BufferedReader(new FileReader(files[i]));
					while((line = br.readLine()) != null) {
						if (line.length() > 500) {
							String type = line.substring(37,38);							
							String amount = line.substring(60,78).trim();						
							String branch = line.substring(198,202);
							String acct = line.substring(83,85);
							
							if (branch.equals(sc)) {
								try {
									if (acct.equals("98")) {
										if (type.equals("D"))
											total = total.subtract(new TBigDecimal(amount));
										else
											total = total.add(new TBigDecimal(amount));
										count++;
									}
								} catch (NumberFormatException nfe) { 
									System.out.println(nfe);
								}
							}
						}
						
					}
					br.close();
	   			}
	   
	   			System.out.println("Count   = " + count);
	   			System.out.println("Total   = " + total);
	   			
			} else {
				System.out.println("java PaymentTotal <dir> <serviceCenter>");
			}
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
	
	}

}