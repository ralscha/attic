package borders;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import common.ui.borders.*;

public class JBorderRounded extends JPanel implements BorderConstants {
	protected Object[] borders = { JBorder.createRoundedBorder(Color.blue, null, 10, ALL_CORNERS,
                               	N_SIDE + W_SIDE), "N,W SIDES",
                               	JBorder.createRoundedBorder(Color.red, null, 10, ALL_CORNERS,
                                                           	S_SIDE + W_SIDE + E_SIDE), "S,E,W SIDES",
                               	JBorder.createRoundedBorder(Color.blue, null, 10, ALL_CORNERS,
                                                           	N_SIDE + E_SIDE), "N,E SIDES",
                               	JBorder.createRoundedBorder(Color.red, null, 10, ALL_CORNERS,
                                                           	E_SIDE + N_SIDE + S_SIDE), "N,S,E SIDES",
                               	JBorder.createRoundedBorder(Color.blue, null, 10, NE_CORNER + SW_CORNER,
                                                           	ALL_SIDES), "NE,SW CORNERS",
                               	JBorder.createRoundedBorder(Color.red, null, 10, ALL_CORNERS,
                                                           	W_SIDE + N_SIDE + S_SIDE), "N,S,W SIDES",
                               	JBorder.createRoundedBorder(Color.blue, null, 10, ALL_CORNERS,
                                                           	S_SIDE + W_SIDE), "S,W SIDES",
                               	JBorder.createRoundedBorder(Color.red, null, 10, ALL_CORNERS,
                                                           	N_SIDE + W_SIDE + E_SIDE), "N,E,W SIDES",
                               	JBorder.createRoundedBorder(Color.blue, null, 10, ALL_CORNERS,
                                                           	S_SIDE + E_SIDE), "S,E SIDES" };

	public JBorderRounded() {
		setBorder(JBorder.createEmptyBorder(5));
		int size = borders.length;
		setLayout(new GridLayout(3, 3, 5, 5));
		for (int i = 0; i < size; i += 2) {
			add(makeLabel((Border) borders[i], (String) borders[i + 1]));
		}
	}

	private JLabel makeLabel(Border border, String name) {
		JLabel label = new JLabel();
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBorder(border);
		label.setOpaque(true);
		label.setText(name);
		return label;
	}

	public static void main(String[] args) {
		common.swing.PlafPanel.setNativeLookAndFeel(true);

		JFrame frame = new JFrame("Rounded Border Examples");
		frame.setBounds(50, 50, 500, 200);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(BorderLayout.CENTER, new JBorderRounded());
		frame.setDefaultCloseOperation(3);
		frame.show();
	}
}
