import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Timeout {

	public static final int TIMEOUT = 10000; // milliseconds
	public static void main(String[] args) {
		Frame frame = new Frame("Progress Demo");
		WindowListener exitListener;
		TimeoutListener timeoutListener;
		Timer timer;
		
		exitListener = new WindowAdapter() {
               			public void windowClosing(WindowEvent e) {
               				Window window = e.getWindow();
               				window.setVisible(false);
               				window.dispose();
               				System.exit(0);
               			}
               		};


		timeoutListener = new TimeoutListener(TIMEOUT);
		frame.addMouseListener(timeoutListener);
		frame.addMouseMotionListener(timeoutListener);
		frame.addKeyListener(timeoutListener);
		frame.addWindowListener(exitListener);
		frame.setSize(400, 400);
		frame.setVisible(true);

		// We poll at half the timeout interval to reduce the fudge factor
		// introduced by events delivered at just under the timeout value.
		timer = new Timer(TIMEOUT / 2, timeoutListener);
		timer.setRepeats(true);
		timer.start();
	}
}