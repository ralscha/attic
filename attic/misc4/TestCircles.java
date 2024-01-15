import java.math.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class TestCircles extends Applet implements Runnable {
	private static Font font = new Font("Dialog", Font.BOLD, 36);
	private static String str = "   Welcome   to   VisualAge";
	
	private static final double r = 0.851;
	private static final double ri = 0.688;
	private static final double D = 1.618;
	
	private Image offImg = null;
	private Graphics offG = null;
	private Thread runner;
	
	private boolean n = false;
	private boolean y = true;

	// Fix-Werte des Fünfecks
	private double dShalb;
	private double dSehne;
	private double lenge;
	private double dradkl;
	private double dradgr;
	private double daddY;
	private double dGross;
	private double dKlein;
	private double dDiff;
	private double dhDiff;

	//private Color dark;
	private int mitteX;
	private int mitteY;
	private int deckelHöhe;
	private int rumpfHöhe;
	private int radkl;
	private int radgr;
	private int Sehne;
	private int Shalb;
	private int addY;
	private int addX;
	private int Gross;
	private int Klein;
	private int Diff;
	private int hDiff;
	private int yDiff;
	private int diffKorX;
	private int diffKorY;
	private int lenx;
	private int leny;
	private int temp1, temp2, temp3, temp4, temp5;
	private int x1, x2, x3, x4, x5;
	private int ytemp, y1, y2, y3, y4, y5;
	private int[] arrx1 = new int[5];
	private int[] arry1 = new int[5];
	private int[] arrx2 = new int[5];
	private int[] arry2 = new int[5];
	private int[] arrx3 = new int[5];
	private int[] arry3 = new int[5];
	private int[] arrx4 = new int[5];
	private int[] arry4 = new int[5];

	// Position von  Welcome to VisualAge
	int xPos1;
	int yPos1;

	// Position von  kleinem Quadrat und kleinem Kreis
	int xPos2;
	int yPos2;

	// Länge und Breite von kleinemQuadrat
	int quWidth1;
	int quHeight1;

	// Länge und Breite von kleinem Kreis
	int krWidth1;
	int krHeight1;

	// Länge und Breite von grossem Kreis
	int krWidth2;
	int krHeight2;

	// Länge und Breite von grossem Quadrat
	int quWidth2;
	int quHeight2;

	// Position von grossem Quadrat und grossem Kreis
	int xPos3;
	int yPos3;
	
	private void neuBerechnen(int x, int y) {		
		if ((x >= 39) && (y >= 39)) {
			diffKorX = x - xPos3;
			diffKorY = y - yPos3;
	
			// Position von  Welcome anpassen
			xPos1 = xPos1 + diffKorX;
			yPos1 = yPos1 + diffKorY;
	
			// Position von kleinem Quadrat und kleinem Kreis anpassen
			xPos2 = xPos2 + diffKorX;
			yPos2 = yPos2 + diffKorY;
	
			// Position von grossem Quadrat und grossem Kreis anpassen
			xPos3 = xPos3 + diffKorX;
			yPos3 = yPos3 + diffKorY;
	
			// Alle x-Werte für erstes Fünfeck anpassen
	
			for (int i = 0; i < 5; i++) {			
				arrx1[i] = arrx1[i] + diffKorX;
				arry1[i] = arry1[i] + diffKorY;
				arrx2[i] = arrx2[i] + diffKorX;
				arry2[i] = arry2[i] + diffKorY;
				arrx3[i] = arrx3[i] + diffKorX;
				arry3[i] = arry3[i] + diffKorY;
				arrx4[i] = arrx4[i] + diffKorX;
				arry4[i] = arry4[i] + diffKorY;
			}
			
			clear();
			
			runner = new Thread(this);
			runner.start();
		}
	}

	
	public void init() {
		//super.init();
		addMouseListener(new MouseBearbeiter());

		// Position von  Welcome to VisualAge
		xPos1 = 200;
		yPos1 = 50;

		lenge = 240.0;
		lenx = 240;

		// start x / y -Koordinaten
		x1 = 320;
		y1 = 450;

		// Radius (gross und klein), Sehne und Durchmesser
		dradkl = ri * lenge;
		dKlein = dradkl * 2;
		dradgr = r * lenge;
		dGross = dradgr * 2;
		dDiff = dGross - dKlein;
		dhDiff = dDiff / 2;
		dSehne = D * lenge;
		dShalb = dSehne / 2;

		radkl = (int) dradkl;
		radgr = (int) dradgr;
		Gross = (int) dGross; // Grosser Durchmesser
		Klein = (int) dKlein; // Kleiner Durchmesser
		Diff = (int) dDiff;
		hDiff = (int) dhDiff;
		Sehne = (int) dSehne;
		Shalb = (int) dShalb;

		daddY = Math.sqrt((dradgr * dradgr) - (dShalb * dShalb));

		addY = (int) daddY;
		addX = addY;

		// Mittelpunkt bestimmen
		mitteX = x1 + (lenx / 2);
		mitteY = y1 - radkl;

		// Alle x-Werte für erstes Fünfeck berechnen

		x2 = x1 + lenx;
		x3 = x1 + (lenx / 2) + (Shalb);
		x4 = x1 + (lenx / 2);
		x5 = x3 - Sehne;

		arrx1[0] = x1;
		arrx1[1] = x2;
		arrx1[2] = x3;
		arrx1[3] = x4;
		arrx1[4] = x5;

		// Alle y-Werte für erstes Fünfeck berechnen

		y2 = y1;
		y3 = y1 - (radkl + addY);
		rumpfHöhe = y2 - y3;
		y4 = y1 - (radkl + radgr);
		deckelHöhe = y3 - y4;
		y5 = y3;

		arry1[0] = y1;
		arry1[1] = y2;
		arry1[2] = y3;
		arry1[3] = y4;
		arry1[4] = y5;

		// Länge und Breite von grossem Kreis und grossem Quadrat
		krWidth2 = Gross;
		krHeight2 = Gross;
		quWidth2 = Gross;
		quHeight2 = Gross;

		// Position von grossem Quadrat und grossem Kreis
		xPos3 = ((x1 + x2) / 2) - radgr;
		yPos3 = y4;

		// Länge und Breite von grossem Quadrat
		quWidth2 = (int) Math.sqrt(((quWidth1 * quWidth1) + (quHeight1 * quHeight1)));
		quHeight2 = krWidth2;

		// Länge und Breite von kleinem Kreis und kleinem Quadrat
		krWidth1 = Klein;
		krHeight1 = Klein;
		quWidth1 = Klein;
		quHeight1 = Klein;

		// Position von kleinem Quadrat und kleinem Kreis
		xPos2 = xPos3 + hDiff;
		yPos2 = yPos3 + hDiff;

		// Alle x-Werte für drittes Fünfeck berechnen

		temp1 = x1;
		temp2 = x2;
		temp3 = x3;
		temp4 = x4;
		temp5 = x5;

		x1 = temp4;
		x2 = temp3;
		x3 = temp2;
		x4 = temp1;
		x5 = temp5;

		arrx3[0] = x1;
		arrx3[1] = x2;
		arrx3[2] = x3;
		arrx3[3] = x4;
		arrx3[4] = x5;

		// Alle y-Werte für drittes Fünfeck berechnen

		yDiff = radgr - radkl;

		temp1 = y1;
		temp2 = y2;
		temp3 = y3;
		temp4 = y4;
		temp5 = y5;

		y1 = temp1 + yDiff;
		y2 = y1 - deckelHöhe;
		y3 = y1 - (radgr + radkl);
		y4 = y3;
		y5 = y2;

		arry3[0] = y1;
		arry3[1] = y2;
		arry3[2] = y3;
		arry3[3] = y4;
		arry3[4] = y5;

		// Alle x-Werte für zweites Fünfeck berechnen

		x1 = mitteX + radkl;
		x2 = x1;
		x3 = x2 - rumpfHöhe;
		x4 = x2 - (rumpfHöhe + deckelHöhe);
		x5 = x3;

		arrx2[0] = x1;
		arrx2[1] = x2;
		arrx2[2] = x3;
		arrx2[3] = x4;
		arrx2[4] = x5;

		// Alle y-Werte für zweites Fünfeck berechnen

		y1 = mitteY + (lenx / 2);
		y2 = mitteY - (lenx / 2);
		y3 = mitteY - (Sehne / 2);
		y4 = mitteY;
		y5 = mitteY + (Sehne / 2);

		arry2[0] = y1;
		arry2[1] = y2;
		arry2[2] = y3;
		arry2[3] = y4;
		arry2[4] = y5;

		// Alle x-Werte für viertes Fünfeck berechnen

		x1 = mitteX - radkl;
		x2 = x1 + rumpfHöhe;
		x3 = x2 + deckelHöhe;
		x4 = x2;
		x5 = x1;

		arrx4[0] = x1;
		arrx4[1] = x2;
		arrx4[2] = x3;
		arrx4[3] = x4;
		arrx4[4] = x5;

		// Alle y-Werte für viertes Fünfeck berechnen

		y1 = mitteY + (lenx / 2);
		y2 = mitteY + (Sehne / 2);
		y3 = mitteY;
		y4 = mitteY - (Sehne / 2);
		y5 = mitteY - (lenx / 2);

		arry4[0] = y1;
		arry4[1] = y2;
		arry4[2] = y3;
		arry4[3] = y4;
		arry4[4] = y5;

	}

	
	public void start() {
		createOffscreenImage();
		
		runner = new Thread(this);
		runner.start();
	}
	
	public void stop() {
		runner = null;
	}
	
	/**
	  * Ueberschreiben der run-Methode aus Runnable
	  */	  
	public void run() {
		for (int i = 0; (i < 5) && (Thread.currentThread() == runner); i++) {							
			offG.setColor(Color.blue);
			offG.drawString(str, xPos1, yPos1);
			offG.setColor(Color.green);
			//dark = offG.getColor().darker();
			//offG.setColor(dark);
			offG.fillRect(xPos3, yPos3, krWidth2, krHeight2);
			offG.setColor(Color.blue);
			//dark = offG.getColor().darker();
			//offG.setColor(dark);
			offG.fillOval(xPos3, yPos3, krWidth2, krHeight2);
			offG.setColor(Color.yellow);
			//offG.drawRect(xPos2,yPos2,krWidth1,krHeight1);
			offG.setColor(Color.red);
			switch (i % 4) {
				case 0:
					offG.fillPolygon(arrx1, arry1, 5);
					break;
				case 1 :
					offG.fillPolygon(arrx2, arry2, 5);
					break;
				case 2 :
					offG.fillPolygon(arrx3, arry3, 5);
					break;
				case 3 :
					offG.fillPolygon(arrx4, arry4, 5);
					break;
				default :;
			}
			offG.setColor(Color.yellow);
			offG.fillOval(xPos2, yPos2, krWidth1, krHeight1);
						
			repaint();
					
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {	}
			
		}
	}
	
	public void update(Graphics g) {
		paint(g);
	}
		
	public void paint(Graphics g) {		
		if (offImg != null) {
			g.drawImage(offImg, 0, 0, this);
		} 
	}
	
	public void clear() {
		offG.setColor(getBackground());
		offG.fillRect(0, 0, getSize().width, getSize().height);
		offG.setColor(Color.black);
		offG.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
		repaint();
	}
	

	private void createOffscreenImage() {
		if (getSize().width > 0) {
			offImg = createImage(getSize().width, getSize().height);
			offG = offImg.getGraphics();
			offG.setFont(font);
		}
	}
	
	
	class MouseBearbeiter extends MouseAdapter {
		/**
		  * Called when a mouse button has been pressed.
		  * @param e the received event
		  */
		public void mousePressed(MouseEvent e) {
			neuBerechnen(e.getX(), e.getY());
		}
	}
}