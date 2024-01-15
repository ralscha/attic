package ch.sr.applets;
import java.awt.*;

import javax.swing.*;

import com.bdnm.awt.*;

public class WaTorStatusPanel extends JPanel {
	JLabel chrononen;
	JLabel sharks;
	JLabel fishs;

	public WaTorStatusPanel(int f, int h) {

		setBorder( BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),
           		BorderFactory.createEmptyBorder(3, 3, 3, 3)));

		setLayout(new BorderLayout());
		JPanel p = new JPanel();
		FractionalLayout fLay = new FractionalLayout();
		p.setLayout(fLay);

		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		p1.setLayout(new GridLayout(3, 1));
		p2.setLayout(new GridLayout(3, 1));

		p1.add(new JLabel("Fische"));
		p2.add(fishs = new JLabel(String.valueOf(f), SwingConstants.RIGHT));
		p1.add(new JLabel("Haie"));
		p2.add(sharks = new JLabel(String.valueOf(h), SwingConstants.RIGHT));
		p1.add(new JLabel("Chrononen"));
		p2.add(chrononen = new JLabel("0",SwingConstants.RIGHT));

		fLay.setConstraint(p1, new FrameConstraint(0.0, 0, 0.0, 0, 0.7, 0, 1.0, 0));
		p.add(p1);
		fLay.setConstraint(p2, new FrameConstraint(0.7, 0, 0.0, 0, 1.0, 0, 1.0, 0));
		p.add(p2);
		add("Center", p);

	}

	public void setValues(int fish, int shark, int chron) {
		chrononen.setText(String.valueOf(chron));
		fishs.setText(String.valueOf(fish));
		sharks.setText(String.valueOf(shark));
	}

}