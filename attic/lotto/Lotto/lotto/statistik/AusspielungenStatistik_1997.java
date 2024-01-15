package lotto.statistik;

public class AusspielungenStatistik_1997 extends AusspielungenStatistik {

	protected boolean inRange(int nr, int jahr) {
   	if (jahr >= 1997)
			return true;
		else
			return false;
    }
    
}