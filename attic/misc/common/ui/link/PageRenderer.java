package common.ui.link;
import java.awt.*;
import javax.swing.*;

public interface PageRenderer
{
  public JComponent getPageRendererComponent(
    JLink link, Page page, int x, int y);
}

