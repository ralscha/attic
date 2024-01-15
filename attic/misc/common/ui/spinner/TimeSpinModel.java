package common.ui.spinner;

import java.util.*;
import java.text.*;

public class TimeSpinModel extends DefaultSpinModel {
	protected Calendar time = Calendar.getInstance();

	public TimeSpinModel() {
		setRange(DateFormat.HOUR1_FIELD, new DefaultSpinRangeModel());
		setRange(DateFormat.MINUTE_FIELD, new DefaultSpinRangeModel());
		setRange(DateFormat.AM_PM_FIELD, new DefaultSpinRangeModel());
		setActiveField(DateFormat.HOUR1_FIELD);
		setTime(time);
	}

	public void setRange(int fieldID, SpinRangeModel range) {
		super.setRange(fieldID, range);
		if (fieldID == DateFormat.HOUR1_FIELD) {
			time.set(Calendar.HOUR, (int) range.getValue());
		}
		if (fieldID == DateFormat.MINUTE_FIELD) {
			time.set(Calendar.MINUTE, (int) range.getValue());
		}
		if (fieldID == DateFormat.AM_PM_FIELD) {
			time.set(Calendar.HOUR_OF_DAY,
         			time.get(Calendar.HOUR) + 12 * (int) range.getValue());
		}
	}

	public SpinRangeModel getRange(int fieldID) {
		SpinRangeModel range = super.getRange(fieldID);
		if (fieldID == DateFormat.HOUR1_FIELD) {
			range.setExtent(1.0);
			range.setValue(time.get(Calendar.HOUR));
			range.setMinimum(time.getActualMinimum(Calendar.HOUR));
			range.setMaximum(time.getActualMaximum(Calendar.HOUR));
		}
		if (fieldID == DateFormat.MINUTE_FIELD) {
			range.setExtent(1.0);
			range.setValue(time.get(Calendar.MINUTE));
			range.setMinimum(time.getActualMinimum(Calendar.MINUTE));
			range.setMaximum(time.getActualMaximum(Calendar.MINUTE));
		}
		if (fieldID == DateFormat.AM_PM_FIELD) {
			range.setExtent(1.0);
			range.setValue(time.get(Calendar.AM_PM));
			range.setMinimum(time.getActualMinimum(Calendar.AM_PM));
			range.setMaximum(time.getActualMaximum(Calendar.AM_PM));
		}
		return range;
	}

	public void setTime(Calendar time) {
		this.time = time;
		getRange(DateFormat.HOUR1_FIELD);
		getRange(DateFormat.MINUTE_FIELD);
		getRange(DateFormat.AM_PM_FIELD);
		fireStateChanged();
	}

	public Calendar getTime() {
		return time;
	}

	public static void main(String[] args) {
		TimeSpinModel model = new TimeSpinModel();
		int activeField = model.getActiveField();
		System.out.println(model.getRange(activeField));
		model.setNextField();
		activeField = model.getActiveField();
		System.out.println(model.getRange(activeField));
		model.setNextField();
		activeField = model.getActiveField();
		System.out.println(model.getRange(activeField));
		model.setNextField();
		activeField = model.getActiveField();
		System.out.println(model.getRange(activeField));
	}
}