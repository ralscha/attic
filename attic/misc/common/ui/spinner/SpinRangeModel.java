package common.ui.spinner;

import javax.swing.event.*;

public interface SpinRangeModel {
	public double getValue();
	public double getExtent();
	public double getMinimum();
	public double getMaximum();
	public boolean getWrap();
	public void setValue(double value);
	public void setExtent(double extent);
	public void setMinimum(double min);
	public void setMaximum(double max);
	public void setWrap(boolean wrap);
	public void setValueIsAdjusting(boolean adusting);
	public boolean getValueIsAdjusting();
	public void addChangeListener(ChangeListener listener);
	public void removeChangeListener(ChangeListener listener);
}