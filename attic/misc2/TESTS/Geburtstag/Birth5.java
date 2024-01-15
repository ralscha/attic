import java.io.*;
import java.util.*;

public class Birth5 {

    public static void main(String args[]) {  
        try {
           
           Properties defaultProps = new Properties();
           FileInputStream in = new FileInputStream("defaultProperties");
           defaultProps.load(in);
           in.close();

           // create program properties with default
           Properties applicationProps = new Properties(defaultProps);
           applicationProps.list(System.out);
           // now load properties from last invocation
           in = new FileInputStream("appProperties");
           applicationProps.load(in);
           applicationProps.list(System.out);
           in.close();
           
           FileOutputStream out = new FileOutputStream("appProperties");
           applicationProps.put("WEB", false);
           applicationProps.save(out, "---No Comment---");
           out.close();
 
        } catch (FileNotFoundException f) {
            System.out.println(f);
        } catch (IOException io) {
            System.out.println(io);
        }
    }   
}



 
 