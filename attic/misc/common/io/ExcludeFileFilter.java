package common.io;

import java.io.*;
import java.util.*;

public class ExcludeFileFilter implements FileFilter {
	
	Set excludeFileSet;
	
	public ExcludeFileFilter() {
		excludeFileSet = new HashSet();
	}
	
	public ExcludeFileFilter(String[] fileNames) {
		this();
		for (int i = 0; i < fileNames.length; i++) {
			addFileName(fileNames[i]);
		}
	}
	
	public void addFileName(String filename) {	
		excludeFileSet.add(filename.toLowerCase());
	}
	
	public boolean accept(File pathName) {
		if (pathName.isDirectory()) {
			return true;
		} else {		
			String name = pathName.getName().toLowerCase();
			
			if (excludeFileSet.contains(name))
				return false;
			else
				return true;

		}
	}
}