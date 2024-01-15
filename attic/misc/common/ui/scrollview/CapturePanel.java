package common.ui.scrollview;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.*;

public class CapturePanel extends JPanel {
	protected BufferedImage buffer;
	protected Component comp;
	protected Dimension size;
	protected Rectangle clip;
	protected boolean isDynamic = true;

	public CapturePanel(Component comp, boolean isDynamic) {
		this.comp = comp;
		this.isDynamic = isDynamic;
		setLayout(new BorderLayout());
		add(BorderLayout.CENTER, comp);
	}

	private void paintBuffer(Graphics gc, Rectangle clip) {
		Graphics g = buffer.getGraphics();
		g.setFont(gc.getFont());
		g.setColor(gc.getColor());
		g.setClip(clip);
		super.paint(g);
	}

	public void paint(Graphics gc) {
		int w = getSize().width;
		int h = getSize().height;
		if (buffer == null || !size.equals(comp.getPreferredSize())) {
			size = comp.getPreferredSize();
			buffer = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
			clip = new Rectangle(0, 0, size.width, size.height);
			if (!isDynamic)
				paintBuffer(gc, clip);
		}
		if (isDynamic)
			paintBuffer(gc, clip);

		gc.drawImage(buffer, 0, 0, this);
	}

	public boolean isValidateRoot() {
		return true;
	}

	public Image getImage() {
		return buffer;
	}
}
