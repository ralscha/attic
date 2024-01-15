package layout;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import common.ui.layout.*;

public class FieldLayoutTest extends JPanel {
	public FieldLayoutTest() {
		setBorder(new TitledBorder("Border"));
		setLayout(new FieldLayout(4, 4));
		add(new JLabel("Prompt One:"));
		add(new JTextField("Field one"));
		add(new JLabel("Prompt Two:"));
		add(new JTextField("Field two"));
		add(new JLabel("Prompt Three:"));
		add(new JTextField("Field three"));
		add(new JLabel("Prompt Four:"));
		add(new JTextField("Field four"));
		add(new JLabel("Prompt Five:"));
	}

	public static void main(String[] args) {
		common.swing.PlafPanel.setNativeLookAndFeel(true);
		JFrame frame = new JFrame("FieldLayout Test");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add("Center", new FieldLayoutTest());
		frame.setBounds(100, 100, 400, 300);
		frame.setDefaultCloseOperation(3);
		frame.show();
	}
}