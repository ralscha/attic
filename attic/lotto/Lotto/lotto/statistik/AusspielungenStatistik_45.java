package lotto.statistik;

public class AusspielungenStatistik_45 extends AusspielungenStatistik {

	protected boolean inRange(int nr, int jahr) {
		if (jahr >= 1986)
			return true;
		else
			return false;	
	}

}