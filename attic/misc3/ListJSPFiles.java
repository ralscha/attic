
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;
import com.caucho.server.http.*;

public class ListJSPFiles {
  
  List fileList;
  Set ignoreSet;

  public ListJSPFiles() {
    fileList = new ArrayList();

		ignoreSet = new HashSet();
		ignoreSet.add("/dienste/tableindex.jsp");
		ignoreSet.add("/dienste/tableindexsort.jsp");
    ignoreSet.add("/dokumentvorlagen/dveditfeldliste.jsp");
    ignoreSet.add("/include/tableindex.jsp");
    ignoreSet.add("/news/tableindex.jsp");
    ignoreSet.add("/rekrutierung/oalieferantenlist.jsp");
    ignoreSet.add("/rekrutierung/offerten/tableindex.jsp");
    ignoreSet.add("/rekrutierung/tableindex.jsp");
    ignoreSet.add("/rekrutierung/tableindexsort.jsp");
    ignoreSet.add("/rekrutierung/tableindextreffer.jsp");
    ignoreSet.add("/vertrag/tableindex.jsp");
    //ignoreSet.add("");
    //ignoreSet.add("");
  }


  public void readDir(File dir) {
    File[] files = dir.listFiles(new FileFilter() {
      public boolean accept(File d) {
        if ( (d.getName().endsWith(".jsp")) || (d.isDirectory()) )
          return true;
        else
          return false;
      }
        
    });
    
    for (int i = 0; i < files.length; i++) {
      if (files[i].isDirectory()) {
        readDir(files[i]);
      } else {
        fileList.add(files[i]);
      }
      
    }
  }
  		
  public static String replaceSlash(String s) {
    char[] c = s.toCharArray();
    StringBuffer sb = new StringBuffer(s.length());

    for (int i = 0; i < c.length; i++) {
      if (c[i] == '\\') {
        sb.append('/');
      } else {
        sb.append(c[i]);
      }
    }

    return sb.toString();
  }

	public boolean ignore(String s) {
		return ignoreSet.contains(s);
	}

	public static void main(String args[]) {

    if (args.length == 4) {

      ListJSPFiles lf = new ListJSPFiles();
      lf.readDir(new File(args[0]));
    
      Iterator it = lf.fileList.iterator();
      List strList = new ArrayList();

      strList.add("-conf");
      strList.add(args[1]);
      strList.add("-compile-host");
      strList.add("test.pbroker.ch");
      strList.add("-compile");
		


      while (it.hasNext()) {
        String s = ((File)it.next()).getPath();
        s = s.substring(s.indexOf(args[3])+args[3].length());
      
        s = lf.replaceSlash(s);
			  if (!lf.ignore(s)) {
	        strList.add(args[2]+s);
			  }
      }

    
      String[] urls = (String[])strList.toArray(new String[strList.size()]);

		  try {
    	  ResinServer rs = new ResinServer(urls, true);
    	  rs.init(true);
		  } catch (Exception e) {
			  System.err.println(e);
		  }
    } else {
      System.out.println("java ListJSPFiles <dir> <conf> <prefix> <limiter>");
    }

	}
 
}