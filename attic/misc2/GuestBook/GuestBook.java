import java.util.*;

public class GuestBook {

    private static final GuestBook instance = new GuestBook();
    private Vector entryListeners;
    private Vector entries;
        
    protected GuestBook() {
        entryListeners = new Vector();
        entries = new Vector();
    }
    
    public static GuestBook getInstance() {
        return instance;
    }
    
    public Vector getEntries() {
        Vector v;
        synchronized(this) {
            v = (Vector)entries.clone();
        }
        return v;
    }
        
    public synchronized void addEntryListener(EntryListener el) {
        if (entryListeners.contains(el)) return;
        entryListeners.addElement(el);
    }
    
    public synchronized void removeEntryListener(EntryListener el) {
        entryListeners.removeElement(el);
    }
    
    public void addEntry(Entry newEntry) {
        
        entries.addElement(newEntry);        
        fireNewEntryArrived(newEntry);
    }
    
    public void addEntry(EntryFormModel formModel) {
        Entry newEntry = new Entry((String)formModel.getValueAt("name"), 
                                   (String)formModel.getValueAt("message"));
        entries.addElement(newEntry);
        fireNewEntryArrived(newEntry);                                    
    }
    
    private void fireNewEntryArrived(Entry newEntry) {
        Vector v;
        synchronized(this) {
            v = (Vector)entryListeners.clone();
            int size = v.size();
            if (size == 0) return;
            
            EntryEvent event = new EntryEvent(this, newEntry);
            
            for (int i = 0; i < size; i++) {
                EntryListener listener = (EntryListener)v.elementAt(i);
                listener.newEntryArrived(event);
            }
        }
        
    }
        
}