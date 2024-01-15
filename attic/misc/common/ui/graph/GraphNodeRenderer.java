
package common.ui.graph;
/*
=====================================================================

	GraphNodeRenderer.java
	
	Created by Claude Duguay
	Copyright (c) 1999
	
=====================================================================
*/

import javax.swing.*;

public interface GraphNodeRenderer
{
	public JComponent getGraphNodeRendererComponent(
		JGraph graph, GraphNode node, boolean hasFocus);
}

