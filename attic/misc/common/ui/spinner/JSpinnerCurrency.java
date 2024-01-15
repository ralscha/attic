package common.ui.spinner;

import java.text.*;
import java.util.*;

public class JSpinnerCurrency extends JSpinnerField {
	public JSpinnerCurrency() {
		init(new DefaultSpinModel(0, 1, 0, 10, true, 0, 0.01, 0, 1, true),
     		new DefaultSpinRenderer(), NumberFormat.getCurrencyInstance(), true);
		refreshSpinView();
	}

	public void setLocale(Locale locale) {
		formatter = NumberFormat.getCurrencyInstance(locale);
		updateFieldOrder();
	}

	protected void refreshSpinView() {
		double integer = model.getRange(NumberFormat.INTEGER_FIELD).getValue();
		double fraction = model.getRange(NumberFormat.FRACTION_FIELD).getValue();
		spinField.setValue(new Double(integer + fraction));
	}
}