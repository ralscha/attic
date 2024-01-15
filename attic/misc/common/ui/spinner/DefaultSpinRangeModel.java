package common.ui.spinner;

import java.util.*;
import javax.swing.event.*;

public class DefaultSpinRangeModel implements SpinRangeModel {
	protected Vector listeners = new Vector();

	private double value = 0;
	private double extent = 1;
	private double min = 0;
	private double max = 100;
	private boolean wrap = true;
	private boolean isAdjusting = false;

	public DefaultSpinRangeModel() {}

	public DefaultSpinRangeModel(double value, double extent, double min, double max,
                             	boolean wrap) {
		this.value = value;
		this.extent = extent;
		this.min = min;
		this.max = max;
	}

	public double getValue() {
		return value;
	}

	public double getExtent() {
		return extent;
	}

	public double getMinimum() {
		return min;
	}

	public double getMaximum() {
		return max;
	}

	public boolean getWrap() {
		return wrap;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public void setExtent(double extent) {
		this.extent = extent;
	}

	public void setMinimum(double min) {
		this.min = min;
	}

	public void setMaximum(double max) {
		this.max = max;
	}

	public void setWrap(boolean wrap) {
		this.wrap = wrap;
	}

	public void setValueIsAdjusting(boolean isAdusting) {
		this.isAdjusting = isAdjusting;
		if (!isAdjusting)
			fireStateChanged();
	}

	public boolean getValueIsAdjusting() {
		return isAdjusting;
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

	public String toString() {
		String modelString =
  		"value=" + getValue() + ", " + "extent=" + getExtent() + ", " + "min=" +
  		getMinimum() + ", " + "max=" + getMaximum() + ", " + "adj=" +
  		getValueIsAdjusting();
		return getClass().getName() + "[" + modelString + "]";
	}
}