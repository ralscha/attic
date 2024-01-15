package borders;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import common.ui.borders.*;

public class JBorderPatterns extends JPanel {
	private static final int T = 9;
	protected Object[] borders = { JBorder.createPatternBorder(PatternBorder.DEFAULT, Color.black, T), "DEFAULT",
                               	JBorder.createPatternBorder(PatternBorder.CROSS, Color.black, T), "CROSS",
                               	JBorder.createPatternBorder(PatternBorder.VERT, Color.black, T), "VERT",
                               	JBorder.createPatternBorder(PatternBorder.HORZ, Color.black, T), "HORZ",
                               	JBorder.createPatternBorder(PatternBorder.GRID, Color.black, T), "GRID",
                               	JBorder.createPatternBorder(PatternBorder.ROUND, Color.black, T), "ROUND",
                               	JBorder.createPatternBorder(PatternBorder.NW_TRIANGLE, Color.black, T), "NW_TRIANGLE",
                               	JBorder.createPatternBorder(PatternBorder.NE_TRIANGLE, Color.black, T), "NE_TRIANGLE",
                               	JBorder.createPatternBorder(PatternBorder.SW_TRIANGLE, Color.black, T), "SW_TRIANGLE",
                               	JBorder.createPatternBorder(PatternBorder.SE_TRIANGLE, Color.black, T), "SE_TRIANGLE",
                               	JBorder.createPatternBorder(PatternBorder.DIAMOND, Color.black, T), "DIAMOND",
                               	JBorder.createPatternBorder(PatternBorder.DOTS, Color.black, T), "DOTS",
                               	JBorder.createPatternBorder(PatternBorder.DOT, Color.black, T), "DOT",
                               	JBorder.createPatternBorder(PatternBorder.SLASH, Color.black, T), "SLASH",
                               	JBorder.createPatternBorder(PatternBorder.BACKSLASH, Color.black, T), "BACKSLASH",
                               	JBorder.createPatternBorder(PatternBorder.THICK_VERT, Color.black, T), "THICK_VERT",
                               	JBorder.createPatternBorder(PatternBorder.THICK_HORZ, Color.black, T), "THICK_HORZ",
                               	JBorder.createPatternBorder(PatternBorder.THICK_GRID, Color.black, T), "THICK_GRID",
                               	JBorder.createPatternBorder(PatternBorder.THICK_SLASH, Color.black, T), "THICK_SLASH",
                               	JBorder.createPatternBorder(PatternBorder.THICK_BACKSLASH, Color.black, T), "THICK_BACKSLASH" };

	public JBorderPatterns() {
		int size = borders.length;
		setLayout(new GridLayout(5, 4, 5, 5));
		setBorder(JBorder.createEmptyBorder(5));
		for (int i = 0; i < size; i += 2) {
			add(makeLabel((Border) borders[i], (String) borders[i + 1]));
		}
	}

	private JLabel makeLabel(Border border, String name) {
		JLabel label = new JLabel();
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBorder(border);
		label.setOpaque(true);
		label.setText(name);
		return label;
	}

	public static void main(String[] args) {
		common.swing.PlafPanel.setNativeLookAndFeel(true);

		JFrame frame = new JFrame("Pattern Border Examples");
		frame.setBounds(50, 50, 600, 300);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(BorderLayout.CENTER, new JBorderPatterns());
		frame.setDefaultCloseOperation(3);
		frame.show();
	}
}
