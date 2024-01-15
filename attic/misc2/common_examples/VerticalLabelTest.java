import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import common.swing.*;

// This code uses Graphics2D which is supported only on Java 2

public class VerticalLabelTest {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Main");
		frame.setDefaultCloseOperation(3);

		frame.getContentPane().setLayout(new FlowLayout());
		ImageIcon icon = new ImageIcon("dukeWave.gif");
		JLabel l = new JLabel("Rotated anti-clockwise", icon, SwingConstants.LEFT);
		l.setUI(new VerticalLabelUI(false));
		l.setBorder(new EtchedBorder());
		frame.getContentPane().add(l);
		l = new JLabel("RotatedClockwise", icon, SwingConstants.LEFT);
		//		l.setHorizontalTextPosition( SwingConstants.LEFT );
		l.setUI(new VerticalLabelUI(true));
		l.setBorder(new EtchedBorder());
		frame.getContentPane().add(l);
		frame.getContentPane().add(new JButton("<html><b>Button</b>"));
		frame.pack();
		frame.show();
	}
}