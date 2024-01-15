package common.ui.graph;

/*
=====================================================================

	DefaultGraphModel.java
	
	Created by Claude Duguay
	Copyright (c) 1999
	
=====================================================================
*/

import java.util.*;

public class DefaultGraphModel implements GraphModel
{
	protected ArrayList nodes = new ArrayList();
	protected ArrayList edges = new ArrayList();

	public int getNodeCount()
	{
		return nodes.size();
	}
	
	public GraphNode getNode(int index)
	{
		return (GraphNode)nodes.get(index);
	}
	
	public void addNode(GraphNode node)
	{
		nodes.add(node);
	}
	
	public int getEdgeCount()
	{
		return edges.size();
	}
	
	public GraphEdge getEdge(int index)
	{
		return (GraphEdge)edges.get(index);
	}

	public void addEdge(GraphEdge edge)
	{
		edges.add(edge);
	}

}

