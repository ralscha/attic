
package common.ui.graph;
/*
=====================================================================

	GraphEdgeRenderer.java
	
	Created by Claude Duguay
	Copyright (c) 1999
	
=====================================================================
*/

import javax.swing.*;

public interface GraphEdgeRenderer
{
	public JComponent getGraphEdgeRendererComponent(
		JGraph graph, GraphEdge edge, boolean showStress);
}

