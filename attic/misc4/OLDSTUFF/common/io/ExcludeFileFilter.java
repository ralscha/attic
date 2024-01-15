package common.io;

import java.io.*;
import java.util.*;

public class ExcludeFileFilter implements FileFilter {
	
	List excludeFileList;
	
	public ExcludeFileFilter() {
		excludeFileList = new ArrayList();
	}
	
	public ExcludeFileFilter(String[] fileNames) {
		this();
		for (int i = 0; i < fileNames.length; i++) {
			addFileName(fileNames[i]);
		}
	}
	
	public void addFileName(String ending) {	
		excludeFileList.add(ending);
	}
	
	public boolean accept(File pathName) {
		if (pathName.isDirectory()) {
			return true;
		} else {		
			String name = pathName.getName();
			Iterator it = excludeFileList.iterator();
			while(it.hasNext()) {
				if (name.endsWith((String)it.next()))
					return false;
			}
			return true;
		}
	}
}