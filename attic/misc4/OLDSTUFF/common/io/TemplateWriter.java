
package common.io;

import java.io.*;
import java.util.*;

public class TemplateWriter {
	private Map variablesMap = new HashMap();
	private List filesList = new ArrayList();
		
	
	public TemplateWriter() {
	}
	
	public TemplateWriter(String fileName)  throws FileNotFoundException {
		addFile(fileName);
	}
	
	public TemplateWriter(File file)  throws FileNotFoundException {
		addFile(file);
	}

	public void addFile(String fileName)  throws FileNotFoundException {
		addFile(new File(fileName));
	}
	
	public void addFile(File file) throws FileNotFoundException {
		if (file.exists())
			filesList.add(file);
		else
			throw new FileNotFoundException("File " + file + " not found");
	}


	public void addVariable(String varName, String value) {
		StringBuffer sb = new StringBuffer();
		sb.append("%%");
		sb.append(varName);
		sb.append("%%");
		variablesMap.put(sb.toString(), value);
	}
	
	public void addVariable(String varName, int value) {
		addVariable(varName, String.valueOf(value));
	}
	
	public void addVariable(String varName, Integer value) {
		addVariable(varName, value.toString());
	}

	public void write(String outputFileName) throws IOException {
	  	PrintWriter pr = new PrintWriter(new BufferedWriter(new FileWriter(outputFileName)));
   	write(pr);
   	pr.close();
	}

	public void write(PrintWriter writer) throws IOException {
		String line;
		StringBuffer sb = new StringBuffer();
		int pos;
		
		Iterator it = filesList.iterator();
		while(it.hasNext()) {
			File file = (File)it.next();
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			while((line = br.readLine()) != null) {				
				if (line.indexOf("%%") != -1) {
					Iterator varIt = variablesMap.keySet().iterator();
					while(varIt.hasNext()) {
						String key = (String)varIt.next();
						while((pos = line.indexOf(key)) != -1) {
							sb.setLength(0);
							sb.append(line.substring(0, pos));
							sb.append(variablesMap.get(key));
							sb.append(line.substring(pos+key.length()));
							line = sb.toString();
						}
					}
				}
				writer.println(line);
			}
		}
	}		
    
}