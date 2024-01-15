package common.ui.scroller;

import java.awt.*;
import javax.swing.*;

public class JScrollerPane extends JScrollPane
{
  protected JScrollBar vert, horz;

  public JScrollerPane()
  {
    this(null,
      VERTICAL_SCROLLBAR_AS_NEEDED,
      HORIZONTAL_SCROLLBAR_AS_NEEDED,
      new JScrollerBar(JScrollerBar.VERTICAL),
      new JScrollerBar(JScrollerBar.HORIZONTAL));
  }
  
  public JScrollerPane(JScrollBar vert, JScrollBar horz)
  {
    this(null,
      VERTICAL_SCROLLBAR_AS_NEEDED,
      HORIZONTAL_SCROLLBAR_AS_NEEDED,
      vert, horz);
  }

  public JScrollerPane(Component view)
  {
    this(view,
      VERTICAL_SCROLLBAR_AS_NEEDED,
      HORIZONTAL_SCROLLBAR_AS_NEEDED,
      new JScrollerBar(JScrollerBar.VERTICAL),
      new JScrollerBar(JScrollerBar.HORIZONTAL));
  }

  public JScrollerPane(Component view,
    int vsbPolicy, int hsbPolicy)
  {
    this(view, vsbPolicy, hsbPolicy,
      new JScrollerBar(JScrollerBar.VERTICAL),
      new JScrollerBar(JScrollerBar.HORIZONTAL));
  }
  
  public JScrollerPane(int vsbPolicy, int hsbPolicy)
  {
    this(null, vsbPolicy, hsbPolicy,
      new JScrollerBar(JScrollerBar.VERTICAL),
      new JScrollerBar(JScrollerBar.HORIZONTAL));
  }

  public JScrollerPane(Component view,
    JScrollBar vert, JScrollBar horz)
  {
    this(view,
      VERTICAL_SCROLLBAR_AS_NEEDED,
      HORIZONTAL_SCROLLBAR_AS_NEEDED,
      vert, horz);
  }
  
  public JScrollerPane(Component view,
    int vsbPolicy, int hsbPolicy,
    JScrollBar vert, JScrollBar horz)
  {
    super(view, vsbPolicy, hsbPolicy);
    setVerticalScrollBar(vert);
    setHorizontalScrollBar(horz);
  }
}

