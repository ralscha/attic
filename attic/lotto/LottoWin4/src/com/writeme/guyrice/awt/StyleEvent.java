// Decompiled by Jad v1.5.5.3. Copyright 1997-98 Pavel Kouznetsov.
// Jad home page:      http://web.unicom.com.cy/~kpd/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StyleEvent.java

package com.writeme.guyrice.awt;

import java.util.*;

public final class StyleEvent extends EventObject
{

    public StyleEvent(Object obj, int i)
    {
        super(obj);
        changed = i;
    }

    public int getChanged()
    {
        return changed;
    }

    public boolean isChange(int i)
    {
        return (changed & i) != 0;
    }

    public boolean isGlobalChange()
    {
        return (changed & 0xff) != 0;
    }

    public boolean isButtonChange()
    {
        return (changed & 0xf0f) != 0;
    }

    public boolean isFolderChange()
    {
        return (changed & 0xf00f) != 0;
    }

    protected int changed;
    public static final int OUTLINE = 1;
    public static final int COLOR = 2;
    public static final int GLOBAL_MASK = 255;
    public static final int BUTTON_STYLE = 256;
    public static final int BUTTON_MASK = 3855;
    public static final int FOLDER_TAB_GAP = 4096;
    public static final int FOLDER_MASK = 61455;
}
