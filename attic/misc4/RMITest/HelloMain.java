
import java.rmi.*;

public class HelloMain
{
    public static void main(String args[])
    {
        String message = "";
        try
        {
            Hello obj = (Hello)Naming.lookup("rmi://ralphsch/HelloServer");
            message = obj.sayHello();
            System.out.println("Message vom Server = "+message);
        }
        catch (Exception e)
        {
            System.out.println("HelloMain exception: " +  e.getMessage());
                        e.printStackTrace();
        }
    }
}