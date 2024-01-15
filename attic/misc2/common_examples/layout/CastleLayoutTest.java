package layout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JFrame;

import common.ui.layout.*;

public class CastleLayoutTest extends JPanel {
	public CastleLayoutTest() {
		int[] rows = {1, 2, 1, 2, 1, 2, 1};
		int[] cols = {1, 2, 1, 2, 1, 2, 1};
		setLayout(new CastleLayout());

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
		JPanel panel6 = new JPanel();
		JPanel panel7 = new JPanel();
		JPanel panel8 = new JPanel();
		JPanel panel9 = new JPanel();

		panel1.setBackground(Color.blue);
		panel2.setBackground(Color.blue);
		panel3.setBackground(Color.blue);
		panel4.setBackground(Color.blue);
		panel5.setBackground(Color.orange);
		panel6.setBackground(Color.orange);
		panel7.setBackground(Color.orange);
		panel8.setBackground(Color.orange);
		panel9.setBackground(Color.green);

		panel5.setPreferredSize(new Dimension(25, 25));
		panel8.setPreferredSize(new Dimension(25, 25));

		add(panel1, "North");
		add(panel2, "South");
		add(panel3, "East");
		add(panel4, "West");
		add(panel5, "NorthEast");
		add(panel6, "NorthWest");
		add(panel7, "SouthEast");
		add(panel8, "SouthWest");
		add(panel9, "Center");
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Table Panel");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add("Center", new CastleLayoutTest());
		frame.setBounds(0, 0, 200, 200);
		frame.setDefaultCloseOperation(3);
		frame.show();
	}

}