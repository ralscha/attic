package common.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BusyCursor {

	private JFrame frame;

	public BusyCursor(JFrame frame) {
		this.frame = frame;
		this.frame.getGlassPane().addMouseListener(new MouseAdapter() {});
		this.frame.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
	}

	public void setBusy(boolean busy) {
		frame.getGlassPane().setVisible(busy);
	}

}