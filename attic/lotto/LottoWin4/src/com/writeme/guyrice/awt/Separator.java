// Decompiled by Jad v1.5.5.3. Copyright 1997-98 Pavel Kouznetsov.
// Jad home page:      http://web.unicom.com.cy/~kpd/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Separator.java

package com.writeme.guyrice.awt;

import java.awt.*;

public class Separator extends Component
{

    public Separator()
    {
        this(2, true);
    }

    public Separator(int i, boolean flag)
    {
        len = i;
        horizontal = flag;
    }

    public Dimension getMinimumSize()
    {
        if(horizontal)
            return new Dimension(len, 2);
        else
            return new Dimension(2, len);
    }

    public Dimension getPreferredSize()
    {
        if(horizontal)
            return new Dimension(len, 2);
        else
            return new Dimension(2, len);
    }

    public void paint(Graphics g)
    {
        if(horizontal)
        {
            g.setColor(getBackground().darker());
            g.drawLine(0, 0, getSize().width - 1, 0);
            g.setColor(getBackground().brighter());
            g.drawLine(0, 1, getSize().width - 1, 1);
            return;
        }
        else
        {
            g.setColor(getBackground().darker());
            g.drawLine(0, 0, 0, getSize().height - 1);
            g.setColor(getBackground().brighter());
            g.drawLine(1, 0, 1, getSize().height - 1);
            return;
        }
    }

    public static final boolean VERTICAL = false;
    public static final boolean HORIZONTAL = true;
    private int len;
    private boolean horizontal;
}
