

/*
 * Copyright (c) 1997,1998 Object Technology International Inc.
 */
import com.oti.ulc.application.*;

class QuitAction implements IActionListener {
	ULCApplication fApplication;

	QuitAction(ULCApplication application) {
		fApplication= application;
	}
	public void actionPerformed(ULCActionEvent e) {
		if (e.getCmd("").equals("Quit") || e.getCmd("").equals("windowClosing"))
			fApplication.terminate();
	}
}