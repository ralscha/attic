package common.ui.spinner;

import java.text.*;
import java.util.*;

public class JSpinnerDate extends JSpinnerField {
	public JSpinnerDate() {
		this(Calendar.getInstance());
	}

	public JSpinnerDate(Calendar date) {
		super(new DateSpinModel(), new DefaultSpinRenderer(),
      		DateFormat.getDateInstance(DateFormat.MEDIUM), true);
		getDateModel().setDate(date);
		refreshSpinView();
	}

	public void setLocale(Locale locale) {
		formatter = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
		updateFieldOrder();
	}

	private DateSpinModel getDateModel() {
		return (DateSpinModel) model;
	}

	protected void refreshSpinView() {
		spinField.setValue(getDateModel().getDate().getTime());
	}
}