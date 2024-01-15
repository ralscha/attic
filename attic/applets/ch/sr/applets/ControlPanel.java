package ch.sr.applets;

import java.awt.*;
import java.awt.event.*;

import com.bdnm.awt.*;

public class ControlPanel extends Panel {
	DrawCanvas drawer;
	ScrollbarPanel sinp;
	ScrollbarPanel cosp;
	Scrollbar start;
	Scrollbar end;
	Scrollbar yzoomScrollbar;
	TextField yzoom;
	Label startText;
	Label endText;
	Button reset, about, change;
	int z;
	int mode = 1;
	Frame parent;
	AboutDialog aboutDlg = null;
	Panel p, bp, pl;

	final double div = 100.0 / (Math.PI * 2);

	public ControlPanel(DrawCanvas _drawer, ScrollbarPanel _sinp, ScrollbarPanel _cosp,
                    	Frame parent) {

		this.parent = parent;
		this.drawer = _drawer;
		this.sinp = _sinp;
		this.cosp = _cosp;

		p = new Panel();
		pl = new Panel();
		bp = new Panel();

		FractionalLayout fLay = new FractionalLayout();
		setLayout(fLay);

		start = new Scrollbar(Scrollbar.HORIZONTAL, 0, 0, 0, 101);
		end = new Scrollbar(Scrollbar.HORIZONTAL, 100, 0, 0, 101);

		AdjustmentListener adjListener = new AdjustmentListener() {
                                 			public void adjustmentValueChanged(AdjustmentEvent e) {
                                 				processAdjustment();
                                 			}
                                 		};

		start.addAdjustmentListener(adjListener);
		end.addAdjustmentListener(adjListener);

		yzoomScrollbar = new Scrollbar(Scrollbar.HORIZONTAL, 1, 0, 1, 21);
		yzoomScrollbar.addAdjustmentListener(new AdjustmentListener() {
                                     			public void adjustmentValueChanged(AdjustmentEvent e) {
                                     				int v = yzoomScrollbar.getValue();
                                     				yzoomScrollbar.setValue(v);
                                     				yzoom.setText(String.valueOf(v));
                                     				drawer.setZoom(v);
                                     			}
                                     		}
                                    		);


		yzoom = new TextField(1);
		yzoom.setText("1");
		reset = new Button("Reset");
		about = new Button("About");
		change = new Button("Change");

		yzoom.addActionListener(new ActionListener() {
                        			public void actionPerformed(ActionEvent ae) {
                    						try {
                        					z = Integer.parseInt(yzoom.getText());
                    							if (z > 20) {
                        						z = 20;
                        						yzoom.setText(String.valueOf(z));
                        					}
                    							if (z < 1) {
                        						z = 1;
                        						yzoom.setText(String.valueOf(z));
                        					}
                        					yzoomScrollbar.setValue(z);
                        					drawer.setZoom(z);
                    						} catch (NumberFormatException e) {}
                        			}
                        		}
                       		);

		reset.addActionListener(new ActionListener() {
                        			public void actionPerformed(ActionEvent ae) {
                        				sinp.resetScrollbars();
                        				cosp.resetScrollbars();
                        				start.setValue(0);
                        				end.setValue(100);
                        				drawer.setRange(0, Math.PI * 2);
                        			}
                        		}
                       		);

		change.addActionListener(new ActionListener() {
                         			public void actionPerformed(ActionEvent ae) {
                     				if (mode == 1) {
                         					drawer.setMode(2);
                         					mode = 2;
                     				} else {
                         					drawer.setMode(1);
                         					mode = 1;
                         				}
                         			}
                         		}
                        		);

		about.addActionListener(new ActionListener() {
                        			public void actionPerformed(ActionEvent ae) {
                        				showDialog();
                        			}
                        		}
                       		);

		p.setLayout(new GridLayout(1, 2, 0, 0));
		startText = new Label("0.0", Label.LEFT);
		endText = new Label("2*PI", Label.RIGHT);
		p.add(startText);
		p.add(endText);

		fLay.setConstraint(p, new FrameConstraint(0.0, 0, 0.0, 0, 1.0, 0, 0.0, 10));
		add(p);

		pl.setLayout(new GridLayout(2, 1, 0, 0));
		pl.add(start);
		pl.add(end);

		fLay.setConstraint(pl, new FrameConstraint(0.0, 0, 0.0, 15, 1.0, 0, 0.0, 41));
		add(pl);

		fLay.setConstraint(yzoom, new OriginConstraint(0.05, 0, 0.0, 48));
		add(yzoom);

		fLay.setConstraint(yzoomScrollbar,
                   		new FrameConstraint(0.10, 2, 0.0, 50, 0.4, 0, 0.0, 66));
		add(yzoomScrollbar);

		bp.setLayout(new FlowLayout());
		bp.add(reset);
		bp.add(change);
		bp.add(about);

		fLay.setConstraint(bp, new FrameConstraint(0.5, 0, 0.0, 44, 1.0, 0, 1.0, -5));
		add(bp);

		setVisible(true);
	}

	void processAdjustment() {
		double h1, h2;
		int v1, v2;

		v1 = start.getValue();
		v2 = end.getValue();
		start.setValue(v1);
		end.setValue(v2);
		h1 = v1 / div;
		h2 = v2 / div;

		if (h1 < h2) {
			if (v1 == start.getMaximum() - 1)
				startText.setText("2*PI");
			else
				startText.setText(String.valueOf(h1));

			if (v2 == end.getMaximum() - 1)
				endText.setText("2*PI");
			else
				endText.setText(String.valueOf(h2));

			drawer.setRange(h1, h2);
		} else {
			if (v1 == start.getMaximum() - 1)
				endText.setText("2*PI");
			else
				endText.setText(String.valueOf(h1));

			if (v2 == end.getMaximum() - 1)
				startText.setText("2*PI");
			else
				startText.setText(String.valueOf(h2));


			drawer.setRange(h2, h1);
		}
	}
	
	void showDialog() {
		if (aboutDlg == null) {
			aboutDlg = new AboutDialog(parent, "About Harmony");
		}
		aboutDlg.setVisible(true);
	}
}