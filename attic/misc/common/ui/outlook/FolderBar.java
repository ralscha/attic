package common.ui.outlook;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;

public class FolderBar extends JPanel implements ActionListener {
	protected JLabel text;
	protected JLabel icon;

	public FolderBar(String label) {
		this(label, null);
	}

	public FolderBar(String label, Icon image) {

		if (image == null) {
			image = new ImageIcon(
          			Toolkit.getDefaultToolkit().createImage(getClass().getResource("view.gif")));
		}

		setOpaque(true);
		setBackground(Color.gray);
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(0, 7, 2, 7));
		add("Center", text = new JLabel(label));
		add("East", icon = new JLabel(image));
		text.setFont(new Font("Helvetica", Font.PLAIN, 24));
		text.setVerticalAlignment(JLabel.BOTTOM);
		text.setVerticalTextPosition(JLabel.BOTTOM);
		text.setForeground(Color.lightGray);
		icon.setVerticalAlignment(JLabel.CENTER);
	}

	public void setText(String string) {
		text.setText(string);
	}

	public void actionPerformed(ActionEvent event) {
		text.setText(event.getActionCommand());
	}
}