package common.ui.binder;

import common.ui.layout.*;
import java.awt.*;

public class BinderLayout extends AbstractLayout {
  protected int page = 0;

  public BinderLayout(int hgap) {
    super(hgap, 0);
  }

  public Dimension preferredLayoutSize(Container parent) {
    int count = parent.getComponentCount();
    int width = 0;
    int height = 0;
    for (int i = 0; i < count; i++) {
      Component child = parent.getComponent(i);
      Dimension size = child.getPreferredSize();
      if (size.width > width)
        width = size.width;
      if (size.height > height)
        height = size.height;
    }
    Insets insets = parent.getInsets();
    int x = insets.left + insets.right;
    int y = insets.top + insets.bottom;
    return new Dimension(width * 2 + x + hgap, height + y);
  }

  public Dimension minimumLayoutSize(Container parent) {
    int count = parent.getComponentCount();
    int width = 0;
    int height = 0;
    for (int i = 0; i < count; i++) {
      Component child = parent.getComponent(i);
      Dimension size = child.getMinimumSize();
      if (size.width > width)
        width = size.width;
      if (size.height > height)
        height = size.height;
    }
    Insets insets = parent.getInsets();
    int x = insets.left + insets.right;
    int y = insets.top + insets.bottom;
    return new Dimension(width * 2 + x + hgap, height + y);
  }

  public void layoutContainer(Container parent) {
    int count = parent.getComponentCount();
    Insets insets = parent.getInsets();
    int x = insets.left;
    int y = insets.top;
    int w = parent.getSize().width - (insets.left + insets.right) - hgap;
    int h = parent.getSize().height - (insets.top + insets.bottom);
    for (int i = 0; i < count; i++) {
      Component child = parent.getComponent(i);
      boolean active = (i == page - 1 || i == page);
      child.setVisible(active);
      child.setEnabled(active);
      if (i == page - 1) {
        child.setBounds(x, y, w / 2, h);
      }
      if (i == page) {
        child.setBounds(x + w / 2 + hgap, y, w / 2, h);
      }
    }
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }
}

