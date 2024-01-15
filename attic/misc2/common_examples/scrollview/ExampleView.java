package scrollview;


import java.awt.*;
import javax.swing.*;
import common.ui.scrollview.*;

public class ExampleView extends JPanel {
	public ExampleView(String filename) {
		setLayout(new BorderLayout());
		add(BorderLayout.NORTH, new JLabel("Any component can be used in the window"));
		add(BorderLayout.CENTER, new JLabel(new ImageIcon(filename)));
	}
}
