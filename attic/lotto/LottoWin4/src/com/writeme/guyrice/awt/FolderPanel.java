// Decompiled by Jad v1.5.5.3. Copyright 1997-98 Pavel Kouznetsov.
// Jad home page:      http://web.unicom.com.cy/~kpd/jad.html
// Decompiler options: packimports(3)
// Source File Name:   FolderPanel.java

package com.writeme.guyrice.awt;

import java.awt.*;
import java.awt.event.*;

// Referenced classes of package COM.writeme.guyrice.awt:
//            StyleEvent, StyleListener, StyleManager

public class FolderPanel extends Container
    implements StyleListener
{

    public FolderPanel()
    {
        this(null, 0, 0, StyleManager.global);
    }

    public FolderPanel(StyleManager stylemanager)
    {
        this(null, 0, 0, stylemanager);
    }

    public FolderPanel(Insets insets, int i, int j)
    {
        this(insets, i, j, StyleManager.global);
    }

    public FolderPanel(Insets insets, int i, int j, StyleManager stylemanager)
    {
        space = insets != null ? insets : new Insets(0, 0, 0, 0);
        super.setLayout(layout = new CardLayout(i, j));
        style = stylemanager;
        enableEvents(16L);
    }

    public void setLayout(LayoutManager layoutmanager)
    {
        throw new AWTError(getClass().getName() + " layout cannot be changed");
    }

    protected void addImpl(Component component, Object obj, int i)
    {
        if(!(obj instanceof String))
            throw new AWTError("constraints must be a String");
        if(i != -1)
            throw new AWTError("unable to add components at an index");
        super.addImpl(component, obj, i);
        if(tabLabel != null)
        {
            String as[] = new String[tabLabel.length + 1];
            for(int j = 0; j < tabLabel.length; j++)
                as[j] = tabLabel[j];

            as[tabLabel.length] = (String)obj;
            tabLabel = as;
        }
        else
        {
            tabLabel = new String[1];
            tabLabel[0] = (String)obj;
        }
        tabRight = new int[tabLabel.length];
    }

    public void addNotify()
    {
        super.addNotify();
        style.addStyleListener(this);
    }

    public void removeNotify()
    {
        style.removeStyleListener(this);
        super.removeNotify();
    }

    public void styleChanged(StyleEvent styleevent)
    {
        if(styleevent.isFolderChange())
            repaint();
    }

    public Insets getInsets()
    {
        FontMetrics fontmetrics = getFontMetrics(getFont());
        return new Insets(fontmetrics.getHeight() + (fontmetrics.getDescent() + 1) * 2 + space.top + 2, space.left + 2, space.bottom + 2, space.right + 2);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        g.setFont(getFont());
        FontMetrics fontmetrics = g.getFontMetrics();
        int i = fontmetrics.getHeight();
        int j = fontmetrics.getAscent();
        int k = fontmetrics.getDescent() + 1;
        int l = space.top;
        int i1 = space.left;
        int j1 = i1 - style.getFolderTabGap() - 1;
        int k1 = l + i + k * 2;
        int l1 = 0;
        int i2 = 0;
        for(int j2 = 0; j2 < tabLabel.length; j2++)
        {
            i1 = j1 + style.getFolderTabGap() + 1;
            j1 = tabRight[j2] = (i1 + (fontmetrics.stringWidth(tabLabel[j2]) + k * 4)) - 1;
            if(j2 == showing)
            {
                l = space.top;
                l1 = i1;
                i2 = j1;
            }
            else
            {
                l = space.top + 2;
            }
            g.setColor(style.getOuterLight(false));
            g.drawLine(i1, k1, i1, l + 4);
            g.drawLine(i1 + 1, l + 3, i1 + 1, l + 2);
            g.drawLine(i1 + 2, l + 1, i1 + 3, l + 1);
            g.drawLine(i1 + 4, l, j1 - 4, l);
            g.drawLine(j1 - 3, l + 1, j1 - 2, l + 1);
            g.setColor(style.getOuterShade(false));
            g.drawLine(j1 - 1, l + 2, j1 - 1, l + 3);
            g.drawLine(j1, l + 4, j1, k1);
            g.setColor(style.getInnerLight(false));
            g.drawLine(i1 + 1, k1, i1 + 1, l + 4);
            g.drawLine(i1 + 2, l + 3, i1 + 2, l + 2);
            g.drawLine(i1 + 2, l + 2, i1 + 3, l + 2);
            g.drawLine(i1 + 4, l + 1, j1 - 4, l + 1);
            g.drawLine(j1 - 3, l + 2, j1 - 2, l + 2);
            g.setColor(style.getInnerShade(false));
            g.drawLine(j1 - 2, l + 2, j1 - 2, l + 3);
            g.drawLine(j1 - 1, l + 4, j1 - 1, k1);
            g.setColor(style.getForeground(false));
            g.drawString(tabLabel[j2], i1 + k * 2, l + k + j);
        }

        l = space.top + i + k * 2;
        i1 = space.left;
        j1 = getSize().width - 1 - space.right;
        k1 = getSize().height - 1 - space.bottom;
        g.setColor(style.getOuterLight(false));
        g.drawLine(i1, l, i1, k1);
        g.drawLine(i1, l, l1, l);
        g.drawLine(i2, l, j1, l);
        g.setColor(style.getOuterShade(false));
        g.drawLine(j1, l, j1, k1);
        g.drawLine(i1, k1, j1, k1);
        g.setColor(style.getInnerLight(false));
        g.drawLine(i1 + 1, l + 1, i1 + 1, k1 - 1);
        g.drawLine(i1 + 1, l + 1, l1 + 1, l + 1);
        g.drawLine(i2 - 1, l + 1, j1 - 1, l + 1);
        g.setColor(style.getInnerShade(false));
        g.drawLine(j1 - 1, l + 1, j1 - 1, k1 - 1);
        g.drawLine(i1 + 1, k1 - 1, j1 - 1, k1 - 1);
    }

    protected void processMouseEvent(MouseEvent mouseevent)
    {
        if(mouseevent.getID() == 501)
        {
            int i = mouseevent.getX();
            mouseevent.getY();
            for(int j = 0; j < tabRight.length; j++)
            {
                if(i > tabRight[j])
                    continue;
                layout.show(this, tabLabel[j]);
                showing = j;
                repaint();
                break;
            }

        }
        super.processMouseEvent(mouseevent);
    }

    private CardLayout layout;
    private String tabLabel[];
    private int tabRight[];
    private int showing;
    private Insets space;
    private StyleManager style;
}
