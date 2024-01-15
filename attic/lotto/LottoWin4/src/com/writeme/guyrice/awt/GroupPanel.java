// Decompiled by Jad v1.5.5.3. Copyright 1997-98 Pavel Kouznetsov.
// Jad home page:      http://web.unicom.com.cy/~kpd/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GroupPanel.java

package com.writeme.guyrice.awt;

import java.awt.*;

public class GroupPanel extends Container
{

    public GroupPanel()
    {
        this(new FlowLayout(0, 0, 0), null);
    }

    public GroupPanel(String s)
    {
        this(new FlowLayout(0, 0, 0), s);
    }

    public GroupPanel(LayoutManager layoutmanager)
    {
        this(layoutmanager, null);
    }

    public GroupPanel(LayoutManager layoutmanager, String s)
    {
        setLayout(layoutmanager);
        title = s;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String s)
    {
        title = s;
        repaint();
    }

    public Font getTitleFont()
    {
        if(titleFont == null)
            return getFont();
        else
            return titleFont;
    }

    public void setTitleFont(Font font)
    {
        titleFont = font;
        doLayout();
        repaint();
    }

    public Insets getInsets()
    {
        int i = getFontMetrics(getTitleFont()).getHeight();
        return new Insets(i, i, i, i);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        g.setFont(getTitleFont());
        FontMetrics fontmetrics = g.getFontMetrics();
        int i = fontmetrics.getHeight();
        int j = getSize().width - i;
        int k = getSize().height - i;
        g.setColor(getBackground().darker());
        g.drawRect(i / 2 - 1, i / 2 - 1, j, k);
        g.setColor(getBackground().brighter());
        g.drawLine(i / 2, i / 2, (i / 2 + j) - 2, i / 2);
        g.drawLine(i / 2, i / 2, i / 2, (i / 2 + k) - 2);
        g.drawLine(i / 2 - 1, i / 2 + k, i / 2 + j, i / 2 + k);
        g.drawLine(i / 2 + j, i / 2 - 1, i / 2 + j, i / 2 + k);
        if(title != null)
        {
            g.setColor(getBackground());
            int l = fontmetrics.getDescent();
            g.fillRect(i - l, 0, fontmetrics.stringWidth(title) + l * 2, i);
            g.setColor(getForeground());
            g.drawString(title, i, fontmetrics.getAscent());
        }
    }

    private String title;
    private Font titleFont;
}
