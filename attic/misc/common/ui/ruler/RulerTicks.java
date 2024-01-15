package common.ui.ruler;


import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class RulerTicks extends JPanel
  implements SwingConstants
{
  protected RulerModel model;
  
  public RulerTicks(RulerModel model)
  {
    this.model = model;
  }
  
  public void paintComponent(Graphics gc)
  {
    Graphics2D g = (Graphics2D)gc;
    int w = getSize().width - 1;
    int h = getSize().height - 1;
    g.setColor(getBackground());
    g.fillRect(0, 0, w + 1, h + 1);
    g.setColor(getForeground());
    double divisionsPerUnit = model.getDivisionsPerUnit();
    double highlightsPerUnit = model.getHighlightsPerUnit();
    boolean forward = model.getDirection();
    
    double from, base, div, divisions, highlights, edge;
    int orientation = model.getOrientation();
    double increment = model.getIncrement();
    double length = model.getLengthInUnits();
    double anchor = model.getAnchor();
    boolean fixed = model.isFixed();
    if (orientation == NORTH || orientation == SOUTH)
    {
      double pixelsPerUnit = model.getPixelsPerUnit(w);
      div = h / 3;
      base = orientation == NORTH ? h : 0;
      double max = length + (fixed ? 1 : 0);
      for (double i = -1; i < max; i++)
      {
        double pos = (i + anchor) * pixelsPerUnit;
        if (!forward) pos = w - pos;
        g.draw(new Line2D.Double(pos, 0, pos, div * 3));
        divisions = pixelsPerUnit / divisionsPerUnit;
        from = orientation == NORTH ? div * 2 : div;
        for (double j = 0; j < pixelsPerUnit; j += divisions)
        {
          double x = forward ? j : -j;
          g.draw(new Line2D.Double(pos + x, from, pos + x, base));
        }
        highlights = pixelsPerUnit / highlightsPerUnit;
        from = orientation == NORTH ? div : div * 2;
        for (double j = 0; j < pixelsPerUnit; j += highlights)
        {
          double x = forward ? j : -j;
          g.draw(new Line2D.Double(pos + x, from, pos + x, base));
        }
      }
      double pos = length * pixelsPerUnit;
      if (!forward) pos = w - pos;
      if (!fixed) g.draw(new Line2D.Double(pos, 0, pos, h));
    }
    else
    {
      double pixelsPerUnit = model.getPixelsPerUnit(h);
      div = w / 3;
      base = orientation == WEST ? w : 0;
      double max = length + (fixed ? 1 : 0);
      for (double i = -1; i < max; i++)
      {
        double pos = (i + anchor) * pixelsPerUnit;
        if (!forward) pos = h - pos;
        g.draw(new Line2D.Double(0, pos, w, pos));
        divisions = pixelsPerUnit / divisionsPerUnit;
        for (double j = 0; j < pixelsPerUnit; j += divisions)
        {
          double y = forward ? j : -j;
          from = orientation == WEST ? div * 2 : div;
          g.draw(new Line2D.Double(from, pos + y, base, pos + y));
        }
        highlights = pixelsPerUnit / highlightsPerUnit;
        for (double j = 0; j < pixelsPerUnit; j += highlights)
        {
          double y = forward ? j : -j;
          from = orientation == WEST ? div : div * 2;
          g.draw(new Line2D.Double(from, pos + y, base, pos + y));
        }
      }
      double pos = length * pixelsPerUnit;
      if (!forward) pos = h - pos;
      if (!fixed) g.draw(new Line2D.Double(0, pos, w, pos));
    }
  }
}

