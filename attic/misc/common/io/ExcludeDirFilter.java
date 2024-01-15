package common.io;

import java.io.*;
import java.util.*;

public class ExcludeDirFilter implements FileFilter {
	
	List excludeDirList;
	
	public ExcludeDirFilter() {
		excludeDirList = new ArrayList();
	}
	
	public ExcludeDirFilter(String[] dirs) {
		this();
		for (int i = 0; i < dirs.length; i++) {
			addDir(dirs[i]);
		}
	}
	
	public void addDir(String dir) {	
		excludeDirList.add(dir.toLowerCase());
	}
	
	public boolean accept(File pathName) {
		if (pathName.isDirectory()) {
		
			String absolute = pathName.getAbsolutePath().toLowerCase();
			
			Iterator it = excludeDirList.iterator();
			while(it.hasNext()) {
				if (absolute.startsWith((String)it.next()))
					return false;
			}
			return true;
		} else {
			return true;
		}
	}
}