// Decompiled by Jad v1.5.5.3. Copyright 1997-98 Pavel Kouznetsov.
// Jad home page:      http://web.unicom.com.cy/~kpd/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GridBagHelper.java

package com.writeme.guyrice.awt;

import java.awt.*;

public class GridBagHelper
{

    public GridBagHelper(Container container1)
    {
        container = container1;
        layout = (GridBagLayout)container.getLayout();
        constraints = new GridBagConstraints();
        gridx = constraints.gridx;
        gridy = constraints.gridy;
        gridwidth = constraints.gridwidth;
        gridheight = constraints.gridheight;
        fill = constraints.fill;
        ipadx = constraints.ipadx;
        ipady = constraints.ipady;
        insets = constraints.insets;
        anchor = constraints.anchor;
        weightx = constraints.weightx;
        weighty = constraints.weighty;
    }

    public void add(Component component, int i, int j, int k, int l)
    {
        add(component, i, j, k, l, fill, ipadx, ipady, insets, anchor, weightx, weighty);
    }

    public void add(Component component, int i, int j, int k, int l, int i1)
    {
        add(component, i, j, k, l, fill, ipadx, ipady, insets, i1, weightx, weighty);
    }

    public void add(Component component, int i, int j, int k, int l, Insets insets1, int i1)
    {
        add(component, i, j, k, l, fill, ipadx, ipady, insets1, i1, weightx, weighty);
    }

    public void add(Component component, int i, int j, int k, int l, int i1, double d, double d1)
    {
        add(component, i, j, k, l, i1, ipadx, ipady, insets, anchor, d, d1);
    }

    public void add(Component component, int i, int j, int k, int l, Insets insets1, int i1, 
            double d, double d1)
    {
        add(component, i, j, k, l, i1, ipadx, ipady, insets1, anchor, d, d1);
    }

    public void add(Component component, int i, int j, int k, int l, int i1, int j1, 
            int k1, Insets insets1, int l1, double d, double d1)
    {
        constraints.gridx = i;
        constraints.gridy = j;
        constraints.gridwidth = k;
        constraints.gridheight = l;
        constraints.fill = i1;
        constraints.ipadx = j1;
        constraints.ipady = k1;
        if(insets1 != null)
            constraints.insets = insets1;
        constraints.anchor = l1;
        constraints.weightx = d;
        constraints.weighty = d1;
        layout.setConstraints(component, constraints);
        container.add(component);
    }

    public Container container;
    public GridBagLayout layout;
    public GridBagConstraints constraints;
    public int gridx;
    public int gridy;
    public int gridwidth;
    public int gridheight;
    public int fill;
    public int ipadx;
    public int ipady;
    public Insets insets;
    public int anchor;
    public double weightx;
    public double weighty;
    public static final int RELATIVE = -1;
    public static final int REMAINDER = 0;
    public static final int HORIZONTAL = 2;
    public static final int VERTICAL = 3;
    public static final int BOTH = 1;
    public static final int NONE = 0;
    public static final int CENTER = 10;
    public static final int EAST = 13;
    public static final int NORTH = 11;
    public static final int NORTHEAST = 12;
    public static final int NORTHWEST = 18;
    public static final int SOUTH = 15;
    public static final int SOUTHEAST = 14;
    public static final int SOUTHWEST = 16;
    public static final int WEST = 17;
}
