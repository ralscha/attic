package common.ui.graph;

/*
=====================================================================

	GraphEdge.java
	
	Created by Claude Duguay
	Copyright (c) 1999
	
=====================================================================
*/

public interface GraphEdge
{
	public GraphNode getFrom();
	public GraphNode getTo();
	public double getWeight();
}

