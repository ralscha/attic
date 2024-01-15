package common.ui.range;

import javax.swing.*;

public interface RangeRenderer
{
  public JComponent getRangeRendererComponent(
    JRange range, BoundedRangeModel model,
    boolean hasFocus);
}

