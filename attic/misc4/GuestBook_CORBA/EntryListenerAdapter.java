
public class EntryListenerAdapter extends _EntryListenerImplBase {
    
    private EntryTableModel etmodel;
    
    public EntryListenerAdapter(EntryTableModel etmodel) {
        this.etmodel = etmodel;                
    }
    
    public void newEntryArrived(Entry e) {
        etmodel.newEntryArrived(e);
    }

}