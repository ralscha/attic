package common.swing;

import javax.swing.*;
import java.awt.event.*;

public class MinSizeJFrame extends JFrame implements ComponentListener {

	private int minWidth;
	private int minHeight;

	public MinSizeJFrame(String title) {
		this(title, 300, 300);
	}

	public MinSizeJFrame(String title, int minWidth, int minHeight) {
		super(title);
		this.minWidth = minWidth;
		this.minHeight = minHeight;
		addComponentListener(this);
	}


	public void componentResized(ComponentEvent e) {

		int width = getWidth();
		int height = getHeight();

		//we check if either the width
		//or the height are below minimum

		boolean resize = false;

		if (width < minWidth) {
			resize = true;
			width = minWidth;
		}
		if (height < minHeight) {
			resize = true;
			height = minHeight;
		}
		if (resize) {
			setSize(width, height);
		}
	}


	public void componentMoved(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}

	public void componentHidden(ComponentEvent e) {
	}

}