// Decompiled by Jad v1.5.5.3. Copyright 1997-98 Pavel Kouznetsov.
// Jad home page:      http://web.unicom.com.cy/~kpd/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StyleManager.java

package com.writeme.guyrice.awt;

import java.awt.*;
import java.util.*;

// Referenced classes of package COM.writeme.guyrice.awt:
//            BaseButton, StyleEvent, StyleListener

public final class StyleManager
{

    public StyleManager()
    {
        outline = false;
        buttonStyle = 0;
        colors = new Color[6];
        listeners = new Vector(16, 16);
        setColor(null);
    }

    public StyleManager beginUpdate()
    {
        if(update++ == 0)
            changed = 0;
        return this;
    }

    public StyleManager endUpdate()
    {
        if(--update == 0)
            sendStyleEvent(changed);
        return this;
    }

    public boolean getOutline()
    {
        return outline;
    }

    public StyleManager setOutline(boolean flag)
    {
        outline = flag;
        setColor(color);
        if(update == 0)
            sendStyleEvent(1);
        else
            changed |= 0x1;
        return this;
    }

    public int getButtonStyle()
    {
        return buttonStyle;
    }

    public StyleManager setButtonStyle(int i)
    {
        buttonStyle = i;
        if(update == 0)
            sendStyleEvent(256);
        else
            changed |= 0x100;
        return this;
    }

    public int getFolderTabGap()
    {
        return folderTabGap;
    }

    public StyleManager setFolderTabGap(int i)
    {
        folderTabGap = i;
        if(update == 0)
            sendStyleEvent(4096);
        else
            changed |= 0x1000;
        return this;
    }

    public Color getColor()
    {
        return color;
    }

    public StyleManager setColor(Color color1)
    {
        color = color1 != null ? color1 : Color.lightGray;
        float af[] = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        colors[0] = Color.getHSBColor(af[0], af[1], 1.0F);
        colors[1] = Color.getHSBColor(af[0], af[1], af[2] + (1.0F - af[2]) * 0.5F);
        colors[2] = Color.getHSBColor(af[0], af[1], af[2]);
        colors[3] = Color.getHSBColor(af[0], af[1], af[2] * 0.6666667F);
        colors[4] = Color.getHSBColor(af[0], af[1], af[2] * 0.3333333F);
        colors[5] = Color.getHSBColor(af[0], af[1], 0.0F);
        if(outline)
        {
            colors[1] = colors[0];
            colors[0] = colors[4] = colors[5];
        }
        if(update == 0)
            sendStyleEvent(2);
        else
            changed |= 0x2;
        return this;
    }

    public Color getOuterLight(boolean flag)
    {
        return colors[flag ? 3 : 0];
    }

    public Color getInnerLight(boolean flag)
    {
        return colors[flag ? 4 : 1];
    }

    public Color getBackground(boolean flag)
    {
        return colors[2];
    }

    public Color getInnerShade(boolean flag)
    {
        return colors[flag ? 0 : 3];
    }

    public Color getOuterShade(boolean flag)
    {
        return colors[flag ? 1 : 4];
    }

    public Color getForeground(boolean flag)
    {
        return colors[5];
    }

    public void addStyleListener(StyleListener stylelistener)
    {
        listeners.addElement(stylelistener);
    }

    public void removeStyleListener(StyleListener stylelistener)
    {
        listeners.removeElement(stylelistener);
    }

    private void sendStyleEvent(int i)
    {
        StyleEvent styleevent = new StyleEvent(this, i);
        for(int j = 0; j < listeners.size(); j++)
            ((StyleListener)listeners.elementAt(j)).styleChanged(styleevent);

    }

    public boolean equals(Object obj)
    {
        if(obj instanceof StyleManager)
        {
            StyleManager stylemanager = (StyleManager)obj;
            if(stylemanager.outline != outline)
                return false;
            if(stylemanager.buttonStyle != buttonStyle)
                return false;
            if(stylemanager.folderTabGap != folderTabGap)
                return false;
            if(!stylemanager.color.equals(color))
                return false;
            for(int i = 0; i < 6; i++)
                if(!stylemanager.colors[i].equals(colors[i]))
                    return false;

            return true;
        }
        else
        {
            return false;
        }
    }

    private int update;
    private int changed;
    private boolean outline;
    private int buttonStyle;
    private int folderTabGap;
    private Color color;
    private Color colors[];
    private Vector listeners;
    public static StyleManager global = new StyleManager();

}
