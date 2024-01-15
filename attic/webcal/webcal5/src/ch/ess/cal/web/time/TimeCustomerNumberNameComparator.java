package ch.ess.cal.web.time;

import java.util.Comparator;

import ch.ess.cal.model.TimeCustomer;

public class TimeCustomerNumberNameComparator implements Comparator<TimeCustomer> {
	
	@Override
	// neg. Rückgabewert: Der erste Parameter ist untergeordnet;
	// 0 als Rückgabewert: Beide Parameter werden gleich eingeordnet;
	// pos. Rückgabewert: Der erste Parameter ist übergeordnet:
	public int compare(TimeCustomer tc1, TimeCustomer tc2) {
	 
		// beide keine Kundennummer, Namen entscheidet
		if(tc1.getCustomerNumber() == null && tc2.getCustomerNumber() == null) {
			return tc1.getName().compareTo(tc2.getName());
		}
		// TC1 keine Kundennummer, TC1 übergeordnet
		else if(tc1.getCustomerNumber() == null && tc2.getCustomerNumber() != null) {
			return 1;
		}
		// TC2 keine Kundennummer, TC1 untergeordnet
		else if(tc1.getCustomerNumber() != null && tc2.getCustomerNumber() == null) {
			return -1;
		}
		// beide Kundennummer
		else {
			// Kundennummer gleich, Name entscheidet
			if(tc1.getCustomerNumber().compareTo(tc2.getCustomerNumber()) == 0){
				return tc1.getName().compareTo(tc2.getName());
			}
			// Kundennummer ungleich, Kundennummer entscheidet
			return tc1.getCustomerNumber().compareTo(tc2.getCustomerNumber());
		}
	}
}
