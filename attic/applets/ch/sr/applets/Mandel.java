package ch.sr.applets;
import java.awt.*;
import java.awt.event.*;

public class Mandel extends Frame {
	boolean inAnApplet = true;

	Label state;
	MandelZoomCanvas mandel;
	JuliaCanvas julia;
	TrainCanvas train;
	BlackMandel bm;

	public Mandel() {
		super();

		addWindowListener(new Exiter());

		state = new Label("Status");
		mandel = new MandelZoomCanvas(state);

		mandel.addMouseListener(new MouseAdapter() {
                        			public void mouseClicked(MouseEvent me) {
                        				processMandelClick(me);
                        			}
                        		}
                       		);


		julia = new JuliaCanvas();
		bm = new BlackMandel();
		train = new TrainCanvas(bm);
		setLayout(new BorderLayout());
		Panel p = new Panel();
		p.setLayout(new GridLayout(2, 2, 5, 5));
		p.add(mandel);
		p.add(julia);
		p.add(train);
		add("Center", p);

		add("South", state);

		setSize(400, 400);
		setVisible(true);
		mandel.start();
		julia.start(-0.444, 0.1214);

	}


	void processMandelClick(MouseEvent evt) {

		double xd, yd;
		

		xd = mandel.getClickX();
		yd = mandel.getClickY();

		if (evt.isShiftDown()) {
			julia.stop();

			julia.start(xd, yd);
		} else {
			train.stop();

			train.start(xd, yd, -2.1,
            			mandel.getrmax(- 2.1, - 1.4,  1.4), -1.4, 1.4);
		}

	}

	class Exiter extends WindowAdapter {
		public void windowClosing(WindowEvent we) {
			mandel.stop();
			julia.stop();
			if (inAnApplet) {
				dispose();
			} else {
				System.exit(0);
			}
		}
	}



	public static void main(String args[]) {
		Mandel m = new Mandel();
		m.inAnApplet = false;
		m.setTitle("Mandelbrot & Julia");


	}
}