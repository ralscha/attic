package statusbar;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import common.ui.statusbar.*;
import common.ui.borders.*;

public class JStatusBarTest extends JPanel implements StatusConstants {
	protected static JComponent newLabel(String text) {
		return new StatusContainer(new JLabel(text));
	}

	protected static JComponent newProgressBar() {
		JProgressBar bar = new JProgressBar();
		bar.setBorder(new LineBorder(Color.black, 1));
		bar.setPreferredSize(new Dimension(120, 10));
		bar.setValue(50);

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder( new CompoundBorder(new ThreeDBorder(ThreeDBorder.LOWERED),
                                    		new EmptyBorder(2, 2, 2, 2)));
		panel.add(BorderLayout.CENTER, bar);
		return panel;
	}

	public static void main(String[] args) {
		common.swing.PlafPanel.setNativeLookAndFeel(true);

		JStatusBar status = new JStatusBar();

		// Relative, twice normal
		status.addElement(newLabel(" Relative x2 "), RELATIVE, 2);
		// Relative normal
		status.addElement(newLabel(" Relative x1 "), RELATIVE, 1);
		// Fixed, based on preferred size
		status.addElement(newLabel(" Preferred "));
		// Fixed, based on specified size
		status.addElement(newLabel(" Fixed "), FIXED, 40);
		// Relative normal
		status.addElement(newLabel(" Relative x1 "), RELATIVE);
		// Progress bar is based on preferred size
		status.addElement(newProgressBar());

		JFrame frame = new JFrame("JStatusBar Test");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(BorderLayout.SOUTH, status);
		frame.setSize(700, 150);
		frame.show();
	}
}

