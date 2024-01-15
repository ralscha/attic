package common.ui.spinner;

import java.text.*;
import java.util.*;

public class JSpinnerList extends JSpinnerField {
	public JSpinnerList(String[] items, int index, boolean wrap) {
		double[] limits = new double[items.length];
		for (int i = 0; i < items.length; i++) {
			limits[i] = i;
		}
		init(new DefaultSpinModel(index, 1, 0, items.length - 1, wrap),
     		new DefaultSpinRenderer(), new ChoiceFormat(limits, items), wrap);
		refreshSpinView();
	}

	public void setLocale(Locale locale) {}
}