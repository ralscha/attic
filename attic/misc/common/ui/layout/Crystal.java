package common.ui.layout;

import java.awt.Color;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class Crystal extends JPanel {
	public static final int ON = 1;
	public static final int OFF = 0;
	public static final int SPECIAL = 2;

	int state = ON;
	Color color;

	public Crystal(Color color) {
		super();
		this.color = color;
	}

	public void paint(Graphics g) {
		int w = getSize().width - 1;
		int h = getSize().height - 1;
		int arc = Math.min(w, h) / 2;

		g.setColor(color.darker());
		g.fillRoundRect(0, 0, w, h, arc, arc);
		g.setColor(color);
		g.drawRoundRect(0, 0, w - 1, h - 1, arc, arc);
	}

	private static JPanel blankPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.black);
		return panel;
	}

	public static JPanel digitPanel(char num) {
		JPanel panel = new JPanel();
		int[] rows = {1, 5, 1, 5, 1};
		int[] cols = {1, 5, 1};
		panel.setBackground(Color.black);
		panel.setForeground(Color.black);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(new ProportionLayout(rows, cols));

		Color on = Color.green;
		Color off = on.darker().darker().darker().darker().darker();
		panel.add(blankPanel());
		panel.add(new Crystal(("02356789".indexOf(num) > -1) ? on : off));
		panel.add(blankPanel());
		panel.add(new Crystal(("045689".indexOf(num) > -1) ? on : off));
		panel.add(blankPanel());
		panel.add(new Crystal(("01234789".indexOf(num) > -1) ? on : off));
		panel.add(blankPanel());
		panel.add(new Crystal(("2345689".indexOf(num) > -1) ? on : off));
		panel.add(blankPanel());
		panel.add(new Crystal(("02689".indexOf(num) > -1) ? on : off));
		panel.add(blankPanel());
		panel.add(new Crystal(("013456789".indexOf(num) > -1) ? on : off));
		panel.add(blankPanel());
		panel.add(new Crystal(("0235689".indexOf(num) > -1) ? on : off));
		panel.add(blankPanel());

		return panel;
	}

	public static JPanel colonPanel() {
		JPanel panel = new JPanel();
		int[] cols = {1};
		int[] rows = {3, 1, 3, 1, 3};
		panel.setBackground(Color.black);
		panel.setForeground(Color.black);
		panel.setBorder(new EmptyBorder(2, 2, 2, 2));
		panel.setLayout(new ProportionLayout(rows, cols));

		Color color = Color.green;
		panel.add(blankPanel());
		panel.add(new Crystal(color));
		panel.add(blankPanel());
		panel.add(new Crystal(color));
		panel.add(blankPanel());

		return panel;
	}

	public static JPanel outlinePanel() {
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder());
		panel.setPreferredSize(new Dimension(15, 15));
		return panel;
	}

	public static void main(String[] args) {
		JPanel number = new JPanel();
		number.setLayout(new ScalingLayout(1, 21));
		number.setBackground(Color.black);
		number.setForeground(Color.black);
		number.add(digitPanel('1'), new Rectangle(0, 0, 5, 1));
		number.add(digitPanel('2'), new Rectangle(5, 0, 5, 1));
		number.add(colonPanel(), new Rectangle(10, 0, 1, 1));
		number.add(digitPanel('3'), new Rectangle(11, 0, 5, 1));
		number.add(digitPanel('4'), new Rectangle(16, 0, 5, 1));

		JPanel outline = new JPanel();
		outline.setLayout(new CastleLayout());
		outline.add(outlinePanel(), "NorthEast");
		outline.add(outlinePanel(), "NorthWest");
		outline.add(outlinePanel(), "SouthEast");
		outline.add(outlinePanel(), "SouthWest");
		outline.add(outlinePanel(), "North");
		outline.add(outlinePanel(), "South");
		outline.add(outlinePanel(), "East");
		outline.add(outlinePanel(), "West");
		outline.add(number, "Center");

		JFrame frame = new JFrame("Numbers Panel");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(outline, "Center");
		frame.setBounds(0, 0, 400, 200);
		frame.show();
	}

}