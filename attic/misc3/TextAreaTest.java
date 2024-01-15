import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextAreaTest extends JFrame {

	public TextAreaTest() {
		super("TextAreaTest");
		setDefaultCloseOperation(3);

		getContentPane().setLayout(new FlowLayout());
		JTextArea ta = new JTextArea("Dies ist ein Test",3, 10) {
			public void paste() {
				if (!isEditable() || !isEnabled()) {
					return;
				}
				super.paste();
			}};
			
		ta.setEditable(false);
		getContentPane().add(ta);

		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		new TextAreaTest();
	}
}