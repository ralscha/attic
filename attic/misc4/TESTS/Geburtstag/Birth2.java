/*
    This class is an extension of the Frame class for use as the
    main window of an application.

    You can add controls or menus to Birth2 with Cafe Studio.
 */

import java.awt.*;

public class Birth2 extends Frame {

    public Birth2() {

	super("Birth2 window");

	//{{INIT_MENUS
	MenuBar mb = new MenuBar();
	fileMenu = new Menu("&File");
	fileMenu.add(new MenuItem("&New"));
	fileMenu.add(new MenuItem("&Open..."));
	fileMenu.add(new MenuItem("&Save"));
	fileMenu.add(new MenuItem("Save &As..."));
	fileMenu.addSeparator();
	fileMenu.add(new MenuItem("E&xit"));
	mb.add(fileMenu);
	editMenu = new Menu("&Edit");
	editMenu.add(new MenuItem("&Undo"));
	editMenu.addSeparator();
	editMenu.add(new MenuItem("Cu&t"));
	editMenu.add(new MenuItem("&Copy"));
	editMenu.add(new MenuItem("&Paste"));
	mb.add(editMenu);
	helpMenu = new Menu("&Help");
	helpMenu.add(new MenuItem("&About..."));
	mb.add(helpMenu);
	setMenuBar(mb);
	//}}

	//{{INIT_CONTROLS
	setLayout(null);
	addNotify();
	resize(insets().left + insets().right + 352, insets().top + insets().bottom + 254);
	//}}

	show();
    }

    public synchronized void show() {
	move(50, 50);
	super.show();
    }

    public boolean handleEvent(Event event) {

	if (event.id == Event.WINDOW_DESTROY) {
	    hide();         // hide the Frame
	    dispose();      // tell windowing system to free resources
	    System.exit(0); // exit
	    return true;
	}
	return super.handleEvent(event);
    }

    public boolean action(Event event, Object arg) {
	if (event.target instanceof MenuItem) {
	    String label = (String) arg;
	    if (label.equalsIgnoreCase("&About...")) {
		selectedAbout();
		return true;
	    } else if (label.equalsIgnoreCase("E&xit")) {
		selectedExit();
		return true;
	    } else if (label.equalsIgnoreCase("&Open...")) {
		selectedOpen();
		return true;
	    }
	}
	return super.action(event, arg);
    }

    public static void main(String args[]) {
	new Birth2();
    }

    //{{DECLARE_MENUS
    Menu fileMenu;
    Menu editMenu;
    Menu helpMenu;
    //}}

    //{{DECLARE_CONTROLS
    //}}

    public void selectedOpen() {
	(new FileDialog(this, "Open...")).show();
    }
    public void selectedExit() {
	QuitBox theQuitBox;
	theQuitBox = new QuitBox(this);
	theQuitBox.show();
    }
    public void selectedAbout() {
	AboutBox theAboutBox;
	theAboutBox = new AboutBox(this);
	theAboutBox.show();
    }
}


/*
    This class is a basic extension of the Dialog class.  It can be used
    by subclasses of Frame.  To use it, create a reference to the class,
    then instantiate an object of the class (pass 'this' in the constructor),
    and call the show() method.

    example:

    AboutBox theAboutBox;
    theAboutBox = new AboutBox(this);
    theAboutBox.show();

    You can add controls to AboutBox with Cafe Studio.
    (Menus can be added only to subclasses of Frame.)
 */

class AboutBox extends Dialog {

    public AboutBox(Frame parent) {

	    super(parent, "About", true);
	setResizable(false);

	//{{INIT_CONTROLS
	setLayout(null);
	addNotify();
	resize(insets().left + insets().right + 292, insets().top + insets().bottom + 79);
	label1=new Label("Simple Java SDI Application");
	add(label1);
	label1.reshape(insets().left + 12,insets().top + 18,174,16);
	OKButton=new Button("OK");
	add(OKButton);
	OKButton.reshape(insets().left + 204,insets().top + 12,72,26);
	//}}
    }

    public synchronized void show() {
	Rectangle bounds = getParent().bounds();
	Rectangle abounds = bounds();

	move(bounds.x + (bounds.width - abounds.width)/ 2,
	     bounds.y + (bounds.height - abounds.height)/2);

	super.show();
    }

    public synchronized void wakeUp() {
	notify();
    }

    public boolean handleEvent(Event event) {
	if (event.id == Event.ACTION_EVENT && event.target == OKButton) {
		clickedOKButton();
		return true;
	}
	else

	if (event.id == Event.WINDOW_DESTROY) {
	    hide();
	    return true;
	}
	return super.handleEvent(event);
    }

    //{{DECLARE_CONTROLS
    Label label1;
    Button OKButton;
    //}}

    public void clickedOKButton() {
	handleEvent(new Event(this, Event.WINDOW_DESTROY, null));
    }
}


/*
    This class is a basic extension of the Dialog class.  It can be used
    by subclasses of Frame.  To use it, create a reference to the class,
    then instantiate an object of the class (pass 'this' in the constructor),
    and call the show() method.

    example:

    QuitBox theQuitBox;
    theQuitBox = new QuitBox(this);
    theQuitBox.show();

    You can add controls, but not menus, to QuitBox with Cafe Studio.
    (Menus can be added only to subclasses of Frame.)
 */

class QuitBox extends Dialog {

    public QuitBox(Frame parent) {

	    super(parent, "Quit Application?", true);
	setResizable(false);

	//{{INIT_CONTROLS
	setLayout(null);
	addNotify();
	resize(insets().left + insets().right + 261, insets().top + insets().bottom + 72);
	yesButton=new Button("Yes");
	add(yesButton);
	yesButton.reshape(insets().left + 68,insets().top + 10,46,23);
	noButton=new Button("No");
	add(noButton);
	noButton.reshape(insets().left + 135,insets().top + 10,47,23);
	//}}
    }

    public synchronized void show() {
	Rectangle bounds = getParent().bounds();
	Rectangle abounds = bounds();

	move(bounds.x + (bounds.width - abounds.width)/ 2,
	     bounds.y + (bounds.height - abounds.height)/2);

	super.show();
    }

    public synchronized void wakeUp() {
	notify();
    }

    public boolean handleEvent(Event event) {
	if (event.id == Event.ACTION_EVENT && event.target == noButton) {
		clickedNoButton();
		return true;
	}
	else
	if (event.id == Event.ACTION_EVENT && event.target == yesButton) {
		clickedYesButton();
		return true;
	}
	else

	if (event.id == Event.WINDOW_DESTROY) {
	    hide();
	    return true;
	}
	return super.handleEvent(event);
    }

    //{{DECLARE_CONTROLS
    Button yesButton;
    Button noButton;
    //}}

    public void clickedYesButton() {
	System.exit(0);
    }
    public void clickedNoButton() {
	handleEvent(new Event(this, Event.WINDOW_DESTROY, null));
    }
}
