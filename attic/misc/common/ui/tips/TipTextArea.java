package common.ui.tips;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class TipTextArea extends JTextArea {
	public TipTextArea() {
		super();
		setFont(new Font("Helvetica", Font.PLAIN, 12));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setWrapStyleWord(true);
		setEditable(false);
		setLineWrap(true);
	}

	public boolean isFocusTraversable() {
		return false;
	}
}