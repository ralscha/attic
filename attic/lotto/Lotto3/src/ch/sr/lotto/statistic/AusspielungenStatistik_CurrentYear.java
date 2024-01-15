package ch.sr.lotto.statistic;

import java.util.*;

public class AusspielungenStatistik_CurrentYear extends AusspielungenStatistik {
	
	protected boolean inRange(int nr, int jahr) {

		if (jahr >= Calendar.getInstance().get(Calendar.YEAR))
			return true;
		else
			return false;
	}

}