package ch.sr.lotto.statistic;

public class AusspielungenStatistik_42 extends AusspielungenStatistik {

	protected boolean inRange(int nr, int jahr) {
		if (jahr > 1979) 
			return true;
		else if ((jahr == 1979) && (nr >= 14)) 
			return true;
		
		return false;	
	}

}