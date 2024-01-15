package ch.ess.cal.web.time;

import java.util.Comparator;

import ch.ess.cal.model.TimeTask;

public class TimeTaskNameComparator implements Comparator<TimeTask> {
	
	@Override
	// neg. R�ckgabewert: Der erste Parameter ist untergeordnet;
	// 0 als R�ckgabewert: Beide Parameter werden gleich eingeordnet;
	// pos. R�ckgabewert: Der erste Parameter ist �bergeordnet:
	public int compare(TimeTask tt1, TimeTask tt2) {		
		return tt1.getName().compareTo(tt2.getName());
	}
}
