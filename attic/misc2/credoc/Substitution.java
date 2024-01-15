package credoc;

import java.util.*;
import java.io.*;
import ViolinStrings.*;

public class Substitution {
	private final static Substitution instance  = new Substitution();
	private Map substMap;
	
	public Substitution() {
		substMap = new HashMap();
	
		String line;
		
		try {
			long stammnr;
			BufferedReader br = new BufferedReader(new FileReader("subst.dat"));
			while ((line = br.readLine()) != null) {
				stammnr = Long.parseLong(line.substring(0,20).trim());
				substMap.put(String.valueOf(stammnr), line.substring(20).trim());
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public void save() {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter("subst.dat"));
			Iterator it = substMap.keySet().iterator();
			StringBuffer sb = new StringBuffer();
			String key;
			while(it.hasNext()) {
				sb.setLength(0);
				key = (String)it.next();
				sb.append(Strings.leftJustify(key,20));
				sb.append((String)substMap.get(key));
				pw.println(sb.toString());
			}
			pw.close();
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
	}
	
	public static Substitution getInstance() {
		return instance;
	}
	
	public String getSubst(String cif) {	
		return (String)substMap.get(cif);		
	}
	
	public static void main(String[] args) {
		Substitution.getInstance().save();
	}
}