package common.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JExitFrame extends JFrame {

	private boolean exit;	

	private class Exiter extends WindowAdapter {
		public void windowClosing(WindowEvent windowevent) {
			if (exit) {
				doExit();
			}
		}
	}

	protected void doExit() {
		dispose();
		System.exit(0);
	}

	public JExitFrame() {
		exit = true;
		addWindowListener(new Exiter());
	}

	public JExitFrame(String s) {
		super(s);
		exit = true;
		addWindowListener(new Exiter());
	}

	public boolean getExitOnClose() {
		return exit;
	}

	public void setExitOnClose(boolean flag) {
		exit = flag;
	}

}