package common.ui.preview;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.print.*;
import javax.swing.*;

public class PreviewPage extends JPanel {
	protected PrinterJob job;
	protected Printable printable;
	protected int page;
	protected BufferedImage buffer;

	public PreviewPage(PrinterJob job, Printable printable, int page) throws PrinterException {
		this.job = job;
		this.printable = printable;
		this.page = page;
		setOpaque(false);
		setBackground(Color.white);
		setBorder(new PreviewBorder());
		render(job.defaultPage(), 0.1);
	}

	public void render(PageFormat format, double scale) throws PrinterException {
		int w = (int) format.getWidth();
		int h = (int) format.getHeight();
		int ww = (int)(format.getWidth() * scale);
		int hh = (int)(format.getHeight() * scale);

		Insets insets = getInsets();
		setPreferredSize( new Dimension(ww + insets.left + insets.right,
                                		hh + insets.top + insets.bottom));

		buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) buffer.getGraphics();
		g.scale(scale, scale);
		g.setColor(Color.white);
		g.fillRect(0, 0, w, h);

		if (printable.print(g, format, page) == Printable.NO_SUCH_PAGE)
			throw new PrinterException("No Such Page");
	}

	public void paintComponent(Graphics g) {
		Insets insets = getInsets();
		g.setColor(getBackground());
		int w = getSize().width;
		int h = getSize().height;
		g.fillRect(insets.left, insets.top, w - insets.left - insets.right,
           		h - insets.top - insets.bottom);
		if (buffer != null) {
			g.drawImage(buffer, insets.left, insets.top, this);
		}
	}
}

