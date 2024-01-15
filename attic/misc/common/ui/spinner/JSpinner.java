package common.ui.spinner;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.*;

import java.util.*;

public class JSpinner extends JPanel implements ActionListener, KeyListener, SwingConstants {
	protected Vector listeners = new Vector();
	protected BasicArrowButton north, south;
	SpinModel model;

	public JSpinner(SpinModel model) {
		this.model = model;
		setLayout(new GridLayout(2, 1));
		setPreferredSize(new Dimension(16, 16));

		north = new BasicArrowButton(BasicArrowButton.NORTH);
		north.addActionListener(this);
		add(north);

		south = new BasicArrowButton(BasicArrowButton.SOUTH);
		south.addActionListener(this);
		add(south);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == north) {
			increment();
		}
		if (event.getSource() == south) {
			decrement();
		}
	}

	public void keyTyped(KeyEvent event) {}
	public void keyReleased(KeyEvent event) {}
	public void keyPressed(KeyEvent event) {
		int code = event.getKeyCode();
		if (code == KeyEvent.VK_UP) {
			increment();
		}
		if (code == KeyEvent.VK_DOWN) {
			decrement();
		}
		if (code == KeyEvent.VK_LEFT) {
			model.setPrevField();
		}
		if (code == KeyEvent.VK_RIGHT) {
			model.setNextField();
		}
	}

	protected void increment() {
		int fieldID = model.getActiveField();
		SpinRangeModel range = model.getRange(fieldID);
		range.setValueIsAdjusting(true);
		double value = range.getValue() + range.getExtent();
		if (value > range.getMaximum())
			value = range.getWrap() ? range.getMinimum() : range.getMaximum();
		range.setValue(value);
		model.setRange(fieldID, range);
		range.setValueIsAdjusting(false);
	}

	public void decrement() {
		int fieldID = model.getActiveField();
		SpinRangeModel range = model.getRange(fieldID);
		range.setValueIsAdjusting(true);
		double value = range.getValue() - range.getExtent();
		if (value < range.getMinimum())
			value = range.getWrap() ? range.getMaximum() : range.getMinimum();
		range.setValue(value);
		model.setRange(fieldID, range);
		range.setValueIsAdjusting(false);
	}
}