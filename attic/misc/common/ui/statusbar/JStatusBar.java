package common.ui.statusbar;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import common.ui.borders.ThreeDBorder;

public class JStatusBar extends JPanel implements StatusConstants {
	public JStatusBar() {
		setLayout(new StatusLayout(2, 2));
		setBorder(new CompoundBorder(new ThreeDBorder(), new EmptyBorder(2, 2, 2, 2)));
	}

	public void addElement(Component component) {
		add(component, new StatusConstraint());
	}

	public void addElement(Component component, int width) {
		add(component, new StatusConstraint(width));
	}

	public void addElement(Component component, boolean relative) {
		add(component, new StatusConstraint(relative));
	}

	public void addElement(Component component, boolean relative, float width) {
		add(component, new StatusConstraint(relative, width));
	}
}

