package common.ui.preview;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PreviewButton extends JButton {
	protected String command;

	public PreviewButton(String name, ActionListener listener) {
	
		ImageIcon icon = new ImageIcon(getClass().getResource("icons/" + name + "Icon.gif"));
		
		setIcon(icon);
		setPreferredSize(new Dimension(22, 22));
		setMaximumSize(new Dimension(22, 22));
		setFocusPainted(false);
		addActionListener(listener);
		command = name;
	}

	public boolean isFocusTraversable() {
		return true;
	}

	public boolean isDefaultButton() {
		return false;
	}

	public String getCommand() {
		return command;
	}
}

