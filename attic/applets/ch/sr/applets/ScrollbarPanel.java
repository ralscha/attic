package ch.sr.applets;
import java.awt.*;

import com.bdnm.awt.*;


public class ScrollbarPanel extends Panel {

	Scrollbar scrollbar[] = new Scrollbar[10];
	NumberCanvas sblabel[] = new NumberCanvas[10];
	Scrollbar horz;
	double scrolls[] = new double[Global.sb];
	int scrollValues[] = new int[Global.sb];
	Panel lp, sp;
	DrawCanvas drawer;
	int start;
	Label tlabel;

	public ScrollbarPanel(String title, DrawCanvas draw) {

		FractionalLayout fLay = new FractionalLayout();
		setLayout(fLay);

		lp = new Panel();
		sp = new Panel();
		lp.setLayout(new GridLayout(1, 10, 0, 0));
		sp.setLayout(new GridLayout(1, 10, 0, 0));

		drawer = draw;

		for (int i = 0; i < 10; i++) {
			scrollbar[i] = new Scrollbar(Scrollbar.VERTICAL, 100, 1, 0, 101);
			sblabel[i] = new NumberCanvas(String.valueOf(i + 1));

			lp.add(sblabel[i]);
			sp.add(scrollbar[i]);
		}

		fLay.setConstraint(lp, new FrameConstraint(0.0, 0, 0.08, 0, 1.0, 0, 0.15, 0));
		add(lp);

		fLay.setConstraint(sp, new FrameConstraint(0.0, 0, 0.16, 0, 1.0, 0, 0.93, 0));
		add(sp);

		for (int i = 0; i < Global.sb; i++)
			scrollValues[i] = 100;

		tlabel = new Label(title, Label.CENTER);
		fLay.setConstraint(tlabel, new FrameConstraint(0.0, 0, 0.0, 0, 1.0, 0, 0.08, 0));
		add(tlabel);

		horz = new Scrollbar(Scrollbar.HORIZONTAL, 0, 0, 0, Global.sb - 10+1);
		fLay.setConstraint(horz, new FrameConstraint(0.0, 0, 0.93, 0, 1.0, 0, 1.0, 0));
		add(horz);
		setVisible(true);
	}


	public void resetScrollbars() {
		for (int i = 0; i < 10; i++)
			scrollbar[i].setValue(100);

		for (int i = 0; i < Global.sb; i++)
			scrollValues[i] = 100;

		drawer.reset();
	}

}