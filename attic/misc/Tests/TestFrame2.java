

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class TestFrame2 extends JFrame {


	public TestFrame2(String title) {
		super(title);
		
		EmptyBorder border = new EmptyBorder(20,20,20,20);
		
		JLabel text = new JLabel("Loading GtfUserManager...");
		text.setBorder(border);
		text.setFont(new Font("Verdana", Font.PLAIN, 20));
		getContentPane().add(text);
		
		pack();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = getSize();
		screenSize.height = screenSize.height/2;
		screenSize.width = screenSize.width/2;
		size.height = size.height/2;
		size.width = size.width/2;
		setLocation(screenSize.width - size.width, screenSize.height - size.height);
	}

	public static void main(String args[]) {
		JFrame frame = new TestFrame2("Please wait");
		frame.setVisible(true);
	}
}