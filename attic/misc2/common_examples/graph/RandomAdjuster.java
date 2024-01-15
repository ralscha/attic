package graph;
/*
=====================================================================

	RandomAdjuster.java
	
	Created by Claude Duguay
	Copyright (c) 1999
	
=====================================================================
*/

import java.awt.*;
import common.ui.graph.*;

public class RandomAdjuster implements GraphAdjuster
{
 	protected JGraph graph;
	
	public RandomAdjuster(JGraph graph)
	{
		this.graph = graph;
	}
	
	public synchronized void adjust()
	{
		GraphModel model = graph.getModel();
    if (Math.random() < 0.03)
		{
			GraphNode node = model.getNode(
				(int)(Math.random() * model.getNodeCount()));
			if (!node.isFixed())
			{
	    	node.setX(node.getX() + 100 * Math.random() - 50);
	    	node.setY(node.getY() + 100 * Math.random() - 50);
			}
    }
		graph.repaint();
	}
}

