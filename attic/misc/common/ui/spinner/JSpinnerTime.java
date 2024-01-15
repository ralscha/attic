package common.ui.spinner;

import java.text.*;
import java.util.*;

public class JSpinnerTime extends JSpinnerField {
	public JSpinnerTime() {
		this(Calendar.getInstance());
	}

	public JSpinnerTime(Calendar time) {
		super(new TimeSpinModel(), new DefaultSpinRenderer(),
      		DateFormat.getTimeInstance(DateFormat.SHORT), true);
		getTimeModel().setTime(time);
		refreshSpinView();
	}

	public void setLocale(Locale locale) {
		formatter = DateFormat.getTimeInstance(DateFormat.SHORT, locale);
		updateFieldOrder();
	}

	private TimeSpinModel getTimeModel() {
		return (TimeSpinModel) model;
	}

	protected void refreshSpinView() {
		spinField.setValue(getTimeModel().getTime().getTime());
	}
}