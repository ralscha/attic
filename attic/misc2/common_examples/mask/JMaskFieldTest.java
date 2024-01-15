package mask;

import java.awt.GridLayout;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComponent;
import javax.swing.border.*;
import javax.swing.text.*;

import common.ui.mask.*;

public class JMaskFieldTest extends JPanel {
	JMaskField field;

	public JMaskFieldTest() {
		int count = 8;
		setLayout(new BorderLayout());
		setBorder( new CompoundBorder(new TitledBorder("Masked Fields"),
                              		new EmptyBorder(5, 5, 5, 5)));
		JPanel prompt = new JPanel();
		prompt.setLayout(new GridLayout(count, 1, 5, 5));
		JPanel fields = new JPanel();
		fields.setLayout(new GridLayout(count, 1, 5, 5));
		add("West", prompt);
		add("Center", fields);

		MaskMacros macros = new MaskMacros();
		macros.addMacro('#', "[0-9]");
		macros.addMacro('@', "[a-zA-Z]");

		int labelAlignment = JLabel.RIGHT;
		prompt.add(new JLabel("Phone: ", labelAlignment));
		fields.add(field = new JMaskField("(###) ###-####", macros));

		prompt.add(new JLabel("Zip Code: ", labelAlignment));
		fields.add(new JMaskField("#####-####", macros));

		prompt.add(new JLabel("Postal Code: ", labelAlignment));
		fields.add(new JMaskField("@#@ #@#", macros));

		prompt.add(new JLabel("Credit Card: ", labelAlignment));
		fields.add(new JMaskField("#### #### #### ####", macros));

		prompt.add(new JLabel("Social Security: ", labelAlignment));
		fields.add(new JMaskField("###-##-####", macros));

		prompt.add(new JLabel("Date: ", labelAlignment));
		fields.add(new JMaskField("{[01]}#/{[0-3]}#/##", macros));

		prompt.add(new JLabel("Time: ", labelAlignment));
		fields.add(new JMaskField("{[01]}#:{[0-5]}#", macros));

		prompt.add(new JLabel("Money: ", labelAlignment));
		fields.add(new JMaskField("$###,###", macros));
	}

	public static void main(String[] args) {
		common.swing.PlafPanel.setNativeLookAndFeel(true);
		JFrame frame = new JFrame("JMaskFieldTest");
		JMaskFieldTest field = new JMaskFieldTest();
		frame.getContentPane().add(field);
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(3);
		frame.pack();
		frame.show();
	}
}