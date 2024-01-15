package common.ui.splash;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;

public class JSplash extends JWindow implements KeyListener, MouseListener, ActionListener {
	
	public JSplash(JFrame parent, ImageIcon image, int timeout) {
		super(parent);
		int w = image.getIconWidth() + 5;
		int h = image.getIconHeight() + 5;

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - w) / 2;
		int y = (screen.height - h) / 2;
		setBounds(x, y, w, h);

		getContentPane().setLayout(new BorderLayout());
		JLabel picture = new JLabel(image);
		getContentPane().add(BorderLayout.CENTER, picture);
		picture.setBorder(new BevelBorder(BevelBorder.RAISED));

		// Listen for key strokes
		addKeyListener(this);

		// Listen for mouse events from here and parent
		addMouseListener(this);
		parent.addMouseListener(this);

		// Timeout after a while
		Timer timer = new Timer(0, this);
		timer.setRepeats(false);
		timer.setInitialDelay(timeout);
		timer.start();
	}
	
	public JSplash(JFrame parent, String filename, int timeout) {
		this(parent, new ImageIcon(filename), timeout);		
	}

	public void block() {
		while (isVisible()) {}
	}

	// Dismiss the window on a key press
	public void keyTyped(KeyEvent event) {}
	public void keyReleased(KeyEvent event) {}
	public void keyPressed(KeyEvent event) {
		setVisible(false);
		dispose();
	}

	// Dismiss the window on a mouse click
	public void mousePressed(MouseEvent event) {}
	public void mouseReleased(MouseEvent event) {}
	public void mouseEntered(MouseEvent event) {}
	public void mouseExited(MouseEvent event) {}
	public void mouseClicked(MouseEvent event) {
		setVisible(false);
		dispose();
	}

	// Dismiss the window on a timeout
	public void actionPerformed(ActionEvent event) {
		setVisible(false);
		dispose();
	}
}