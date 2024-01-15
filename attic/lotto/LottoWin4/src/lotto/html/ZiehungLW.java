package lotto.html;

import java.io.*;
import java.util.*;

import lotto.util.*;

public class ZiehungLW implements Serializable {
	private int zahlen[] = new int[6];
	private int zz;
	private int nr;
	private int jahr;
	private MyCalendar date;
	private String joker;

	public ZiehungLW(Calendar date, int nr, int jahr,
	               int zahlen[], int zz, String joker) {
        this.zz = zz;
        this.jahr = jahr;
        this.nr = nr;
        for (int i = 0; i < 6; i++)
            this.zahlen[i] = zahlen[i];
        this.date = (MyCalendar)date.clone();
	    this.joker = joker;
	}

    public int getNr() {
        return (nr);
    }

    public String getJoker() {
        return joker;
    }

    public int getJahr() {
		return jahr;
	}


	public int getZZ() {
		return zz;
	}

    public int[] getZahlen() {
	    int z[] = new int[6];

	    for (int i = 0; i < 6; i++)
    		z[i] = zahlen[i];

    	return z;
	}

    public Calendar getDate() {
        return date;
    }


    public int getWin(int tip[]) {
        int i,j,win = 0;

        for (i = 0; i < 6; i++)
            for (j = 0; j < 6; j++)
                if (tip[i] == zahlen[j]) win++;

        if (win == 5) {
            for (i = 0; i < 6; i++)
                if (tip[i] == zz) return(51);
        }

        return (win);

    }

    public boolean isWednesday() {
        if (date.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)
            return true;
        else
            return false;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (nr < 100)
            sb.append(" ");
        if (nr < 10)
            sb.append(" ");
        sb.append(nr);
        sb.append(", ");
        if (isWednesday())
            sb.append("Mittwoch, ");
        else
            sb.append("Samstag,  ");

        if (date.get(Calendar.DAY_OF_MONTH) < 10)
            sb.append(" ");
        sb.append(String.valueOf(date.get(Calendar.DAY_OF_MONTH)));
        sb.append(".");
        if (date.get(Calendar.MONTH)+1 < 10)
            sb.append(" ");
        sb.append(String.valueOf(date.get(Calendar.MONTH)+1));
        sb.append(".");
        sb.append(String.valueOf(date.get(Calendar.YEAR)));

        return sb.toString();
    }
}

