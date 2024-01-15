
import java.util.*;

public interface IGuestBook extends java.rmi.Remote {
    void addEntry(EntryListener el, Entry e) throws java.rmi.RemoteException;
    public Vector getEntries() throws java.rmi.RemoteException;
    
    void addEntryListener(EntryListener el) throws java.rmi.RemoteException;
    void removeEntryListener(EntryListener el) throws java.rmi.RemoteException;
}