package gtf.tools;

import java.io.*;
import java.util.*;

public class SCAccounts {
	private static SCAccounts instance = new SCAccounts();
	private Set accountsSet;

	private SCAccounts() {
		accountsSet = new HashSet();
		String line;
		InputStream is = getClass().getResourceAsStream("scaccounts.dat");
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));

			while ((line = buf.readLine()) != null) {
				String acct = line.substring(15);
				accountsSet.add(acct);
			} 
		} catch (FileNotFoundException fnfe) {
			System.err.println(fnfe);
		} catch (IOException ioe) {
			System.err.println(ioe);
		} 
	}

	public static boolean isSC(String acct) {
		return (instance.accountsSet.contains(acct));
	} 
	
	public static void main(String args[]) {
		System.out.println(SCAccounts.isSC("0060033705593000"));
		System.out.println(SCAccounts.isSC("0990033705593000"));
	}
}