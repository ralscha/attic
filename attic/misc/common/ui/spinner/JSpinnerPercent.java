
package common.ui.spinner;

import java.text.*;
import java.util.*;

public class JSpinnerPercent extends JSpinnerField {
	public JSpinnerPercent() {
		init(new DefaultSpinModel(0, 0.01, 0, 1, true), new DefaultSpinRenderer(),
     		NumberFormat.getPercentInstance(), true);
		refreshSpinView();
	}

	public void setLocale(Locale locale) {
		formatter = NumberFormat.getPercentInstance(locale);
		updateFieldOrder();
	}
}