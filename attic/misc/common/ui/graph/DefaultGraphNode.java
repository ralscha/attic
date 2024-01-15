package common.ui.graph;

/*
=====================================================================

	DefaultGraphNode.java
	
	Created by Claude Duguay
	Copyright (c) 1999
	
=====================================================================
*/

import java.awt.*;
import java.awt.geom.*;

public class DefaultGraphNode implements GraphNode
{
	protected double x, y;
	protected boolean fixed;
	protected String name;

	public DefaultGraphNode(String name)
	{
		x = 10 + 380 * Math.random();
		y = 10 + 380 * Math.random();
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public boolean isFixed()
	{
		return fixed;
	}
	
	public void setFixed(boolean fixed)
	{
		this.fixed = fixed;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public void setX(double x)
	{
		this.x = x;
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
}

