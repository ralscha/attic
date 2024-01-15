import kiwi.ui.*;
import javax.swing.*;

public class Status {
	
	private static Status instance = new Status();
	private StatusBar statusBar;
	
	protected Status() {
		statusBar = new StatusBar(true);
	}
	
	public static JComponent getStatusBarComponent() {
		return getInstance().statusBar;
	}
	
	public static void setBusy(boolean busy) {
		getInstance().statusBar.setBusy(busy);
	}
	
	public static void setText(String text) {
		getInstance().statusBar.setText(text);
	}
	
	private static Status getInstance() {	
		return instance;
	}
	
}