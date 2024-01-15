package outlook;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import common.ui.outlook.*;
import common.ui.layout.*;

public class ScrollingPanelTest {
	public static void main(String[] args) {
		common.swing.PlafPanel.setNativeLookAndFeel(true);

		JPanel scroll = new JPanel();
		scroll.setLayout(new ListLayout());
		scroll.add(new RolloverButton("Alpha"));
		scroll.add(new RolloverButton("Beta"));
		scroll.add(new RolloverButton("Gamma"));
		scroll.add(new RolloverButton("Delta"));
		scroll.add(new RolloverButton("Epsilon"));

		JFrame frame = new JFrame("ScrollingPanel Test");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add("Center", new ScrollingPanel(scroll));
		frame.setBounds(0, 0, 100, 200);
		frame.setDefaultCloseOperation(3);
		frame.setVisible(true);

	}

}