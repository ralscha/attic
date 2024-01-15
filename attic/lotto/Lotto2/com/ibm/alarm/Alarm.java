
package com.ibm.alarm;

import lotto.util.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.*;

// Referenced classes of package com.ibm.alarm:
//            AlarmEvent, AlarmEventListener

public class Alarm
    implements Runnable, Serializable
{

    public Alarm()
    {
        propertyChange = new PropertyChangeSupport(this);
        fieldRepeatable = false;
        clock = new Thread(this);
        cal = new GregorianCalendar();
        setRepeatable(true);
        fieldTimeZone = TimeZone.getDefault();
        clock.start();
        wasFired = true;
    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener propertychangelistener)
    {
        if(propertychangelistener == null)
        {
            throw new IllegalArgumentException();
        }
        else
        {
            propertyChange.addPropertyChangeListener(propertychangelistener);
            return;
        }
    }

    public void addAlarmEventListener(AlarmEventListener alarmeventlistener)
    {
        if(alarmeventlistener == null)
            throw new IllegalArgumentException();
        if(aAlarmEventListener == null)
            aAlarmEventListener = new Vector();
        aAlarmEventListener.addElement(alarmeventlistener);
    }

    public void firePropertyChange(String s, Object obj, Object obj1)
    {
        propertyChange.firePropertyChange(s, obj, obj1);
    }

    protected void fireAlarmAction(AlarmEvent alarmevent)
    {
        if(aAlarmEventListener == null)
            return;
        int i = aAlarmEventListener.size();
        Object obj = null;
        for(int j = 0; j < i; j++)
        {
            AlarmEventListener alarmeventlistener = (AlarmEventListener)aAlarmEventListener.elementAt(j);
            if(needToFire() && alarmeventlistener != null)
            {
                alarmeventlistener.AlarmAction(alarmevent);
                if(!getRepeatable())
                    removeAlarmEventListener(alarmeventlistener);
            }
        }

    }

    public boolean getRepeatable()
    {
        return fieldRepeatable;
    }

    public Date getTime()
    {
        if(time == null)
            try
            {
                time = new Date();
            }
            catch(Throwable ex)
            {
                DiskLog.getInstance().log(DiskLog.WARNING, getClass().getName(), "Exception creating time property.");
            }
        return time;
    }

    public TimeZone getTimeZone()
    {
        return cal.getTimeZone();
    }

    private boolean needToFire()
    {
        if(wasFired)
            return false;
        int i = cal.get(1);
        int j = cal.get(2);
        int k = cal.get(5);
        int l = cal.get(11);
        int i1 = cal.get(13);
        int j1 = cal.get(12);
        Date date = new Date(i - 1900, j, k, l, j1, i1);
        if(date.after(time) || date.equals(time))
        {
            wasFired = true;
            return true;
        }
        else
        {
            return false;
        }
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener propertychangelistener)
    {
        if(propertychangelistener == null)
        {
            throw new IllegalArgumentException();
        }
        else
        {
            propertyChange.removePropertyChangeListener(propertychangelistener);
            return;
        }
    }

    public void removeAlarmEventListener(AlarmEventListener alarmeventlistener)
    {
        if(alarmeventlistener == null)
            throw new IllegalArgumentException();
        if(aAlarmEventListener != null)
            aAlarmEventListener.removeElement(alarmeventlistener);
    }

    public void run()
    {
        while(true) 
        {
            AlarmEvent alarmevent;
            do
            {
                try
                {
                    Thread.sleep(1000L);
                }
                catch(Exception ex)
                {
                   DiskLog.getInstance().log(DiskLog.WARNING, getClass().getName(), "Interrupted...");
                }
                alarmevent = new AlarmEvent(this, 0, null);
            }
            while(time == null);
            cal.setTime(new Date(System.currentTimeMillis()));
            fireAlarmAction(alarmevent);
        }

    }

    public void setRepeatable(boolean flag)
    {
        boolean flag1 = fieldRepeatable;
        fieldRepeatable = flag;
        firePropertyChange("repeatable", new Boolean(flag1), new Boolean(flag));
    }

    public synchronized void setTime(Date date)
    {
        Date date1 = time;
        time = date;
        firePropertyChange("time", date1, date);
        wasFired = false;
    }

    public void setTimeFromCurrent(int i)
    {
        long l = System.currentTimeMillis();
        setTime(new Date(l + (long)(i * 1000)));
    }

    public void setTimeZone(TimeZone timezone)
    {
        TimeZone timezone1 = fieldTimeZone;
        cal.setTimeZone(timezone);
        fieldTimeZone = timezone;
        firePropertyChange("timeZone", timezone1, timezone);
    }

    protected transient PropertyChangeSupport propertyChange;
    protected Date time;
    protected transient Vector aAlarmEventListener;
    private boolean wasFired;
    private transient Thread clock;
    private GregorianCalendar cal;
    TimeZone fieldTimeZone;
    boolean fieldRepeatable;
}
