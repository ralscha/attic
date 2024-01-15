package test;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileReader {

	private List fileList;
	
	private FileReader() {		
		fileList = new ArrayList();		
	}
	
	public void collectFiles(File dir) {
		collectFiles(dir, new FileFilter() {
										public boolean accept(File file) {
                      if (file.isDirectory()) { 
                        return true;
                      }
                      
                      if (file.getName().endsWith(".tif")) {                         
											  return true;
                      }
                      
                      return false;
										}
									});
	}
	
	public void collectFiles(File dir, FileFilter filter) {
		    
    File[] files = dir.listFiles(filter);
    
    if (files != null) {
      for (int i = 0; i < files.length; i++) {
        if (files[i].isFile()) {
          fileList.add(files[i]);
        } else {
          collectFiles(files[i], filter);
        }
      }      
		}
	}
	
	private void go() throws IOException {
    
 	  collectFiles(new File("c:/temp"));			
    
    Iterator it = fileList.iterator();
    while(it.hasNext()) {
      File f = (File)it.next();
      
      System.out.println(f.getAbsoluteFile());
      String fileName = f.getName();
      String nr = fileName.substring(0, fileName.length()-4);
      System.out.println(nr);
    }
      
	}

	public static void main(String[] args) {
		try {
      new FileReader().go();
    } catch (IOException e) {
      e.printStackTrace();
    }
	}
}