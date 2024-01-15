package outlook;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;

import common.ui.outlook.*;

public class ContextPanelTest extends ContextPanel {
	public ContextPanelTest(int index) {
		JPanel center1 = new JPanel();
		center1.setBackground(Color.blue);
		center1.setPreferredSize(new Dimension(225, 225));
		JPanel center2 = new JPanel();
		center2.setBackground(Color.green);
		JPanel center3 = new JPanel();
		center3.setBackground(Color.red);
		JPanel center4 = new JPanel();
		center4.setBackground(Color.white);
		JPanel center5 = new JPanel();
		center5.setBackground(Color.orange);
		JPanel center6 = new JPanel();
		center6.setBackground(Color.black);

		addTab("Blue", center1);
		addTab("Green", center2);
		addTab("Red", center3);
		addTab("White", center4);
		addTab("Orange", center5);
		addTab("Black", center6);
		setIndex(index);
	}

	public static void main(String[] args) {
		common.swing.PlafPanel.setNativeLookAndFeel(false);
		JFrame frame = new JFrame("ContextPanel Test");
		frame.getContentPane().setLayout(new GridLayout(1, 3));
		frame.getContentPane().add(new ContextPanelTest(3));
		frame.getContentPane().add(new ContextPanelTest(2));
		frame.getContentPane().add(new ContextPanelTest(1));
		frame.setBounds(0, 0, 250, 250);
		frame.setDefaultCloseOperation(3);
		frame.show();
	}

}