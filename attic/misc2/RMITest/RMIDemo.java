import RMITest.HobbyInterface;
import java.rmi.*;

public class RMIDemo
{
    public static void main(String[] args)
    {
        try
        {
            HobbyInterface  myHobby = (HobbyInterface)Naming.lookup("//localhost/HobbyServer");
            // localhost is used in lookup() since for this demo, we can have
            // the server run on the same machine as the client. Change
            // the hostname to that of the remote host, if you intend on having
            // the server run in a truly distributed mode.
            String advice = myHobby.recommendHobby("Jim Jones", 27);
            System.out.println(advice);
         }
         catch (Exception e) {System.out.println(e);}
    }
}