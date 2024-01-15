package common.ui.pattern;

import java.awt.*;
import javax.swing.*;

public class PreviewPanel extends JPanel
{
  protected PatternPaint pattern = PatternConstants.DEFAULT;

  public PreviewPanel()
  {
    setOpaque(true);
    setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createTitledBorder("Preview"),
      BorderFactory.createEmptyBorder(0, 4, 8, 4)));
    setPreferredSize(new Dimension(70, 70));
  }
  
  public PatternPaint getPattern()
  {
    return new PatternPaint(pattern,
      getForeground(), getBackground());
  }
  
  public void setPattern(PatternPaint pattern)
  {
    this.pattern = pattern;
  }
  
  public void paintComponent(Graphics gc)
  {
    Graphics2D g = (Graphics2D)gc;
    int w = getSize().width;
    int h = getSize().height;
    g.setColor(getParent().getBackground());
    g.fillRect(0, 0, w, h);
    if (pattern == null) return;
    Insets insets = getInsets();
    Rectangle rect = new Rectangle(
      insets.left, insets.top,
      w - (insets.left + insets.right),
      h - (insets.top + insets.bottom));
    g.setPaint(new PatternPaint(pattern,
      getForeground(), getBackground()));
    g.fill(rect);
  }
}

