


/*
 * Copyright (c) 1997,1998 Object Technology International Inc.
 */
import com.oti.ulc.util.*;
import com.oti.ulc.application.*;

/*
 * A minimal ULC application.
 */
public
class Hello extends ULCContext {

	public static
	void main(String args[]) {
		ULC.run(args, Hello.class);
	}
	
	
	public void start() {
	
	    System.out.println(this);
	
		ULCApplication app= new ULCApplication(this);
		
		ULCShell shell= new ULCShell("Hello World", true);	// receive CloseBox events
		shell.addActionListener(new QuitAction(app));

		app.add(shell);
		shell.add(new ULCLabel("Hello World!"));

		ULCMenuBar mb= new ULCMenuBar();
		shell.setMenuBar(mb);

		ULCMenu file= new ULCMenu("File");
		mb.add(file);

		file.add("Quit", new QuitAction(app));

		shell.show();
	}
}