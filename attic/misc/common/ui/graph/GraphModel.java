
package common.ui.graph;
/*
=====================================================================

	GraphModel.java
	
	Created by Claude Duguay
	Copyright (c) 1999
	
=====================================================================
*/

import java.awt.*;

public interface GraphModel
{
	public int getNodeCount();
	public GraphNode getNode(int index);
	public void addNode(GraphNode node);

	public int getEdgeCount();
	public GraphEdge getEdge(int index);
	public void addEdge(GraphEdge edge);
}

