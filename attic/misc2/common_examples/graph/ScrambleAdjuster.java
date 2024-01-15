package graph;
/*
=====================================================================

  ScrambleAdjuster.java
  
  Created by Claude Duguay
  Copyright (c) 1999
  
=====================================================================
*/

import java.awt.*;
import common.ui.graph.*;

public class ScrambleAdjuster implements GraphAdjuster
{
  protected JGraph graph;
  
  public ScrambleAdjuster(JGraph graph)
  {
    this.graph = graph;
  }

  public void adjust()
  {
    Dimension area = graph.getSize();
    GraphModel model = graph.getModel();
    for (int i = 0; i < model.getNodeCount(); i++)
    {
      GraphNode node = model.getNode(i);
      node.setX(10 + (area.width - 20) * Math.random());
      node.setY(10 + (area.height - 20) * Math.random());
    }
    graph.repaint();
  }
}

