// Decompiled by Jad v1.5.5.3. Copyright 1997-98 Pavel Kouznetsov.
// Jad home page:      http://web.unicom.com.cy/~kpd/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AlarmEvent.java

package com.ibm.alarm;

import java.awt.event.ActionEvent;

public class AlarmEvent extends ActionEvent
{

    public AlarmEvent(Object obj, int i, String s)
    {
        super(obj, i, s);
    }

    public AlarmEvent(Object obj, int i, String s, int j)
    {
        super(obj, i, s, j);
    }

    public void AlarmAction(AlarmEvent alarmevent)
    {
    }
}
