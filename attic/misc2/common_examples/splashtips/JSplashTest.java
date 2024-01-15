package splashtips;

import javax.swing.*;
import java.awt.*;
import common.ui.splash.*;

public class JSplashTest {
	
	public JSplashTest() {
		JFrame frame = new JFrame("Splash Screen Test");
		
		ImageIcon icon = new ImageIcon( Toolkit.getDefaultToolkit().createImage(
	                         getClass().getResource("JSplash.gif")));
		JSplash splash = new JSplash(frame, icon, 5000);
		splash.show();

		frame.setBounds(100, 100, 400, 400);
		frame.setDefaultCloseOperation(3);
		frame.show();

		// Critical - this focus request must be here after
		// showing the parent to get keystrokes properly.
		splash.requestFocus();
	}
	
	
	public static void main(String[] args) {
		new JSplashTest();
	}
}