
package common.ui.configure;

import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class TitleBar extends JLabel {
	public TitleBar(String text, Icon icon) {
		super(text, icon, JLabel.LEFT);
		setFont(new Font(getFont().getName(), Font.PLAIN, 16));
		setPreferredSize(new Dimension(30, 30));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setBackground(Color.gray);
		setForeground(Color.white);
		setOpaque(true);
	}
}