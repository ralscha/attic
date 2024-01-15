
import java.io.*;
import gnu.trove.*;
import java.util.*;

public class TimeTool {

  
  public static void main(String[] args) {
    try {
      File datFile = new File("D:/download/time_prod.log");
      
      BufferedReader br = new BufferedReader(new FileReader(datFile));
      
      THashMap hashMap = new THashMap();
      
      String line;
      while ((line = br.readLine()) != null) {
        int pos1 = line.indexOf(";/");
        int i = line.lastIndexOf(';');
        if (pos1 != -1) {
          String timeStr = line.substring(i+1);
          long time = Long.parseLong(timeStr);
          String url = line.substring(pos1+1, i);
          
          TLongArrayList list = (TLongArrayList)hashMap.get(url);
          if (list == null) {
            list = new TLongArrayList();
            hashMap.put(url, list);
          }
          
          list.add(time);
          
        }
      }
      
      br.close();
      System.out.println(hashMap.size());
      
      File output = new File("D:/download/time_prod_out.log");
      PrintWriter pw = new PrintWriter(new FileWriter(output));
      Iterator it = hashMap.keySet().iterator();
      while(it.hasNext()) {
        String url = (String)it.next();
        TLongArrayList list = (TLongArrayList)hashMap.get(url);
        pw.print(url);
        pw.print(";");
        pw.print(list.size());
        pw.print(";");
        
        long total = 0;
        long max = 0; 
        long min = Long.MAX_VALUE;
        
        for (int i = 0; i < list.size(); i++) {
          long t = list.get(i);
          total += t;
          
          if (t > max) {
            max = t;
          }
          
          if (t < min) {
            min = t;
          }
        }
        pw.print(min);
        pw.print(";");
        pw.print(max);
        pw.print(";");
        pw.println(total);
        
      }
      
      
      pw.close();
      
    } catch (IOException ioe) {
      System.err.println(ioe);
    }
  }
}