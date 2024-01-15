package credoc;

import java.util.*;
import java.io.*;

public class Branches {
	private final static Branches instance = new Branches();
	private Map branches;

	protected Branches() {
		branches = new HashMap();
	
		String line;
	
		try {
			BufferedReader br = new BufferedReader(new FileReader("Branches.dat"));
			while ((line = br.readLine()) != null) {
				String ibbb = line.substring(0,4);
				String rz   = line.substring(4,5);
				Integer rzint = Integer.valueOf(rz);
				branches.put(ibbb, rzint);
			}
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public static Branches getInstance() {
		return instance;
	}
	
	public int getRZ(String ibbb) {
		return ((Integer)branches.get(ibbb)).intValue();	
	}
}