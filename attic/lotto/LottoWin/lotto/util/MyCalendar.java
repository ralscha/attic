package lotto.util;

import java.util.*;

public class MyCalendar extends GregorianCalendar
{
    public MyCalendar() {
        super();
    }

    public MyCalendar(long millis) {
        super();
        setTimeInMillis(millis);
    }

    public MyCalendar(int year, int month, int date) {
        super(TimeZone.getDefault(), Locale.getDefault());
        set(Calendar.ERA, AD);
        set(Calendar.YEAR, year);
        set(Calendar.MONTH, month);
        set(Calendar.DATE, date);
        complete();
    }

     public void myset(int year, int month, int date) {
        set(Calendar.ERA, AD);
        set(YEAR, year);
        set(MONTH, month);
        set(DATE, date);
        complete();
     }
}
