// Decompiled by Jad v1.5.5.3. Copyright 1997-98 Pavel Kouznetsov.
// Jad home page:      http://web.unicom.com.cy/~kpd/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OriginConstraint.java

package com.bdnm.awt;

import java.awt.*;

public class OriginConstraint
{

    public OriginConstraint()
    {
    }

    public OriginConstraint(double d, int i, double d1, int j)
    {
        this();
        leftFraction = d;
        left = i;
        topFraction = d1;
        top = j;
    }

    public Rectangle adjustedRectangle(Dimension dimension, Dimension dimension1)
    {
        Rectangle rectangle = new Rectangle(dimension1);
        rectangle.setLocation(adjustedLeft(dimension), adjustedTop(dimension));
        return rectangle;
    }

    protected int adjustedLeft(Dimension dimension)
    {
        return (int)Math.round(leftFraction * (double)dimension.width) + left;
    }

    protected int adjustedTop(Dimension dimension)
    {
        return (int)Math.round(topFraction * (double)dimension.height) + top;
    }

    public Dimension containSize(Dimension dimension)
    {
        Dimension dimension1 = new Dimension();
        if(leftFraction > 0.0D)
        {
            if(leftFraction < 1.0D)
            {
                dimension1.width = (int)Math.ceil((double)(left + dimension.width) / (1.0D - leftFraction));
                int i = (int)(leftFraction * (double)dimension1.width) + left;
                if(i < 0)
                    dimension1.width -= i;
            }
            else
            {
                dimension1.width = -left;
            }
        }
        else
        {
            dimension1.width = left + dimension.width;
        }
        if(topFraction > 0.0D)
        {
            if(topFraction < 1.0D)
            {
                dimension1.height = (int)Math.ceil((double)(top + dimension.height) / (1.0D - topFraction));
                int j = (int)(topFraction * (double)dimension1.height) + top;
                if(j < 0)
                    dimension1.height -= j;
            }
            else
            {
                dimension1.height = -top;
            }
        }
        else
        {
            dimension1.height = top + dimension.height;
        }
        return dimension1;
    }

    public double leftFraction;
    public int left;
    public double topFraction;
    public int top;
}
