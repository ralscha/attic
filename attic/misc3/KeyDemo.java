
/***
 * $RCSfile: KeyDemo.java,v $ $Revision: 1.1 $ $Date: 1999/08/08 20:28:47 $
 *
 * QUESTION:
 * How can I capture KeyEvents in a Frame that doesn't
 * have any TextFields or TextAreas?
 ***/                       

import java.awt.*;
import java.awt.event.*;

public class KeyDemo {

  public static void main(String[] args) {
    Frame frame = new Frame("foo");
    WindowListener exitListener;

    exitListener = new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        Window window = e.getWindow();
        window.setVisible(false);
        window.dispose();
        System.exit(0);
      }
    };

    frame.addWindowListener(exitListener);
    frame.addKeyListener( new KeyListener() {
	public void keyPressed(KeyEvent e) {
	  System.out.println("keyPressed: " + e.toString());
	}
	public void keyReleased(KeyEvent e) {
	  System.out.println("keyReleased: " + e.toString());
	}
	public void keyTyped(KeyEvent e) {
	  System.out.println("keyTyped: " + e.toString());
	}
      });
      
    frame.setSize(400, 400);
    frame.setVisible(true);
  }
}
