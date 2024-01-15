

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class TimeoutListener implements MouseListener, MouseMotionListener, KeyListener,
ActionListener {
	Object _lock;
	long _timeout, _lastTime;

	public TimeoutListener(long timeout) {
		_lock = new int[1];
		_timeout = timeout;
		_lastTime = System.currentTimeMillis();
	}

	private void __recordLastEvent(InputEvent event) {
		synchronized (_lock) {
			_lastTime = System.currentTimeMillis();
			System.out.println("Last Event: " + _lastTime + " " + event);
		}
	}

	public void actionPerformed(ActionEvent e) {
		synchronized (_lock) {
			// We really shouldn't exit, but cleanup gracefully instead
			if (System.currentTimeMillis() - _lastTime >= _timeout) {
				System.out.println("Timed out! " + System.currentTimeMillis());
				System.exit(0);
			} else
				System.out.println("Still active.");
		}
	}

	public void mouseDragged(MouseEvent e) {
		__recordLastEvent(e);
	}
	public void mouseMoved(MouseEvent e) {
		__recordLastEvent(e);
	}
	public void mouseClicked(MouseEvent e) {
		__recordLastEvent(e);
	}
	public void mouseEntered(MouseEvent e) {
		__recordLastEvent(e);
	}
	public void mouseExited(MouseEvent e) {
		__recordLastEvent(e);
	}
	public void mousePressed(MouseEvent e) {
		__recordLastEvent(e);
	}
	public void mouseReleased(MouseEvent e) {
		__recordLastEvent(e);
	}
	public void keyPressed(KeyEvent e) {
		__recordLastEvent(e);
	}
	public void keyReleased(KeyEvent e) {
		__recordLastEvent(e);
	}
	public void keyTyped(KeyEvent e) {
		__recordLastEvent(e);
	}
}
