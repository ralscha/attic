package common.io;

import java.io.*;
import java.util.*;

public class MultiFileFilter implements FileFilter {
	
	List fileFilterList;
	
	public MultiFileFilter() {
		fileFilterList = new ArrayList();
	}
	
	public MultiFileFilter(FileFilter[] filters) {
		this();
		for (int i = 0; i < filters.length; i++) {
			addFilter(filters[i]);
		}
	}
	
	public void addFilter(FileFilter filter) {
		fileFilterList.add(filter);
	}
	
	public boolean accept(File pathName) {
				
		Iterator it = fileFilterList.iterator();
		while(it.hasNext()) {
			if (((FileFilter)it.next()).accept(pathName) == true)
				return true;
		}
		return false;
	}

}