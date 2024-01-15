
import java.io.*;
import java.util.*;
import gnu.regexp.*;

public class BrowsCapFileReader {

	private final static String RESYNTAX = ".()\\$^";
	private final static String BROWSCAP_FILE = "browscap.ini";
	private List capabilitiesList;

	public BrowsCapFileReader() throws IOException, REException {
		Map capabilitiesMap = new HashMap();
		
		RE regexp = new RE("\\[([^\\]]*)\\]");
		RE propertyregexp = new RE("(.*)=(.*)");
		
		Capability currentCap = null;
		CapabilityAccess capabilityAccess = new CapabilityAccess();
		
		capabilitiesList = new ArrayList();
		
		InputStream is = getClass().getResourceAsStream(BROWSCAP_FILE);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		while((line = br.readLine()) != null) {
			line = line.trim();
			if ( !line.startsWith(";") && (line.length() != 0)) {
				REMatch match = regexp.getMatch(line);				
				if (match != null) {
				
					// Die vorherige Capabilities abspeichern					
					if (currentCap != null) {
						capabilitiesMap.put(currentCap.getREUseragent(), currentCap);
						capabilitiesList.add(currentCap);
					}
							
					String useragent = createREExpression(match.toString(1));
					currentCap = new Capability(useragent);				
				} else if ((match = propertyregexp.getMatch(line)) != null) {
					String key = match.toString(1);
					String value = match.toString(2);
					
					if (!key.equalsIgnoreCase("parent"))
						capabilityAccess.setValue(currentCap, key, value);	
					else 
						capabilityAccess.setValue(currentCap, key, createREExpression(value));							
				}
									
			}
		}
				
		br.close();
		
		// Letzte Capability abspeichern
		if (currentCap != null) {
			capabilitiesMap.put(currentCap.getREUseragent(), currentCap);
			capabilitiesList.add(currentCap);
		}
		
		// Parents setzten
		Iterator it = capabilitiesMap.values().iterator();
		while(it.hasNext()) {
			Capability cap = (Capability)it.next();
			if (cap.getParent() != null) {
				Capability parent = (Capability)capabilitiesMap.get(cap.getParent());	
				if (parent != null)
					cap.setParent(parent);
				else
					System.err.println("not found : " + 	cap.getParent());
			} 
		}
		
		
	}
	
	
	public List getCapabilities() {
		return capabilitiesList;
	}
		
	private String createREExpression(String str) {
		char c;
		StringBuffer sb = new StringBuffer();
		//sb.append("^");
		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			if (RESYNTAX.indexOf(c) != -1) {
				sb.append("\\").append(c);
			} else if (c == '*') {
				sb.append(".*");
			} else {
				sb.append(c);
			}
		}
		//sb.append("$");
		return sb.toString();
	}
	
	
}