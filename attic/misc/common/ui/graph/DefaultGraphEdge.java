package common.ui.graph;

/*
=====================================================================

	DefaultGraphEdge.java
	
	Created by Claude Duguay
	Copyright (c) 1999
	
=====================================================================
*/

public class DefaultGraphEdge implements GraphEdge
{
	private GraphNode from, to;
	private double weight;
	
	public DefaultGraphEdge(GraphNode from, GraphNode to, double weight)
	{
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
	
	public GraphNode getFrom()
	{
		return from;
	}

	public GraphNode getTo()
	{
		return to;
	}

	public double getWeight()
	{
		return weight;
	}
}

