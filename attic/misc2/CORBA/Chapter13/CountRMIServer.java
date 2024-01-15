import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;
import java.rmi.server.*;

public class CountRMIServer {
    public static void main(String args[]) {
        System.setSecurityManager(new RMISecurityManager());
        try {
            CountRMIImpl myCount = new CountRMIImpl("my CountRMI");
            System.out.println("CountRMI Server ready");
            
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        
    }

}