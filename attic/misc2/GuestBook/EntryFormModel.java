import com.oti.ulc.application.*;

class EntryFormModel extends ULCFormModel {
    
    String name;
    String message;
    
    public EntryFormModel() {
        name = "";
        message = "";
    }

	public Object getValueAt(String key) {
	    if ("name".equals(key)) return name;
	    else if ("message".equals(key)) return message;	    
		else return null;
	}
	
	public void setValueAt(Object v, String key) {
	    if ("name".equals(key)) name = (String)v;
	    else if ("message".equals(key)) message = (String)v;
	}
	
	public void clear() {
		name = "";
		message = "";
		notify(CHANGED, null);
	}
	
}