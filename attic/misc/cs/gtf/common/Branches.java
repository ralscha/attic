package gtf.common;

import java.io.*;
import java.util.*;

public class Branches {
	private static Branches instance = new Branches();
	private Map branchesMap;

	private Branches() {
		branchesMap = new HashMap();
		String line;
		InputStream is = getClass().getResourceAsStream("branches.dat");
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));

			while ((line = buf.readLine()) != null) {
				try {
					String ibbb = line.substring(0, 4);
					Integer rz = new Integer(line.substring(5,6));
					String name = line.substring(6);
					Branch b = new Branch(ibbb, rz.intValue(), name);
					branchesMap.put(ibbb, b);
				} catch (NumberFormatException nfe) {}
			} 
		} catch (FileNotFoundException fnfe) {
			System.err.println(fnfe);
		} catch (IOException ioe) {
			System.err.println(ioe);
		} 
	}

	public static int getRZ(String ibbb) {
		Branch b = (Branch)instance.branchesMap.get(ibbb);
		if (b != null)  
			return b.getRZ();
		else
			throw new IllegalArgumentException("Branch " + ibbb + " not found");
	} 
	
	public static String getName(String ibbb) {
		Branch b = (Branch)instance.branchesMap.get(ibbb);
		if (b != null)  
			return b.getName();
		else
			throw new IllegalArgumentException("Branch " + ibbb + " not found");		
	}


	private class Branch {
		private String ibbb;
		private int rz;
		private String name;
		Branch(String ibbb, int rz, String name) {
			this.ibbb = ibbb;
			this.rz = rz;
			this.name = name;
		}
		
		String getIBBB() {
			return ibbb;
		}
		
		int getRZ() {
			return rz;
		}
		
		String getName() {
			return name;
		}
	}

	public static void main(String args[]) {
		System.out.println("0835 = " + getRZ("0835")+getName("0835"));
		System.out.println("0456 = " + getRZ("0456")+getName("0456"));
		System.out.println("0208 = " + getRZ("0208")+getName("0208"));
		System.out.println("9999 = " + getRZ("9999")+getName("9999"));
	}
}