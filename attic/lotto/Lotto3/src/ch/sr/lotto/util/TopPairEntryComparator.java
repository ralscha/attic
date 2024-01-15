package ch.sr.lotto.util;

import java.util.*;

public class TopPairEntryComparator implements Comparator {
	
	public	int compare(Object o1, Object o2) {
		try {
			PairEntry pe1 = (PairEntry)o1;
			PairEntry pe2 = (PairEntry)o2;
			if (pe1.getData() == pe2.getData()) {
				if (pe1.getValue1() == pe2.getValue1()) 
					return (pe1.getValue2() - pe2.getValue2());
				else
					return (pe1.getValue1() - pe2.getValue1());
			} else {
				return (pe2.getData() - pe1.getData());
			} 
				
		} catch (ClassCastException cce) {
			return 1;
		}	
	}
}