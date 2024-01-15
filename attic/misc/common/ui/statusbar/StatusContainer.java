package common.ui.statusbar;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import common.ui.borders.ThreeDBorder;

public class StatusContainer extends JPanel implements StatusConstants {
	public StatusContainer(Component component) {
		setBorder(new ThreeDBorder(ThreeDBorder.LOWERED));
		setLayout(new BorderLayout());
		add(BorderLayout.CENTER, component);
	}
}

