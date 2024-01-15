
package common.ui.graph;
/*
=====================================================================

	GraphNode.java
	
	Created by Claude Duguay
	Copyright (c) 1999
	
=====================================================================
*/

import java.awt.*;
import java.awt.geom.*;

public interface GraphNode
{
	public String getName();
	public void setName(String name);
	
	public boolean isFixed();
	public void setFixed(boolean fixed);
	
	public double getX();
	public double getY();
	public void setX(double x);
	public void setY(double y);
}

