import java.io.*;

public class Includer {
    
    public static void includeFile(OutputStream out,String directory,String fileName) throws IOException {

    	String line;
    	
    	if (directory==null)
	        directory=".";
    
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
    
	    File file = new File(directory,fileName);    	    	
        BufferedReader dis = new BufferedReader(new FileReader(file));     
        while ((line = dis.readLine()) != null) {
            pw.println(line);    
    	}
    	pw.close();    	
    }
}