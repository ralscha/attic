package ch.ess.cal.web.time;

import java.util.Comparator;

public class TimeReportListComparator implements Comparator<Object[]> {
	
	@Override
	// neg. Rückgabewert: Der erste Parameter ist untergeordnet;
	// 0 als Rückgabewert: Beide Parameter werden gleich eingeordnet;
	// pos. Rückgabewert: Der erste Parameter ist übergeordnet (wird nachfolgend aufgeführt)
	public int compare(Object[] ob1, Object[] ob2) {
		/* OBJECT:
		 * [1]: Kundenname
		 * [2]: Kundennummer
		 * [4]: Projektname
		 * [5]:	Projektnummer
		 * [7]: Task-Name
		*/		
		
		// beide keine Kundennummer
		if(ob1[2] == null && ob2[2] == null) {
			//Kundenname entscheidend
			return compareKundenname(ob1, ob2);
		}
		// OB1 keine Kundennummer, OB2 hat Kundennummer
		else if (ob1[2] == null && ob2[2] != null) {
			return 1;
		}
		// OB2 keine Kundennummer, OB1 hat Kundennummer
		else if (ob1[2] != null && ob2[2] == null) {
			return -1;
		}
		// beide haben Kundennummer
		else if (ob1[2] != null && ob2[2] != null) {
			// Gleiche Kundennummer
			if(ob1[2].toString().equals(ob2[2].toString())){
				//Kundenname entscheidend
				return compareKundenname(ob1, ob2);
			}
			// ungleiche Kundennummer
			return (ob1[2].toString()).compareTo(ob2[2].toString());
		}
		// Default, sollte nie eintreffen
		return 0;
	}

	// neg. Rückgabewert: Der erste Parameter ist untergeordnet;
	// 0 als Rückgabewert: Beide Parameter werden gleich eingeordnet;
	// pos. Rückgabewert: Der erste Parameter ist übergeordnet (wird nachfolgend aufgeführt)
	private int compareKundenname(Object[] ob1, Object[] ob2){
		//Gleicher Kundenname
		if(ob1[1].toString().equals(ob2[1].toString())){	
			// beide keine Projektnummer, Projektname entscheidend
			if(ob1[5] == null && ob2[5] == null) {
				return compareProjektname(ob1, ob2);
			}
			// OB1 Projektnummer, OB2 keine Projektnummer
			else if (ob1[5] != null && ob2[5] == null){
				return -1;
			}
			// OB1 keine Projektnummer, OB2 Projektnummer
			else  if (ob1[5] == null && ob2[5] != null){
				return 1;
			}			
			//beide haben Projektnummer
			else if(ob1[5] != null && ob2[5] != null){
				//gleiche Projektnummer, Projektname entscheidend
				if( (ob1[5].toString()).equals(ob2[5].toString()) ){
					return compareProjektname(ob1, ob2);
				}
				//ungleiche Projektnummer
				return ob1[5].toString().compareTo(ob2[5].toString());
			}
			// Default, sollte nie eintreffen
			return 0;
		}
		//ungleicher Kundenname
		return (ob1[1].toString().toLowerCase()).compareTo(ob2[1].toString().toLowerCase());		
	}

	// neg. Rückgabewert: Der erste Parameter ist untergeordnet;
	// 0 als Rückgabewert: Beide Parameter werden gleich eingeordnet;
	// pos. Rückgabewert: Der erste Parameter ist übergeordnet (wird nachfolgend aufgeführt)
	private int compareProjektname(Object[] ob1, Object[] ob2) {
		// Gleicher Projektname
		if(ob1[4].toString().equals(ob2[4].toString())){
			// Task-Name entscheidend
			return (ob1[7].toString().toLowerCase()).compareTo(ob2[7].toString().toLowerCase());
		}
		// ungleicher Projektname
		return (ob1[4].toString().toLowerCase()).compareTo(ob2[4].toString().toLowerCase());
	}
}
