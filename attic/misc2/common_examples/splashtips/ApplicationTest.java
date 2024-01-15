package splashtips;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import common.ui.tips.*;
import common.ui.splash.*;

public class ApplicationTest extends JFrame {
	public ApplicationTest(String title) {
		super(title);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add("Center", new BackgroundPanel());
	}

	public static void main(String[] args) {
		JFrame frame = new ApplicationTest("Application Test");

		ImageIcon icon = new ImageIcon( Toolkit.getDefaultToolkit().createImage(
	                         frame.getClass().getResource("JSplash.gif")));
		JSplash splash = new JSplash(frame, icon, 10000);
		splash.show();

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(100, 50, dim.width - 200, dim.height - 150);
		frame.setDefaultCloseOperation(3);		
		frame.show();

		// Critical - this focus request must be here after
		// showing the parent to get the focus properly.
		splash.requestFocus();

		JTips tips = new JTips("d:/javaprojects/private/common_ui/examples/splashtips/jtips.tip", new Properties());
		splash.block();
		tips.startup();
	}
}