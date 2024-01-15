package common.ui.link;
import java.awt.*;
import javax.swing.*;

public interface ConnectionRenderer
{
  public JComponent getConnnectionRendererComponent(
    JLink link, int x1, int y1, int x2, int y2);
}

