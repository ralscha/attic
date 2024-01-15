package common.ui.graph;

/*
=====================================================================

  DefaultGraphEdgeRenderer.java
  
  Created by Claude Duguay
  Copyright (c) 1999
  
=====================================================================
*/

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class DefaultGraphEdgeRenderer extends JPanel
  implements GraphEdgeRenderer
{
  protected final Color textColor = Color.darkGray;
  protected final Color normalColor = Color.black;
  protected final Color stressColor = Color.red;
  
  protected GraphEdge edge;
  protected boolean showStress;
  
  public JComponent getGraphEdgeRendererComponent(
    JGraph graph, GraphEdge edge, boolean showStress)
  {
    this.edge = edge;
    this.showStress = showStress;
    return this;
  }

  public void paintComponent(Graphics g)
  {
    int x1 = (int)edge.getFrom().getX();
    int y1 = (int)edge.getFrom().getY();
    int x2 = (int)edge.getTo().getX();
    int y2 = (int)edge.getTo().getY();
    int len = (int)Math.abs(
      Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)) -
      edge.getWeight());
    g.setColor((len < 10) ? normalColor : stressColor);
    g.drawLine(x1, y1, x2, y2);
    if (showStress)
    {
      g.setColor(textColor);
      g.drawString(String.valueOf(len),
        x1 + (x2 - x1) / 2, y1 + (y2 - y1) / 2);
    }
  }
}

