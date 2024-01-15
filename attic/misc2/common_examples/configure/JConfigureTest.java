package configure;

import java.io.IOException;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import common.ui.configure.*;

public class JConfigureTest extends JPanel implements ActionListener {
	protected JButton ok, cancel;
	protected JConfigure configure;

	public JConfigureTest() throws IOException {
		setLayout(new BorderLayout());
		configure = new JConfigure("JConfigure.properties");
		add("Center", configure);

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 2));
		buttons.add(buttonPanel(ok = new JButton("OK")));
		buttons.add(buttonPanel(cancel = new JButton("Cancel")));
		ok.addActionListener(this);
		cancel.addActionListener(this);

		JPanel south = new JPanel();
		south.setBorder(new EdgeBorder(EdgeBorder.NORTH));
		south.setLayout(new BorderLayout());
		south.add("East", buttons);
		add("South", south);
	}

	private JPanel buttonPanel(JButton button) {
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(4, 4, 4, 4));
		panel.setLayout(new BorderLayout());
		panel.add("Center", button);
		return panel;
	}

	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == ok) {
			try {
				configure.save();
			} catch (IOException e) {}
			setVisible(false);
			System.exit(0);
		}
		if (source == cancel) {
			setVisible(false);
			System.exit(0);
		}
	}

	public static void main(String[] args) throws IOException {
		common.swing.PlafPanel.setNativeLookAndFeel(false);
		JFrame frame = new JFrame("JConfigure Test");
		frame.setBounds(100, 100, 500, 360);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add("Center", new JConfigureTest());
		frame.setDefaultCloseOperation(3);
		frame.show();
	}
}