package graph;
/*
=====================================================================

	ShakeAdjuster.java
	
	Created by Claude Duguay
	Copyright (c) 1999
	
=====================================================================
*/

import java.awt.*;
import common.ui.graph.*;

public class ShakeAdjuster implements GraphAdjuster
{
	protected JGraph graph;
	
	public ShakeAdjuster(JGraph graph)
	{
		this.graph = graph;
	}

  public void adjust()
	{
		GraphModel model = graph.getModel();
		for (int i = 0; i < model.getNodeCount(); i++)
		{
			GraphNode node = model.getNode(i);
			if (!node.isFixed())
			{
				node.setX(node.getX() + 80 * Math.random() - 40);
				node.setY(node.getY() + 80 * Math.random() - 40);
			}
		}
		graph.repaint();
	}
}

