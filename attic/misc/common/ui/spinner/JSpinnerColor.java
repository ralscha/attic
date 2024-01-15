package common.ui.spinner;

import java.util.*;
import java.awt.*;

public class JSpinnerColor extends JSpinnerField {
	protected Vector list = new Vector();

	public JSpinnerColor(Color[] items, int index, boolean wrap) {
		super(new DefaultSpinModel(index, 1, 0, items.length - 1, wrap),
      		new ColorSpinRenderer(), null, wrap);
		for (int i = 0; i < items.length; i++) {
			list.addElement(items[i]);
		}
		refreshSpinView();
	}

	protected void refreshSpinView() {
		if (list == null)
			return;
		int fieldID = model.getActiveField();
		SpinRangeModel range = model.getRange(fieldID);
		spinField.setValue(list.elementAt((int) range.getValue()));
	}

	public void updateFieldOrder() {}
}