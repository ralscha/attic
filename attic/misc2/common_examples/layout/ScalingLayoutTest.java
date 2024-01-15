package layout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JFrame;

import common.ui.layout.*;

public class ScalingLayoutTest extends JPanel {
	public ScalingLayoutTest() {
		setLayout(new ScalingLayout(4, 4));

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
		JPanel panel6 = new JPanel();
		JPanel panel7 = new JPanel();
		JPanel panel8 = new JPanel();
		JPanel panel9 = new JPanel();
		JPanel panel0 = new JPanel();
		JPanel panelA = new JPanel();

		panel1.setBackground(Color.white);

		panel1.setBackground(Color.white);
		panel2.setBackground(Color.black);
		panel3.setBackground(Color.white);
		panel4.setBackground(Color.green);
		panel5.setBackground(Color.gray);
		panel6.setBackground(Color.black);
		panel7.setBackground(Color.white);
		panel8.setBackground(Color.black);
		panel9.setBackground(Color.orange);
		panel0.setBackground(Color.blue);
		panelA.setBackground(Color.white);

		add(panelA, new Rectangle(3, 3, 1, 1));
		add(panel1, new Rectangle(0, 0, 1, 1));
		add(panel2, new Rectangle(1, 0, 1, 1));
		add(panel3, new Rectangle(2, 0, 1, 1));
		add(panel4, new Rectangle(0, 1, 2, 1));
		add(panel5, new Rectangle(2, 2, 2, 2));
		add(panel6, new Rectangle(2, 1, 1, 1));
		add(panel7, new Rectangle(0, 2, 1, 1));
		add(panel8, new Rectangle(1, 2, 1, 1));
		add(panel9, new Rectangle(0, 3, 2, 1));
		add(panel0, new Rectangle(3, 0, 1, 2));
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Table Panel");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add("Center", new ScalingLayoutTest());
		//frame.setBounds(0, 0, 200, 200);
		frame.setDefaultCloseOperation(3);		
		frame.pack();
		frame.show();
	}

}