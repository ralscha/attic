// Decompiled by Jad v1.5.5.3. Copyright 1997-98 Pavel Kouznetsov.
// Jad home page:      http://web.unicom.com.cy/~kpd/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FrameConstraint.java

package com.bdnm.awt;

import java.awt.*;

// Referenced classes of package com.bdnm.awt:
//            OriginConstraint

public class FrameConstraint extends OriginConstraint
{

    public FrameConstraint()
    {
    }

    public FrameConstraint(double d, int i, double d1, int j, double d2, int k, double d3, int l)
    {
        super(d, i, d1, j);
        rightFraction = d2;
        right = k;
        bottomFraction = d3;
        bottom = l;
    }

    public Rectangle adjustedRectangle(Dimension dimension, Dimension dimension1)
    {
        int i = adjustedLeft(dimension);
        int j = adjustedTop(dimension);
        int k = adjustedRight(dimension);
        int l = adjustedBottom(dimension);
        return new Rectangle(i, j, k - i, l - j);
    }

    protected int adjustedRight(Dimension dimension)
    {
        return (int)Math.round(rightFraction * (double)dimension.width) + right;
    }

    protected int adjustedBottom(Dimension dimension)
    {
        return (int)Math.round(bottomFraction * (double)dimension.height) + bottom;
    }

    public Dimension containSize(Dimension dimension)
    {
        Dimension dimension1 = new Dimension();
        if(rightFraction != leftFraction)
            dimension1.width = (int)Math.ceil((double)((dimension.width - right) + left) / (rightFraction - leftFraction));
        else
            dimension1.width = (int)Math.ceil((double)Math.max(-left, -right) / leftFraction);
        if(topFraction != bottomFraction)
            dimension1.height = (int)Math.ceil((double)((dimension.height - bottom) + top) / (bottomFraction - topFraction));
        else
            dimension1.height = (int)Math.ceil((double)Math.max(-top, -bottom) / topFraction);
        return dimension1;
    }

    public double rightFraction;
    public int right;
    public double bottomFraction;
    public int bottom;
}
