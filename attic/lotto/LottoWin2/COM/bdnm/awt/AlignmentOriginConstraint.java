// Decompiled by Jad v1.5.5.3. Copyright 1997-98 Pavel Kouznetsov.
// Jad home page:      http://web.unicom.com.cy/~kpd/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AlignmentOriginConstraint.java

package com.bdnm.awt;

import java.awt.Dimension;
import java.awt.Rectangle;

// Referenced classes of package com.bdnm.awt:
//            OriginConstraint

public class AlignmentOriginConstraint extends OriginConstraint
{

    public AlignmentOriginConstraint()
    {
    }

    public AlignmentOriginConstraint(double d, int i, double d1, int j, double d2, double d3)
    {
        super(d, i, d1, j);
        leftAlignmentFraction = d2;
        topAlignmentFraction = d3;
    }

    public Rectangle adjustedRectangle(Dimension dimension, Dimension dimension1)
    {
        Rectangle rectangle = super.adjustedRectangle(dimension, dimension1);
        rectangle.translate(-adjustedAlignmentLeft(dimension1), -adjustedAlignmentTop(dimension1));
        return rectangle;
    }

    protected int adjustedAlignmentLeft(Dimension dimension)
    {
        return (int)Math.round(leftAlignmentFraction * (double)dimension.width);
    }

    protected int adjustedAlignmentTop(Dimension dimension)
    {
        return (int)Math.round(topAlignmentFraction * (double)dimension.height);
    }

    public Dimension containSize(Dimension dimension)
    {
        int i = left;
        int j = top;
        left -= adjustedAlignmentLeft(dimension);
        top -= adjustedAlignmentTop(dimension);
        Dimension dimension1 = super.containSize(dimension);
        left = i;
        top = j;
        return dimension1;
    }

    public double leftAlignmentFraction;
    public double topAlignmentFraction;
}
