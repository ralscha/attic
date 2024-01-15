package common.ui.range;

import javax.swing.*;

public interface RangeEditor
{
  public int getWidth();
  public JComponent getRangeEditorComponent(
    JRange range, BoundedRangeModel model,
    boolean hasFocus);
}

