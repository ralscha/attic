import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl
        extends UnicastRemoteObject
        implements Hello
{
        private String name;

        public HelloImpl(String s) throws RemoteException {
                super();
                name = s;
        }

        public String sayHello() throws RemoteException {
                return  "Hello World!";
        }

        public static void main(String args[])
        {
                // Create and install a security manager
                System.setSecurityManager(new RMISecurityManager());

                try {
                        HelloImpl obj = new HelloImpl("HelloServer");
                        Naming.rebind("rmi://ralphsch/HelloServer", obj);
                        System.out.println("HelloServer bound in registry");
                } catch (Exception e) {
                        System.out.println("HelloImpl err: " + e.getMessage());
                        e.printStackTrace();
                }
        }
}