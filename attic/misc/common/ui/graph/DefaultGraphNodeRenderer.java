package common.ui.graph;

/*
=====================================================================

  DefaultGraphNodeRenderer.java
  
  Created by Claude Duguay
  Copyright (c) 1999
  
=====================================================================
*/

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class DefaultGraphNodeRenderer extends JLabel
  implements GraphNodeRenderer
{
  protected final Color fixedColor =
    Color.red;
  protected final Color nodeColor =
    new Color(250, 220, 100);
  protected final Border normalBorder =
    new EtchedBorder(EtchedBorder.RAISED);
  protected final Border focusBorder =
    new LineBorder(Color.blue, 2);

  public DefaultGraphNodeRenderer()
  {
    setBorder(normalBorder);
    setOpaque(true);
  }

  public JComponent getGraphNodeRendererComponent(
    JGraph graph, GraphNode node, boolean hasFocus)
  {
    setText(node.getName());
    setBackground(node.isFixed() ? fixedColor : nodeColor);
    setBorder(hasFocus ? focusBorder : normalBorder);
    return this;
  }
}

