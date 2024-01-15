
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import javax.swing.text.*; 
import javax.swing.event.*; 

public class TestFrame extends JFrame {
	
	public TestFrame() {
		super("Test Frame");
		addWindowListener(new BasicWindowMonitor());

		final JButton button = new JButton("BUTTON");
		button.setEnabled(false);
		
		final JTextField field = new JTextField(10);
		field.requestFocus();
		
		final JTextField field2 = new JTextField(10);
		
		DocumentListener dl = new DocumentListener() {
	        public void insertUpdate(DocumentEvent e) {
	        		if (field.getText().equals(""))
							button.setEnabled(false);
						else
							button.setEnabled(true);

	        }
	        public void removeUpdate(DocumentEvent e) {
	        		if (field.getText().equals(""))
							button.setEnabled(false);
						else
							button.setEnabled(true);

	        }
	        public void changedUpdate(DocumentEvent e) {}
		};
		
		field.getDocument().addDocumentListener(dl);
		field2.getDocument().addDocumentListener(dl);
		
		getContentPane().setLayout(new FlowLayout());
		getContentPane().add(field);
		getContentPane().add(field2);
		
		getContentPane().add(button);
		
		
		FocusAdapter focusAdapter = new FocusAdapter() {
					public void focusLost(FocusEvent e) {
						if (field.getText().equals(""))
							button.setEnabled(false);
						else
							button.setEnabled(true);
					}};
		
		//field.addFocusListener(focusAdapter);
		
	}
	
	public static void main(String[] args) {
		TestFrame tf = new TestFrame();
		tf.pack();
	//	tf.setSize(200,200);
		tf.setVisible(true);
	}
	
	
	class BasicWindowMonitor extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}
}