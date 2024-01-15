package common.io;

import java.io.*;
import java.util.*;

public class ExcludeEndingFileFilter implements FileFilter {
	
	Set endingsSet;
	
	public ExcludeEndingFileFilter() {
		endingsSet = new HashSet();
	}
	
	public ExcludeEndingFileFilter(String[] endings) {
		this();
		for (int i = 0; i < endings.length; i++) {
			addEnding(endings[i]);
		}
	}
	
	public void addEnding(String ending) {
		if (ending.startsWith(".")) {
			endingsSet.add(ending.toLowerCase());
		} else {
			throw new IllegalArgumentException("ending must start with a '.'");
		}
	}
	
	public boolean accept(File pathName) {
		if (pathName.isDirectory()) {
			return true;
		} else {		
			String name = pathName.getName().toLowerCase();
			int p = name.lastIndexOf('.');
			if (p != -1)
				name = name.substring(p);	
			else
				return true;
			
			if (endingsSet.contains(name))
				return false;
			else
				return true;
		}
	}
}