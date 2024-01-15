
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServer {
    Hashtable events = new Hashtable();
    Vector clients;
  
    public ChatServerImpl() throws RemoteException {
        clients = new Vector();
    }
  
    public boolean login(String str) throws RemoteException {
        if (clients.indexOf(str) > -1)
            return false;
        
        clients.addElement(str);
        fireEvent(str, new ChatServerEvent(ChatServerEvent.LOGIN, null));
        fireEventToAll(new ChatServerEvent(ChatServerEvent.LIST_UPDATE, clients));
    
        return true;
    }
  
    public boolean logout(String str) throws RemoteException {
        events.remove(str);
        
        if (clients.indexOf(str) != -1) {
          clients.removeElementAt(clients.indexOf (str)); 
          fireEvent(str, new ChatServerEvent(ChatServerEvent.LOGOUT, null));
          fireEventToAll(new ChatServerEvent(ChatServerEvent.LIST_UPDATE, clients));
        }
    
        return true;
    }
  
    synchronized void fireEventToAll(ChatServerEvent evt) {
        for (int i=0; i < clients.size(); i++)
            fireEvent("" + clients.elementAt(i), evt);
            
        notifyAll();    
    }
  
    synchronized void fireEvent(String name, ChatServerEvent evt) {
        Vector v = (Vector)events.get(name);
        if (v == null)
            v = new Vector();
      
        v.addElement(evt);
        events.put(name,v);        
    }


    public synchronized ChatServerEvent[] waitForEvent(String str) throws RemoteException {                
        boolean one;
        one = false;
        
        while (!events.containsKey(str) && !one) {
            try {
                System.out.println("WAIT " + str);
                wait(20000);
                one = true;
            } catch (InterruptedException e) { }
        }
                
        if (!events.containsKey(str))
            return null;
    
        Vector v = (Vector)events.get(str);
        ChatServerEvent[] evt = new ChatServerEvent[v.size()];
        v.copyInto(evt);                   
        events.remove(str);      
        return evt;
    }

    public void tell (String name, String m) {
        fireEventToAll(new ChatServerEvent(ChatServerEvent.MESSAGE,name +": " +m));
    }
  
    public static void main (String args[]) {
        System.setSecurityManager(new RMISecurityManager());
    
        try {
            LocateRegistry.createRegistry(2005);            
            ChatServerImpl impl = new ChatServerImpl();
            Naming.rebind("//:2005/Chat", impl);
            System.out.println("Chat Object Bound");
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
}

