

public class EntryEvent extends java.util.EventObject {

    private Entry entry;

    public EntryEvent(GuestBook source, Entry newEntry) {
        super(source);
        this.entry = newEntry;
    }
    
    public Entry getEntry() {
        return entry;
    }
    
}