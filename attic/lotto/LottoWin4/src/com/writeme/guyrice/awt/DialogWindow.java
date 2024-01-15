// Decompiled by Jad v1.5.5.3. Copyright 1997-98 Pavel Kouznetsov.
// Jad home page:      http://web.unicom.com.cy/~kpd/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DialogWindow.java

package com.writeme.guyrice.awt;

import java.awt.*;

// Referenced classes of package COM.writeme.guyrice.awt:
//            StyleManager

public class DialogWindow extends Window
{

    public DialogWindow(Frame frame)
    {
        this(frame, StyleManager.global);
    }

    public DialogWindow(Frame frame, StyleManager stylemanager)
    {
        super(frame);
        style = stylemanager;
        setForeground(style.getForeground(false));
        setBackground(style.getBackground(false));
    }

    public Insets getInsets()
    {
        return new Insets(8, 8, 8, 8);
    }

    public void paint(Graphics g)
    {
        Dimension dimension = getSize();
        g.setColor(getBackground());
        g.fillRect(0, 0, dimension.width, dimension.height);
        int i = 0;
        int j = 0;
        int k = dimension.width - 1;
        int l = dimension.height - 1;
        boolean flag = false;
        for(int i1 = 0; i1 < 2; i1++)
        {
            g.setColor(style.getOuterLight(flag));
            g.drawLine(j, i, k, i);
            g.drawLine(j, i, j, l);
            g.setColor(style.getOuterShade(flag));
            g.drawLine(j, l, k, l);
            g.drawLine(k, i, k, l);
            g.setColor(style.getInnerLight(flag));
            g.drawLine(j + 1, i + 1, k - 1, i + 1);
            g.drawLine(j + 1, i + 1, j + 1, l - 1);
            g.setColor(style.getInnerShade(flag));
            g.drawLine(j + 1, l - 1, k - 1, l - 1);
            g.drawLine(k - 1, i + 1, k - 1, l - 1);
            i += 4;
            j += 4;
            k -= 4;
            l -= 4;
            flag = true;
        }

        super.paint(g);
    }

    private StyleManager style;
}
