import HelloApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.io.*;
 
class HelloServant extends _HelloImplBase
{
    public String sayHello(String msg)
        throws HelloApp.HelloPackage.cantWriteFile
    {
        try {
                synchronized(this) {
                   File helloFile = new File("helloStorage.txt");
                   FileOutputStream fos = new FileOutputStream(helloFile);
                   byte[] buf = new byte[msg.length()];
                   msg.getBytes(0, msg.length(), buf, 0);
                   fos.write(buf);
                   fos.close();
                }
        } catch(Exception e) {
                throw new HelloApp.HelloPackage.cantWriteFile();
        }
        return "\nHello world !!\n";
    }
       
    public String lastMessage()
        throws HelloApp.HelloPackage.cantReadFile
    {
        try {
                synchronized(this) {
                   File helloFile = new File("helloStorage.txt");
                   FileInputStream fis = new FileInputStream(helloFile);
                   byte[] buf = new byte[1000];
                   int n = fis.read(buf);
                   String lastmsg = new String(buf);
                   fis.close();
                   return lastmsg;
                }
        } catch(Exception e) {
                throw new HelloApp.HelloPackage.cantReadFile();
        }
    }
}

 
public class HelloServer {
 
    public static void main(String args[])
    {
        try{
            // create and initialize the ORB
            ORB orb = ORB.init(args, null);
 
            // create servant and register it with the ORB
            HelloServant helloRef = new HelloServant();
            orb.connect(helloRef);
 
            // get the root naming context
            org.omg.CORBA.Object objRef = 
                orb.resolve_initial_references("NameService");
            NamingContext ncRef = NamingContextHelper.narrow(objRef);
 
            // bind the Object Reference in Naming
            NameComponent nc = new NameComponent("Hello", "");
            NameComponent path[] = {nc};
            ncRef.rebind(path, helloRef);
 
            // wait for invocations from clients
            java.lang.Object sync = new java.lang.Object();
            synchronized (sync) {
                sync.wait();
            }
 
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }
    }
}