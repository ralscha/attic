package gtf.crapa;

import java.io.*;
import java.util.*;

public class CrapaFileFilter implements FileFilter {
	
	//0123456789012345
	//crapa_199802.txt
	
	public boolean accept(File pathName) {
		if (pathName.isDirectory()) {
			return false;
		} else {		
			String name = pathName.getName();
			if (name.startsWith("crapa_")) {
				if (name.endsWith(".txt")) {
					return true;
				}	
			} 
			return false;
		}
	}
}