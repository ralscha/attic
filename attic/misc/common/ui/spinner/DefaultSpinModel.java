package common.ui.spinner;

import javax.swing.event.*;
import java.util.*;
import java.text.*;

public class DefaultSpinModel implements SpinModel, ChangeListener {
	protected int activeField;
	protected Vector fieldIDs = new Vector();
	protected Hashtable table = new Hashtable();
	protected Vector listeners = new Vector();

	public DefaultSpinModel() {}

	public DefaultSpinModel(double value, double extent, double min, double max, boolean wrap) {
		SpinRangeModel model = new DefaultSpinRangeModel(value, extent, min, max, wrap);
		setRange(NumberFormat.INTEGER_FIELD, model);
		setActiveField(NumberFormat.INTEGER_FIELD);
	}

	public DefaultSpinModel(double iValue, double iExtent, double iMin, double iMax,
                        	boolean iWrap, double fValue, double fExtent, double fMin, double fMax,
                        	boolean fWrap) {
		SpinRangeModel iModel = new DefaultSpinRangeModel(iValue, iExtent, iMin, iMax, iWrap);
		setRange(NumberFormat.INTEGER_FIELD, iModel);

		SpinRangeModel fModel = new DefaultSpinRangeModel(fValue, fExtent, fMin, fMax, fWrap);
		setRange(NumberFormat.FRACTION_FIELD, fModel);

		setActiveField(NumberFormat.INTEGER_FIELD);
	}

	public int getFieldCount() {
		return fieldIDs.size();
	}

	public int getActiveField() {
		return activeField;
	}

	public void setActiveField(int fieldID) {
		activeField = fieldID;
		fireStateChanged();
	}

	public void setNextField() {
		int[] array = getFieldIDs();
		for (int i = 0; i < array.length; i++) {
			if (array[i] == activeField)// && i != array.length - 1)
			{
				setActiveField(array[Math.min(i + 1, array.length - 1)]);
				return;
			}
		}
	}

	public void setPrevField() {
		int[] array = getFieldIDs();
		for (int i = 0; i < array.length; i++) {
			if (array[i] == activeField) {
				setActiveField(array[Math.max(i - 1, 0)]);
				return;
			}
		}
	}

	public void setRange(int id, SpinRangeModel range) {
		Integer key = new Integer(id);
		if (!table.containsKey(key)) {
			fieldIDs.addElement(key);
			range.addChangeListener(this);
		}
		table.put(key, range);
	}

	public SpinRangeModel getRange(int id) {
		Integer key = new Integer(id);
		return (SpinRangeModel) table.get(key);
	}

	public int[] getFieldIDs() {
		int size = fieldIDs.size();
		int[] array = new int[size];
		for (int i = 0; i < size; i++) {
			array[i] = ((Integer) fieldIDs.elementAt(i)).intValue();
		}
		return array;
	}

	public void setFieldIDs(int[] fields) {
		fieldIDs.removeAllElements();
		for (int i = 0; i < fields.length; i++) {
			fieldIDs.addElement(new Integer(fields[i]));
		}
	}

	public void stateChanged(ChangeEvent event) {
		fireStateChanged();
	}

	public void addChangeListener(ChangeListener listener) {
		listeners.addElement(listener);
	}

	public void removeChangeListener(ChangeListener listener) {
		listeners.removeElement(listener);
	}

	public void fireStateChanged() {
		ChangeListener listener;
		Vector list = (Vector) listeners.clone();
		ChangeEvent event = new ChangeEvent(this);
		for (int i = 0; i < list.size(); i++) {
			listener = ((ChangeListener) list.elementAt(i));
			listener.stateChanged(event);
		}
	}
}