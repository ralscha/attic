
package common.ui.calendar;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;

public class ArrowButton extends BasicArrowButton {
	public ArrowButton(int direction) {
		super(direction);
	}

	public void paint(Graphics g) {
		Color origColor;
		boolean isPressed, isEnabled;
		int w, h, size;

		w = getSize().width;
		h = getSize().height;
		origColor = g.getColor();
		isPressed = getModel().isPressed();
		isEnabled = isEnabled();

		g.setColor(getBackground());
		g.fillRect(1, 1, w - 2, h - 2);

		/// Draw the proper Border
		if (isPressed) {
			g.setColor(UIManager.getColor("controlShadow"));
			g.drawRect(0, 0, w - 1, h - 1);
		} else {
			g.setColor(UIManager.getColor("controlLtHighlight"));
			g.drawLine(0, 0, 0, h - 1);
			g.drawLine(1, 0, w - 2, 0);

			g.setColor(UIManager.getColor("controlShadow"));
			g.drawLine(0, h - 1, w - 1, h - 1);
			g.drawLine(w - 1, h - 1, w - 1, 0);
		}

		// If there's no room to draw arrow, bail
		if (h < 5 || w < 5) {
			g.setColor(origColor);
			return;
		}

		if (isPressed) {
			g.translate(1, 1);
		}

		// Draw the arrow
		size = Math.min((h - 4) / 3, (w - 4) / 3);
		size = Math.max(size, 2);
		paintTriangle(g, (w - size) / 2, (h - size) / 2, size, direction, isEnabled);

		// Reset the Graphics back to it's original settings
		if (isPressed) {
			g.translate(-1, -1);
		}
		g.setColor(origColor);
	}
}