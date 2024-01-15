package common.ui.preview;

import java.awt.*;
import java.awt.print.*;
import javax.swing.*;
import javax.swing.border.*;

public class PreviewPanel extends JPanel {
	protected PreviewLayout layout;
	protected Dimension grid;
	protected double scale;

	public PreviewPanel(PrinterJob job, Printable printable) {
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(layout = new PreviewLayout(8, 8));
		try {
			int page = 0;
			while (true) {
				add(new PreviewPage(job, printable, page));
				page++;
			}
		} catch (PrinterException e) {}
		setBackground(Color.gray);
	}

	public Dimension getGrid() {
		return grid;
	}

	public void setGrid(Dimension grid) {
		this.grid = grid;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
		grid = null;
	}

	public void render(PageFormat format) {
		if (grid != null) {
			ExtendedInsets insets = new ExtendedInsets(getInsets());
			double width = insets.adjustWidth(getPreferredSize().width);
			double height = insets.adjustHeight(getParent().getSize().height);
			double w = (format.getWidth() + 12) * grid.width;
			double h = (format.getHeight() + 12) * grid.height;
			scale = width / w;
			if (scale * h > height)
				scale = height / h;
		}
		int count = getComponentCount();
		try {
			for (int i = 0; i < count; i++) {
				PreviewPage page = (PreviewPage) getComponent(i);
				page.render(format, scale);
			}
		} catch (PrinterException e) {}
		revalidate();
	}
}

