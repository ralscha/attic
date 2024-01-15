package common.ui.outlook;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class RolloverButton extends JButton implements MouseListener {
	public RolloverButton(String name) {
		this(name, null);
	}

	public RolloverButton(String name, Icon icon) {
		super(name, icon);

		if (icon == null) {
			icon = new ImageIcon(
         			Toolkit.getDefaultToolkit().createImage(getClass().getResource("view.gif")));
			setIcon(icon);
		}

		setOpaque(true);
		setBackground(Color.gray);
		setForeground(Color.white);
		setMargin(new Insets(2, 2, 2, 2));
		setBorderPainted(false);
		setFocusPainted(false);
		setVerticalAlignment(TOP);
		setHorizontalAlignment(CENTER);
		setVerticalTextPosition(BOTTOM);
		setHorizontalTextPosition(CENTER);
		addMouseListener(this);
	}

	public boolean isDefaultButton() {
		return false;
	}

	public void mouseEntered(MouseEvent event) {
		setBorderPainted(true);
		setForeground(Color.black);
		setBackground(Color.lightGray);
		repaint();
	}

	public void mouseExited(MouseEvent event) {
		setBorderPainted(false);
		setForeground(Color.white);
		setBackground(Color.gray);
		repaint();
	}

	public void mouseEnter(MouseEvent event) {}
	public void mousePressed(MouseEvent event) {}
	public void mouseClicked(MouseEvent event) {}
	public void mouseReleased(MouseEvent event) {}

}