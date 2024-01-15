package preview;

import java.awt.*;
import java.awt.print.*;
import javax.swing.*;
import common.swing.*;
import common.ui.preview.*;

public class JPreviewTest {
	static class Example implements Printable {
		public int print(Graphics g, PageFormat format, int pageNumber) {
			int x = (int) format.getImageableX();
			int y = (int) format.getImageableY();
			int w = (int) format.getImageableWidth();
			int h = (int) format.getImageableHeight();
			g.setColor(new Color(223, 223, 223));
			g.fillRect(x, y, w, h);
			g.setColor(Color.black);
			g.drawString("This is test page # " + pageNumber, x + 20, y + 20);

			if (pageNumber > 7)
				return NO_SUCH_PAGE;
			else
				return PAGE_EXISTS;
		}
	}

	public static void main(String[] args) {
		PlafPanel.setNativeLookAndFeel(true);

		JFrame frame = new JFrame("JPreview Test");
		frame.setDefaultCloseOperation(3);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(BorderLayout.CENTER,
                           		new JPreview(PrinterJob.getPrinterJob(), new JPreviewTest.Example()));
		frame.setSize(800, 600);
		frame.setVisible(true);
	}
}

