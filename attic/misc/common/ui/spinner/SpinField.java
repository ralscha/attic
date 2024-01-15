package common.ui.spinner;

import java.awt.*;
import javax.swing.*;
import java.text.*;

public class SpinField extends JPanel {
	protected CellRendererPane pane;
	protected JSpinnerField field;
	protected Object value;

	public SpinField(JSpinnerField field) {
		this.field = field;
		setLayout(new BorderLayout());
		add(BorderLayout.CENTER, pane = new CellRendererPane());
		JComponent renderer = (JComponent) field.getRenderer();
		setBorder(renderer.getBorder());
		renderer.setBorder(null);
	}

	public void setValue(Object value) {
		this.value = value;
		repaint();
	}

	public Object getValue() {
		return value;
	}

	public void paintComponent(Graphics g) {
		int w = getSize().width;
		int h = getSize().height;
		Component comp = field.getRenderer(). getSpinCellRendererComponent(field, value,
                 		field.hasFocus, field.formatter, field.model.getActiveField());
		pane.paintComponent(g, comp, this, 0, 0, w, h);
	}

	public Dimension getPreferredSize() {
		return ((JComponent) field.getRenderer()).getPreferredSize();
	}

	public Dimension getMinimumSize() {
		return ((JComponent) field.getRenderer()).getMinimumSize();
	}
}