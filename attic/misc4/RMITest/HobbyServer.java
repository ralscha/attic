package RMITest;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;


public class HobbyServer extends UnicastRemoteObject implements HobbyInterface
{
    private String name ;
    public HobbyServer (String n)  throws java.rmi.RemoteException
    {
        super();
        name=n;
    }

    public String recommendHobby(String n, int age) throws RemoteException
    {
        if (age < 10)
            return (n + ", go play some Nintendo.");
        else if (       age >=10 && age < 20 )
            return (n + ", check out snowboarding, dude!");
        else if (       age >=20 && age < 40 )
        return (n + ", go explore the great outdoors.");
        else
        return (n + ", try chess.");
    }

    public static void main(String[] args)
    {
        System.setSecurityManager(new RMISecurityManager());
        try
        {
            HobbyServer myHobbyServer = new HobbyServer("HobbyServer");
            Naming.rebind("//ralphsch/HobbyServer",myHobbyServer);
            System.out.println("HobbyServer bound in registry");
        }
        catch (Exception e)
        {
            System.out.println("Exception on bind");
        }
    }
}