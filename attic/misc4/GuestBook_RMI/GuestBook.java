
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;

public class GuestBook extends UnicastRemoteObject implements IGuestBook {
    
    private Vector entries;
    private Vector entryListeners;

    public GuestBook() throws RemoteException {
        super();
        entries = new Vector();
        entryListeners = new Vector();
    }
    
    public Vector getEntries() throws java.rmi.RemoteException {
        Vector v;
        synchronized(this) {
            v = (Vector)entries.clone();
        }
        return v;
    }
    
    public void addEntry(EntryListener el, Entry e) throws java.rmi.RemoteException {
        entries.addElement(e);            
        fireNewEntryArrived(el, e);
    }
    
    
    public synchronized void addEntryListener(EntryListener el) throws java.rmi.RemoteException {
        if (entryListeners.contains(el)) return;
        entryListeners.addElement(el);
    }
    
    public synchronized void removeEntryListener(EntryListener el) throws java.rmi.RemoteException {
        entryListeners.removeElement(el);
    }
    
    private void fireNewEntryArrived(EntryListener el, Entry newEntry) {
        Vector v;
        synchronized(this) {
            v = (Vector)entryListeners.clone();
            int size = v.size();
            if (size == 0) return;                        
            
            for (int i = 0; i < size; i++) {
                
                EntryListener listener = (EntryListener)v.elementAt(i);
                if (!listener.equals(el)) {
                    try {
                        listener.newEntryArrived(newEntry);
                    } catch (RemoteException re) {
                        re.printStackTrace();
                    }
                }
            }
        }
        
    }

    
    public static void main(String args[]) {
        System.setSecurityManager(new RMISecurityManager());
        
        try {
            LocateRegistry.createRegistry(2005);            
            GuestBook obj = new GuestBook();
            Naming.rebind("//:2005/GuestBook", obj);
            System.out.println("GuestBook bound in registry");            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}