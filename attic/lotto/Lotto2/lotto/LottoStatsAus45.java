package lotto;


class LottoStatsAus45 extends LottoStats {

    protected boolean inRange(int nr, int jahr) {
        if (jahr >= 1986)
            return true;
        else
            return false;
    }
    
    
}
