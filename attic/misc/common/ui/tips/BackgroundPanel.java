package common.ui.tips;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
	public BackgroundPanel() {
		super();
		setOpaque(true);
	}

	public void paintComponent(Graphics g) {
		int w = getSize().width - 1;
		int h = getSize().height - 1;
		for (int y = 0; y < h; y += 10) {
			g.setColor(Color.blue);
			g.fillRect(0, y, w, y + 5);
			g.setColor(Color.blue.darker());
			g.fillRect(0, y + 5, w, y + 10);
		}
	}
}

