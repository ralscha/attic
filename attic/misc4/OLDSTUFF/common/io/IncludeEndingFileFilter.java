package common.io;

import java.io.*;
import java.util.*;

public class IncludeEndingFileFilter implements FileFilter {
	
	List endingsList;
	
	public IncludeEndingFileFilter() {
		endingsList = new ArrayList();
	}
	
	public IncludeEndingFileFilter(String[] endings) {
		this();
		for (int i = 0; i < endings.length; i++) {
			addEnding(endings[i]);
		}
	}
	
	public void addEnding(String ending) {
		if (ending.startsWith(".")) {
			endingsList.add(ending);
		} else {
			throw new IllegalArgumentException("ending must start with a '.'");
		}
	}
	
	public boolean accept(File pathName) {
		if (pathName.isDirectory()) {
			return true;
		} else {		
			String name = pathName.getName();
			Iterator it = endingsList.iterator();
			while(it.hasNext()) {
				if (name.endsWith((String)it.next()))
					return true;
			}
			return false;
		}
	}
}