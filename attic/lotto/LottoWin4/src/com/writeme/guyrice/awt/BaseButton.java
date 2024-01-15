// Decompiled by Jad v1.5.5.3. Copyright 1997-98 Pavel Kouznetsov.
// Jad home page:      http://web.unicom.com.cy/~kpd/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BaseButton.java

package com.writeme.guyrice.awt;

import java.awt.*;
import java.awt.event.*;

// Referenced classes of package COM.writeme.guyrice.awt:
//            StyleEvent, StyleListener, StyleManager

public class BaseButton extends Component
    implements StyleListener
{

    public BaseButton(String s, StyleManager stylemanager)
    {
        style = stylemanager;
        actionCommand = s;
        enableEvents(16L);
    }

    public String getActionCommand()
    {
        return actionCommand;
    }

    public void setActionCommand(String s)
    {
        actionCommand = s;
    }

    public void addActionListener(ActionListener actionlistener)
    {
        actionListeners = AWTEventMulticaster.add(actionListeners, actionlistener);
    }

    public void removeActionListener(ActionListener actionlistener)
    {
        actionListeners = AWTEventMulticaster.remove(actionListeners, actionlistener);
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
        if(styleevent.isButtonChange())
            repaint();
    }

    public void paint(Graphics g)
    {
        paintInside(g);
        paintBorder(g);
    }

    protected void paintInside(Graphics g)
    {
        g.setColor(style.getBackground(down()));
        g.fillRect(2, 2, getSize().width - 4, getSize().height - 4);
    }

    protected void paintBorder(Graphics g)
    {
        int i = 0;
        int j = 0;
        int k = getSize().width - 1;
        int l = getSize().height - 1;
        switch(style.getButtonStyle())
        {
        case 0: /* '\0' */
            g.setColor(style.getOuterLight(down()));
            g.drawLine(j, i, k, i);
            g.drawLine(j, i, j, l);
            g.setColor(style.getOuterShade(down()));
            g.drawLine(j, l, k, l);
            g.drawLine(k, i, k, l);
            g.setColor(style.getInnerLight(down()));
            g.drawLine(j + 1, i + 1, k - 1, i + 1);
            g.drawLine(j + 1, i + 1, j + 1, l - 1);
            g.setColor(style.getInnerShade(down()));
            g.drawLine(j + 1, l - 1, k - 1, l - 1);
            g.drawLine(k - 1, i + 1, k - 1, l - 1);
            return;

        case 1: /* '\001' */
            g.setColor(style.getOuterLight(down()));
            g.drawLine(j + 1, l - 2, j + 1, l - 3);
            g.drawLine(j, l - 4, j, i + 4);
            g.drawLine(j + 1, i + 3, j + 1, i + 2);
            g.drawLine(j + 2, i + 1, j + 3, i + 1);
            g.drawLine(j + 4, i, k - 4, i);
            g.drawLine(k - 3, i + 1, k - 2, i + 1);
            g.setColor(style.getOuterShade(down()));
            g.drawLine(k - 1, i + 2, k - 1, i + 3);
            g.drawLine(k, i + 4, k, l - 4);
            g.drawLine(k - 1, l - 3, k - 1, l - 2);
            g.drawLine(k - 2, l - 1, k - 3, l - 1);
            g.drawLine(k - 4, l, j + 4, l);
            g.drawLine(j + 3, l - 1, j + 2, l - 1);
            g.setColor(style.getInnerLight(down()));
            g.drawLine(j + 2, l - 2, j + 2, l - 3);
            g.drawLine(j + 1, l - 4, j + 1, i + 4);
            g.drawLine(j + 2, i + 3, j + 2, i + 2);
            g.drawLine(j + 2, i + 2, j + 3, i + 2);
            g.drawLine(j + 4, i + 1, k - 4, i + 1);
            g.drawLine(k - 3, i + 2, k - 2, i + 2);
            g.setColor(style.getInnerShade(down()));
            g.drawLine(k - 2, i + 2, k - 2, i + 3);
            g.drawLine(k - 1, i + 4, k - 1, l - 4);
            g.drawLine(k - 2, l - 3, k - 2, l - 2);
            g.drawLine(k - 2, l - 2, k - 3, l - 2);
            g.drawLine(k - 4, l - 1, j + 4, l - 1);
            g.drawLine(j + 3, l - 2, j + 2, l - 2);
            return;

        }
    }

    public boolean getSticky()
    {
        return sticky;
    }

    public void setSticky(boolean flag)
    {
        sticky = flag;
    }

    public boolean getStuck()
    {
        return stuck;
    }

    public void setStuck(boolean flag)
    {
        statusChange(pressed, inside, flag);
    }

    public final boolean down()
    {
        return pressed && inside || stuck;
    }

    private void statusChange(boolean flag, boolean flag1, boolean flag2)
    {
        boolean flag3 = down();
        pressed = flag;
        inside = flag1;
        stuck = flag2;
        if(down() != flag3)
            repaint();
    }

    protected void processMouseEvent(MouseEvent mouseevent)
    {
        switch(mouseevent.getID())
        {
        case 503: 
        default:
            break;

        case 501: 
            statusChange(true, inside, stuck);
            break;

        case 502: 
            if(pressed && inside && !stuck)
            {
                if(sticky)
                    stuck = true;
                if(actionListeners != null)
                    actionListeners.actionPerformed(new ActionEvent(this, 1001, actionCommand));
            }
            statusChange(false, inside, stuck);
            break;

        case 504: 
            statusChange(pressed, true, stuck);
            break;

        case 505: 
            statusChange(pressed, false, stuck);
            break;

        }
        super.processMouseEvent(mouseevent);
    }

    protected StyleManager style;
    private String actionCommand;
    private ActionListener actionListeners;
    private boolean pressed;
    private boolean inside;
    private boolean sticky;
    private boolean stuck;
    public static final int RECTANGLE = 0;
    public static final int ROUNDRECT = 1;
}
