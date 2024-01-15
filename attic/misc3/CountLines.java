import java.io.*;

public class CountLines {


  public static void main(String[] args) {

      int count = 0;    

      try { 
			  BufferedReader br = new BufferedReader(new java.io.FileReader("d.txt"));
			  String line;
			  while ((line = br.readLine()) != null) {
          System.out.println(line);
          try {
			      BufferedReader br2 = new BufferedReader(new java.io.FileReader(line));
            String l;
            while((l = br2.readLine()) != null) {
              if (!l.trim().equals("")) {
                count++;
              }
            }
            br2.close();
          } catch (FileNotFoundException f) {
            System.out.println(f);
          }
			  }
			  br.close();  
      } catch (Exception e) {
        System.err.println(e);
      }
      System.out.println("total: " + count);
  
  }
}