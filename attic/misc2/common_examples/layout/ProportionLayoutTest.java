
package layout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JFrame;

import common.ui.layout.*;

public class ProportionLayoutTest extends JPanel {
	public ProportionLayoutTest() {
		int[] rows = {1, 2, 1, 2, 1, 2, 1};
		int[] cols = {1, 2, 1, 2, 1, 2, 1};
		setLayout(new ProportionLayout(rows, cols));

		JPanel[] panels = new JPanel[49];
		for (int i = 0; i < panels.length; i++) {
			panels[i] = new JPanel();
			if (i % 2 == 0) {
				panels[i].setBackground(Color.white);
			} else {
				panels[i].setBackground(Color.black);
			}
			add(panels[i]);
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Table Panel");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add("Center", new ProportionLayoutTest());
		frame.setBounds(0, 0, 200, 200);
		frame.setDefaultCloseOperation(3);		
		frame.show();
	}

}