package common.ui.preview;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import javax.swing.*;

public class JPreview extends JPanel {
	protected PreviewPanel panel;

	public JPreview(PrinterJob job, Printable printable) {
		setLayout(new BorderLayout());
		panel = new PreviewPanel(job, printable);
		add(BorderLayout.CENTER,
    		new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		add(BorderLayout.NORTH, new PreviewToolbar(job, panel));
	}

	public Dimension getGrid() {
		return panel.getGrid();
	}

	public void setGrid(Dimension grid) {
		panel.setGrid(grid);
	}

	public double getScale() {
		return panel.getScale();
	}

	public void setScale(double scale) {
		panel.setScale(scale);
	}
}

