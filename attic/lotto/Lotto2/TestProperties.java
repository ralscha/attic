import lotto.html.*;
import lotto.util.*;    	
import java.util.*;
    	
public class TestProperties {    		
    		
    public TestProperties() {
        System.out.println(AppProperties.getInstance().getProperty("database"));
        
        Vector v = AppProperties.getInstance().getFtpConnections();
        for (int i = 0; i < v.size(); i++) {
            FtpConnection ftpc = (FtpConnection)v.elementAt(i);
            System.out.println(ftpc.getHostname());
            System.out.println(ftpc.getPassword());
            System.out.println(ftpc.getUsername());
            System.out.println(ftpc.getHomedir());
        }                
        
    }
    		
    public static void main(String args[]) {
        new TestProperties();
    }
}