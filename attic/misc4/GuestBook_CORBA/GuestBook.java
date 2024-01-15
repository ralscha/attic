
import java.util.*;

public class GuestBook extends _IGuestBookImplBase {
    
    private Vector entries;
    private Vector entryListeners;

    public GuestBook(){
        super();
        entries = new Vector();
        entryListeners = new Vector();
    }
    
    public Vector getEntries() {
        Vector v;
        synchronized(this) {
            v = (Vector)entries.clone();
        }
        return v;
    }
    
    public void addEntry(EntryListener el, Entry e) {
        entries.addElement(e);            
        fireNewEntryArrived(el, e);
    }
    
    
    public synchronized void addEntryListener(EntryListener el) {
        if (entryListeners.contains(el)) return;
        entryListeners.addElement(el);
    }
    
    public synchronized void removeEntryListener(EntryListener el) {
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
                listener.newEntryArrived(newEntry);
            }
        }
        
    }
}