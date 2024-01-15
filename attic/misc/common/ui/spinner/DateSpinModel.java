package common.ui.spinner;

import java.util.*;
import java.text.*;


public class DateSpinModel extends DefaultSpinModel {
	protected Calendar date = Calendar.getInstance();

	public DateSpinModel() {
		setRange(DateFormat.MONTH_FIELD, new DefaultSpinRangeModel());
		setRange(DateFormat.DATE_FIELD, new DefaultSpinRangeModel());
		setRange(DateFormat.YEAR_FIELD, new DefaultSpinRangeModel());
		setActiveField(DateFormat.MONTH_FIELD);
	}

	public void setRange(int fieldID, SpinRangeModel range) {
		super.setRange(fieldID, range);
		if (fieldID == DateFormat.DATE_FIELD) {
			date.set(Calendar.DATE, (int) range.getValue());
		}
		if (fieldID == DateFormat.MONTH_FIELD) {
			date.set(Calendar.MONTH, (int) range.getValue());
		}
		if (fieldID == DateFormat.YEAR_FIELD) {
			date.set(Calendar.YEAR, (int) range.getValue());
		}
	}

	public SpinRangeModel getRange(int fieldID) {
		SpinRangeModel range = super.getRange(fieldID);
		if (fieldID == DateFormat.DATE_FIELD) {
			range.setExtent(1);
			range.setValue(date.get(Calendar.DAY_OF_MONTH));
			range.setMinimum(date.getActualMinimum(Calendar.DAY_OF_MONTH));
			range.setMaximum(date.getActualMaximum(Calendar.DAY_OF_MONTH));
		}
		if (fieldID == DateFormat.MONTH_FIELD) {
			range.setExtent(1);
			range.setValue(date.get(Calendar.MONTH));
			range.setMinimum(date.getActualMinimum(Calendar.MONTH));
			range.setMaximum(date.getActualMaximum(Calendar.MONTH));
		}
		if (fieldID == DateFormat.YEAR_FIELD) {
			range.setExtent(1);
			range.setValue(date.get(Calendar.YEAR));
			range.setMinimum(date.getActualMinimum(Calendar.YEAR));
			range.setMaximum(date.getActualMaximum(Calendar.YEAR));
		}
		return range;
	}

	public void setDate(Calendar date) {
		this.date = date;
		getRange(DateFormat.DATE_FIELD);
		getRange(DateFormat.MONTH_FIELD);
		getRange(DateFormat.YEAR_FIELD);
	}

	public Calendar getDate() {
		return date;
	}
}