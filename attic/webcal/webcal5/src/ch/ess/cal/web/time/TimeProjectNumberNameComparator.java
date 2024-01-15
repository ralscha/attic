package ch.ess.cal.web.time;

import java.util.Comparator;

import ch.ess.cal.model.TimeProject;

public class TimeProjectNumberNameComparator implements Comparator<TimeProject> {
	
	@Override
	// neg. R�ckgabewert: Der erste Parameter ist untergeordnet;
	// 0 als R�ckgabewert: Beide Parameter werden gleich eingeordnet;
	// pos. R�ckgabewert: Der erste Parameter ist �bergeordnet:
	public int compare(TimeProject tp1, TimeProject tp2) {
	 
		// beide keine Projektnummer, Namen entscheidet
		if(tp1.getProjectNumber() == null && tp2.getProjectNumber() == null) {
			return tp1.getName().compareTo(tp2.getName());
		}
		// TP1 keine Projektnummer, TP1 �bergeordnet
		else if(tp1.getProjectNumber() == null && tp2.getProjectNumber() != null) {
			return 1;
		}
		// TP2 keine Projektnummer, TP1 untergeordnet
		else if(tp1.getProjectNumber() != null && tp2.getProjectNumber() == null) {
			return -1;
		}
		// beide Projektnummer
		else {
			// Projektnummer gleich, Projektname entscheidet
			if(tp1.getProjectNumber().compareTo(tp2.getProjectNumber()) == 0){
				return tp1.getName().compareTo(tp2.getName());
			}
			// Projektnummer ungleich, Projektnummer entscheidet
			return tp1.getProjectNumber().compareTo(tp2.getProjectNumber());
		}
	}
}
