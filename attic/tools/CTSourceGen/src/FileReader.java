import java.io.*;
import java.util.*;
import java.util.regex.*;

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
                      
                      if (file.getName().endsWith(".java") ||
                         (file.getName().endsWith(".jsp"))) {                         
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
    
    Set inputSet = new HashSet();
    
    Pattern pattern = Pattern.compile("\"([^\"]+)\"");
    Pattern pattern2 = Pattern.compile("'([^']+)'");
    
		collectFiles(new File("d:/eclipse/workspace1.4/ct"));			
    
    Iterator it = fileList.iterator();
    while(it.hasNext()) {
      File f = (File)it.next();
      String line = null;
      BufferedReader br = new BufferedReader(new java.io.FileReader(f));

      while ((line = br.readLine()) != null) {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
          inputSet.add(matcher.group(1));
        }
        
        matcher = pattern2.matcher(line);
        while (matcher.find()) {
          inputSet.add(matcher.group(1));
        }
        
      }
      br.close();
    }
    	
    File propsFile = new File("D:/eclipse/workspace1.4/ct/www/WEB-INF/classes/ct.properties");
    FileInputStream is = new FileInputStream(propsFile);
    Properties props = new Properties();
    props.load(is);
    

    Set propsSet = new HashSet();
    propsSet.addAll(props.keySet());
    
    it = propsSet.iterator();
    while (it.hasNext()) {
      String key = (String)it.next();
      if (!inputSet.contains(key)) {
        props.remove(key);
      }
    }
    
    
    FileOutputStream fos = new FileOutputStream("c:/temp/new.txt");
    props.store(fos, "new");
    fos.close();
     
      
	}

	public static void main(String[] args) {
    


		try {
      new FileReader().go();
    } catch (IOException e) {
      e.printStackTrace();
    }
	}
}