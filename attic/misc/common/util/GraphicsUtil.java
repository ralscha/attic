package common.util;

import java.awt.*;
import java.io.*;
import Acme.JPM.Encoders.GifEncoder;

public class GraphicsUtil {

	public static void snapshot(Component comp, String fileName) {
		try {
			Image img = comp.createImage(comp.getSize().width, comp.getSize().height);
			Graphics g = img.getGraphics();

			comp.paintAll(g);

			OutputStream os = new BufferedOutputStream(new FileOutputStream(fileName));
			GifEncoder ge = new GifEncoder(img, os);
			ge.encode();
			os.close();
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

  public static void snapshot(Image img, String fileName) throws IOException {
    OutputStream os = new BufferedOutputStream(new FileOutputStream(fileName));
		GifEncoder ge = new GifEncoder(img, os);
		ge.encode();
		os.close();    
  }
}