
import com.oti.ulc.util.*;
import com.oti.ulc.application.*;

public class GuestBookView extends ULCContext {

    private EntryListModel entryListModel;
    private EntryFormModel entryFormModel;
    
    
    public void start() { 
        
    	ULCApplication app = new ULCApplication(this);
		
		ULCShell shell= new ULCShell("GuestBook", true);
		shell.addActionListener(new QuitAction(app));
		app.add(shell);
		
		ULCMenuBar mb= new ULCMenuBar();
		shell.setMenuBar(mb);
		ULCMenu file= new ULCMenu("File");
		mb.add(file);
		file.add("Quit", new QuitAction(app));

      	ULCVBox vbox = new ULCVBox(10);
      	ULCBox box   = new ULCBox(2,3,5,5); 
    
      	
      	entryFormModel = new EntryFormModel();
      	box.add("lc", new ULCLabel("Name"));
      	box.add("lc", new ULCField(entryFormModel, "name", 30));
      	ULCButton addButton = new ULCButton("Add", new IActionListener() {
      	                                    public void actionPerformed(ULCActionEvent e) {
      	                                        entryFormModel.flush();
      	                                        GuestBook.getInstance().addEntry(entryFormModel);   
      	                                        entryFormModel.clear();
      	                                    }
      	                                    });
      	addButton.setEnabler(entryFormModel);                                    
      	box.add("ee", addButton); 
      	                                    
      	box.add("lc", new ULCLabel("Message"));
      	box.add("lc", new ULCField(entryFormModel, "message", 30));
      	
      	vbox.add("lc", box);
      	
      	entryListModel = new EntryListModel();      	      
    	ULCTable messageTable= new ULCTable(entryListModel, 700, 10);

        for (int i = 0; i < entryListModel.getColumnCount(); i++) {
            messageTable.addColumn(entryListModel.getColumnName(i), entryListModel.getWidth(i));    
        }

      	vbox.add("ee", messageTable); 
      	
		shell.add(vbox);
		
        shell.setSize(630, 400);
		shell.show();
    }    
            
    class QuitAction implements IActionListener {
	    ULCApplication fApplication;

    	QuitAction(ULCApplication application) {
	    	fApplication= application;
    	}
	
	    public void actionPerformed(ULCActionEvent e) {
		    if (e.getCmd("").equals("Quit") || e.getCmd("").equals("windowClosing")) {
		        entryListModel.removeListenerConnection();
			    fApplication.terminate();
			}
    	}
    }
}
