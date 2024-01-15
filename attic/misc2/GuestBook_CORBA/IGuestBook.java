
import java.util.*;

public interface IGuestBook extends org.omg.CORBA.Object {
    void addEntry(EntryListener el, Entry e);
    public Vector getEntries();
    
    void addEntryListener(EntryListener el);
    void removeEntryListener(EntryListener el);
}