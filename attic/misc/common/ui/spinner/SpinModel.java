package common.ui.spinner;

import javax.swing.event.*;

public interface SpinModel {
	public int getFieldCount();
	public int getActiveField();
	public void setActiveField(int fieldID);
	public void setNextField();
	public void setPrevField();
	public int[] getFieldIDs();
	public void setFieldIDs(int[] fields);
	public void setRange(int fieldID, SpinRangeModel range);
	public SpinRangeModel getRange(int fieldID);
	public void addChangeListener(ChangeListener listener);
	public void removeChangeListener(ChangeListener listener);
}