import HelloApp.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
 
public class HelloClient 
{
    public static void main(String args[])
    {
        try{
            // create and initialize the ORB
            ORB orb = ORB.init(args, null);
 
            // get the root naming context
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContext ncRef = NamingContextHelper.narrow(objRef);

            // resolve the Object Reference in Naming
            NameComponent nc = new NameComponent("Hello", "");
            NameComponent path[] = {nc};
            Hello helloRef = HelloHelper.narrow(ncRef.resolve(path));
 
            // call the Hello server object and print results
        
            String oldhello = helloRef.lastMessage();
            System.out.println(oldhello);
            String hello = helloRef.sayHello(args[0]);
            System.out.println(hello);
 
        } catch (Exception e) {
            System.out.println("ERROR : " + e) ;
            e.printStackTrace(System.out);
        }
    }
}