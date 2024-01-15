

import java.io.*;
import java.util.*;
import common.io.*;

public class PropertyFinder {


	private FileNode root;
	private List fileList;
	private Properties props;

	private PropertyFinder() {
		
		root = new FileNode(new File("D:\\javaprojects\\ess\\pbroker"));
    try {
    FileInputStream fis = new FileInputStream("D:\\javaprojects\\ess\\pbroker\\web-inf\\classes\\pbroker.properties");
    props = new Properties();
    props.load(fis);
    fis.close();
    } catch (IOException ioe) {
      System.err.println(ioe);
    }
		fileList = new ArrayList();
		
	}
	
	public void collectFiles(FileNode node) {
		collectFiles(node, new FileFilter() {
										public boolean accept(File file) {
											return true;
										}
									});
	}
	
	public void collectFiles(FileNode node, FileFilter filter) {
		FileNode[] children = node.getChildren();
		
		for (int i = 0; i < children.length; i++) {
			if (children[i].isLeaf()) {
				if (filter.accept(children[i].getFile())) {
					fileList.add(children[i].getFile());
				}
			} else {
				collectFiles(children[i], filter);
			}
		}
	}
	
	private void go() {
    try {
		IncludeEndingFileFilter includeFilter = new IncludeEndingFileFilter();
    //ExcludeEndingFileFilter excludeFiler = new ExcludeEndingFileFilter();
    includeFilter.addEnding(".inc");
    includeFilter.addEnding(".java");
    includeFilter.addEnding(".jsp");
		collectFiles(root, includeFilter);
		//collectFiles(root);

    Iterator it = fileList.iterator();
    

    while(it.hasNext()) {
      File f = (File)it.next();

      if (f.getPath().indexOf("\\old\\") != -1)
        continue;
      
      System.out.println(f.getPath());

      List contentList = new ArrayList();
      BufferedReader br = new BufferedReader(new FileReader(f));
      String line = null;
      while((line = br.readLine()) != null) {
        contentList.add(line);
      }
      br.close();

      Set found = new HashSet();
      
      Enumeration e = props.propertyNames();
      while(e.hasMoreElements()) {
        String property = (String)e.nextElement();

        for (int i = 0; i < contentList.size(); i++) {
          line = (String)contentList.get(i);
          if (line.indexOf("\""+property+"\"") != -1) {
            found.add(property);
            break;
          }
        }        
      }

      Iterator it2 = found.iterator();
      while(it2.hasNext()) {
        String p = (String)it2.next();
        props.remove(p);
      }
      

    }


    
    Enumeration e = props.propertyNames();
    while(e.hasMoreElements()) {
      System.out.println((String)e.nextElement());
    }
    } catch (IOException e) {
      System.err.println(e);
    }

    try {
    FileOutputStream fos = new FileOutputStream("zuviel.txt");
    props.store(fos, "zuviel");
    fos.close();
    } catch (IOException e) {
      System.err.println(e);
    }
	}

	public static void main(String[] args) {
		new PropertyFinder().go();
	}


}