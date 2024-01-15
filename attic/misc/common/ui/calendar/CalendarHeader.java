package common.ui.calendar;


import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class CalendarHeader extends JPanel {
	protected CalendarRenderer renderer;
	protected CellRendererPane renderPane = new CellRendererPane();

	private static final String header[] = {"S", "M", "T", "W", "T", "F", "S"};

	protected double xunit = 0, yunit = 0;
	protected int first, days, current = 1;

	public CalendarHeader(CalendarRenderer renderer) {
		this.renderer = renderer;
		setLayout(new BorderLayout());
		add(BorderLayout.CENTER, renderPane);
	}

	public void paintComponent(Graphics g) {
		xunit = getSize().width / 7;
		yunit = getSize().height;
		for (int x = 0; x < 7; x ++) {
			drawCell(g, (int)(x * xunit), 0, (int) xunit, (int) yunit, header[x], false);
		}
	}

	private void drawCell(Graphics g, int x, int y, int w, int h, String text,
                      	boolean selected) {
		Component render = renderer. getCalendarRendererComponent(this, text, selected, false);
		renderPane.paintComponent(g, render, this, x, y, w, h);
	}

	public boolean isFocusTraversable() {
		return false;
	}

	public Dimension getPreferredSize() {
		Dimension dimension = ((Component) renderer).getPreferredSize();
		int width = dimension.width * 7;
		int height = dimension.height;
		return new Dimension(width, height);
	}

	public Dimension getMinimumSize() {
		Dimension dimension = ((Component) renderer).getMinimumSize();
		int width = dimension.width * 7;
		int height = dimension.height;
		return new Dimension(width, height);
	}
}