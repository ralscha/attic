package common.ui.organizer;

import javax.swing.*;

public interface OrganizableList
{
  public int getElementCount();
  public Object[] getSelectionList();
  public void insertSelectionList(Object[] values);
  public void moveSelectedUp();
  public void moveSelectedDown();
  public void removeSelected();
  public ListSelectionModel getSelectionModel();
  public JComponent getComponent();
}

