package graph;
/*
=====================================================================

	OrganizeAdjuster.java
	
	Created by Claude Duguay
	Copyright (c) 1999
	
=====================================================================
*/

import java.awt.*;
import common.ui.graph.*;
public class OrganizeAdjuster implements GraphAdjuster
{
 	protected JGraph graph;
	
	public OrganizeAdjuster(JGraph graph)
	{
		this.graph = graph;
	}
	
  private OrganizeGraphNode safeCast(GraphNode node)
	{
		if (node instanceof OrganizeGraphNode)
		{
			return (OrganizeGraphNode)node;
		}
		throw new IllegalArgumentException(
			"OrganizeAdjuster requires AdjustableGraphNode objects");
	}
	
	public synchronized void adjust()
	{
		GraphModel model = graph.getModel();
		
		// Minimum adjustment
		for (int i = 0; i < model.getEdgeCount(); i++)
		{
	    GraphEdge edge = model.getEdge(i);
			OrganizeGraphNode from = safeCast(edge.getFrom());
			OrganizeGraphNode to = safeCast(edge.getTo());
			double vx = to.getX() - from.getX();
	    double vy = to.getY() - from.getY();
	    double len = Math.sqrt(vx * vx + vy * vy);
      len = (len == 0) ? .0001 : len;
	    double f = (model.getEdge(i).getWeight() - len) / (len * 3);
	    double dx = f * vx;
	    double dy = f * vy;

	    to.setDX(to.getDX() + dx);
	    to.setDY(to.getDY() + dy);
	    from.setDX(from.getDX() - dx);
	    from.setDY(from.getDY() - dy);
		}

		// Comparative adjustment
		for (int i = 0; i < model.getNodeCount(); i++)
		{
	    OrganizeGraphNode node = safeCast(model.getNode(i));
	    double dx = 0;
	    double dy = 0;

	    for (int j = 0; j < model.getNodeCount(); j++)
			{
				if (i == j) continue;
				OrganizeGraphNode to = safeCast(model.getNode(j));
				double vx = node.getX() - to.getX();
				double vy = node.getY() - to.getY();
				double len = vx * vx + vy * vy;
				if (len == 0)
				{
			    dx += Math.random();
			    dy += Math.random();
				}
				else if (len < 100 * 100)
				{
			    dx += vx / len;
		    	dy += vy / len;
				}
	  	}
		  double dlen = dx * dx + dy * dy;
		  if (dlen > 0)
			{
				dlen = Math.sqrt(dlen) / 2;
				node.setDX(node.getDX() + (dx / dlen));
				node.setDY(node.getDY() + (dy / dlen));
	  	}
		}

		// Constrained adjustment
		Dimension d = graph.getSize();
		for (int i = 0; i < model.getNodeCount(); i++)
		{
	  	OrganizeGraphNode node = safeCast(model.getNode(i));
	  	if (!node.isFixed())
			{
				node.setX(node.getX() +
					Math.max(-5, Math.min(5, node.getDX())));
				node.setY(node.getY() +
					Math.max(-5, Math.min(5, node.getDY())));
    	}
  	  if (node.getX() < 0)
			{
      	node.setX(0);
    	}
			else if (node.getX() > d.width)
			{
    		node.setX(d.width);
			}
  	  if (node.getY() < 0)
			{
    		node.setY(0);
			}
			else if (node.getY() > d.height)
			{
      	node.setY(d.height);
    	}
	  	node.setDX(node.getDX() / 2);
	 		node.setDY(node.getDY() / 2);
		}
		graph.repaint();
	}
}

