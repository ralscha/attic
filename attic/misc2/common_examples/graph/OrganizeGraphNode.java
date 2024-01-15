package graph;
/*
=====================================================================

	OrganizeGraphNode.java
	
	Created by Claude Duguay
	Copyright (c) 1999
	
=====================================================================
*/

import common.ui.graph.*;

public class OrganizeGraphNode extends DefaultGraphNode
{
	protected double dx, dy;

	public OrganizeGraphNode(String name)
	{
		super(name);
	}
	
	public double getDX()
	{
		return dx;
	}
	
	public double getDY()
	{
		return dy;
	}
	
	public void setDX(double dx)
	{
		this.dx = dx;
	}
	
	public void setDY(double dy)
	{
		this.dy = dy;
	}
}

