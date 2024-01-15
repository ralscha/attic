import COM.odi.util.*;
import com.oti.ulc.util.*;
import com.oti.ulc.application.*;
import java.awt.*;
import java.util.*;

public class SWIFTInput_ULC extends ULCContext {

    Calendar from, to;

    public void start() { 
        
        DbManager.initialize(false);
        
    	ULCApplication app = new ULCApplication(this);
		
		ULCShell shell= new ULCShell("SWIFT Input", true);
		shell.addActionListener(new QuitAction(app));

		app.add(shell);
		
		SWIFTHeaderModel_ULC swiftHeaderModel = new SWIFTHeaderModel_ULC();
    	ULCTable swiftHeaderTable= new ULCTable(swiftHeaderModel, 700, 50);

//    	fAccountsTable.setAutoResize(ULCTable.AUTO_RESIZE_ALL_COLUMNS); 

        for (int i = 0; i < swiftHeaderModel.getColumnCount(); i++) {
            swiftHeaderTable.addColumn(swiftHeaderModel.getColumnName(i), swiftHeaderModel.getWidth(i));
        }
        
        
        DbManager.startReadTransaction();
        long millis = DbManager.getFromDate();
        from = new GregorianCalendar();
        from.setTime(new Date(millis));
        millis = DbManager.getToDate();
        to = new GregorianCalendar();
        to.setTime(new Date(millis));
        DbManager.commitTransaction();
    
        swiftHeaderModel.showHeaders(from, to);
	 		
//  	fAccountsTable.addSelectionChangedListener(new SelectAccountAction(this));

        shell.add(swiftHeaderTable);
		ULCMenuBar mb= new ULCMenuBar();
		shell.setMenuBar(mb);
		ULCMenu file= new ULCMenu("File");
		mb.add(file);
		file.add("Quit", new QuitAction(app));

        shell.setSize(740, 400);
		shell.show();
    }
    
    public static void main(String args[]) {
        ULC.run(args, SWIFTInput_ULC.class);
    }
    
    
    class QuitAction implements IActionListener {
	    ULCApplication fApplication;

    	QuitAction(ULCApplication application) {
	    	fApplication= application;
    	}
	
	    public void actionPerformed(ULCActionEvent e) {
		    if (e.getCmd("").equals("Quit") || e.getCmd("").equals("windowClosing")) {
		        DbManager.shutdown();
			    fApplication.terminate();
			}
    	}
    }
}
