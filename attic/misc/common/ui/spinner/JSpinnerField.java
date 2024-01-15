package common.ui.spinner;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.text.*;
import java.util.*;

public class JSpinnerField extends JPanel implements ChangeListener, MouseListener,
FocusListener {
	protected SpinModel model;
	protected SpinField spinField;
	protected SpinRenderer renderer;
	protected Format formatter;
	protected boolean wrap = true;
	protected boolean hasFocus = false;

	public JSpinnerField() {}

	public JSpinnerField(int value, int extent, int min, int max, boolean wrap) {
		init(new DefaultSpinModel(value, extent, min, max, wrap),
     		new DefaultSpinRenderer(), NumberFormat.getInstance(), wrap);
		refreshSpinView();
	}

	public JSpinnerField(SpinModel model, SpinRenderer renderer, Format formatter,
                     	boolean wrap) {
		init(model, renderer, formatter, wrap);
		refreshSpinView();
	}

	protected void init(SpinModel model, SpinRenderer renderer, Format formatter,
                    	boolean wrap) {
		this.model = model;
		this.renderer = renderer;
		this.formatter = formatter;
		this.wrap = wrap;
		spinField = new SpinField(this);
		setLayout(new BorderLayout());
		add(BorderLayout.CENTER, spinField);
		setBorder(spinField.getBorder());
		spinField.setBorder(null);
		JSpinner spinner = new JSpinner(model);
		addKeyListener(spinner);
		addMouseListener(this);
		addFocusListener(this);
		model.addChangeListener(this);
		add(BorderLayout.EAST, spinner);
	}

	public void setLocale(Locale locale) {
		formatter = NumberFormat.getInstance(locale);
		updateFieldOrder();
	}

	public void updateFieldOrder() {
		if (spinField.getValue() == null)
			return;
		int[] fieldIDs = model.getFieldIDs();
		LocaleUtil.sortFieldOrder(formatter, spinField.getValue(), fieldIDs);
		model.setFieldIDs(fieldIDs);
	}

	public SpinRenderer getRenderer() {
		return renderer;
	}

	protected void refreshSpinView() {
		int fieldID = model.getActiveField();
		SpinRangeModel range = model.getRange(fieldID);
		spinField.setValue(new Double(range.getValue()));
	}

	public void stateChanged(ChangeEvent event) {
		requestFocus();
		refreshSpinView();
		repaint();
	}

	public void mouseClicked(MouseEvent event) {
		int fieldID = LocaleUtil.findMouseInField(getGraphics().getFontMetrics(), event.getX(),
              		formatter, spinField.getValue(), model.getFieldIDs());
		model.setActiveField(fieldID);
		requestFocus();
		refreshSpinView();
	}
	public void mousePressed(MouseEvent event) {}
	public void mouseReleased(MouseEvent event) {}
	public void mouseEntered(MouseEvent event) {}
	public void mouseExited(MouseEvent event) {}

	public void focusGained(FocusEvent event) {
		hasFocus = true;
		repaint();
	}

	public void focusLost(FocusEvent event) {
		hasFocus = false;
		repaint();
	}

	public boolean isFocusTraversable() {
		return true;
	}
}