
public interface EntryListener extends java.rmi.Remote {
    void newEntryArrived(Entry e) throws java.rmi.RemoteException;    
}