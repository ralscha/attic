package ch.ess.cal.web.time;

import java.util.Comparator;

import ch.ess.cal.model.TimeTask;

public class TimeTaskNameComparator implements Comparator<TimeTask> {
	
	@Override
	// neg. Rückgabewert: Der erste Parameter ist untergeordnet;
	// 0 als Rückgabewert: Beide Parameter werden gleich eingeordnet;
	// pos. Rückgabewert: Der erste Parameter ist übergeordnet:
	public int compare(TimeTask tt1, TimeTask tt2) {		
		return tt1.getName().compareTo(tt2.getName());
	}
}
